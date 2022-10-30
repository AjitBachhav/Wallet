package se.ms.wallet.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import se.ms.wallet.entity.Account;
import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;
import se.ms.wallet.repository.AccountRepository;
import se.ms.wallet.repository.TransactionRepository;
import se.ms.wallet.service.WalletService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class WalletServiceImpl implements WalletService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public WalletServiceImpl(final AccountRepository accountRepository, final TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public Transaction performTransaction(final TransactionType type, final Long playerId, final BigDecimal amount, final String transactionId) {
        final Account account = accountRepository.findById(playerId)
                .orElseThrow(() -> new HttpServerErrorException(NOT_FOUND, "Couldn't find an account by given id."));

        final BigDecimal balance = account.getBalance();
        final BigDecimal newBalance = balance.add(amount.multiply(BigDecimal.valueOf(type.getSignum())));

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new HttpServerErrorException(FORBIDDEN, "Negative account balance is not allowed.");
        }

        validateTransactionId(transactionId);
        final Transaction transaction = new Transaction(UUID.fromString(transactionId), amount, type, LocalDateTime.now());

        account.setBalance(newBalance);
        account.addTransaction(transaction);
        accountRepository.save(account);
        return transaction;
    }

    @Override
    public BigDecimal getWalletBalance(final Long playerId) {
        return accountRepository.findById(playerId).map(Account::getBalance)
                .orElseThrow(() -> new HttpServerErrorException(NOT_FOUND));
    }

    @Override
    public UUID generateTransactionId() {
        return UUID.randomUUID();
    }

    private void validateTransactionId(final String transactionId) {
        try {
            final UUID uuid = UUID.fromString(transactionId);
            if (transactionRepository.findById(uuid).isPresent()) {
                throw new HttpServerErrorException(FORBIDDEN, "Transaction id is not unique.");
            }
        } catch (final IllegalArgumentException ex) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Transaction id is of invalid format.");
        }
    }
}
