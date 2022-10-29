package se.ms.wallet.service.impl;

import org.springframework.stereotype.Service;
import se.ms.wallet.entity.Account;
import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;
import se.ms.wallet.repository.AccountRepository;
import se.ms.wallet.service.WalletService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    private AccountRepository accountRepository;

    public WalletServiceImpl(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction performTransaction(final TransactionType type, final Long playerId, final BigDecimal amount, final String transactionId) {
        return null;
    }

    @Override
    public BigDecimal getWalletBalance(final Long playerId) {
        return accountRepository.findById(playerId).map(Account::getBalance).orElse(null);
    }

    @Override
    public UUID generateTransactionId() {
        return UUID.randomUUID();
    }
}
