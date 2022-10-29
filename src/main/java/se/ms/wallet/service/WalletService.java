package se.ms.wallet.service;

import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {

    Transaction performTransaction(final TransactionType type, final Long playerId, final BigDecimal amount, final String transactionId);

    BigDecimal getWalletBalance(final Long playerId);

    UUID generateTransactionId();
}
