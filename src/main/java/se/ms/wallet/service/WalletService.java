package se.ms.wallet.service;

import org.springframework.validation.annotation.Validated;
import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Validated
public interface WalletService {

    Transaction performTransaction(final TransactionType type, @NotNull final Long playerId, @Positive final BigDecimal amount, @NotBlank final String transactionId);

    BigDecimal getWalletBalance(@NotNull final Long playerId);

    UUID generateTransactionId();
}
