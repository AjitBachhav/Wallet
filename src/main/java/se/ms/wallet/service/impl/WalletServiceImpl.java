package se.ms.wallet.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import se.ms.wallet.entity.Account;
import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;
import se.ms.wallet.repository.AccountRepository;
import se.ms.wallet.service.WalletService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class WalletServiceImpl implements WalletService {

    private AccountRepository accountRepository;

    public WalletServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction performTransaction(final TransactionType type, final Long playerId, final BigDecimal amount, final String transactionId) {
        final Account account = accountRepository.findById(playerId)
                .orElseThrow(() -> new HttpServerErrorException(NOT_FOUND, "Couldn't find an account by given id."));

        final BigDecimal balance = account.getBalance();
        final BigDecimal newBalance = balance.add(amount.multiply(BigDecimal.valueOf(type.getSignum())));

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new HttpServerErrorException(FORBIDDEN, "Negative account balance is not allowed.");
        }

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
}
