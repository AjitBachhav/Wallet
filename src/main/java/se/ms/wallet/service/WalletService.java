package se.ms.wallet.service;

import org.springframework.validation.annotation.Validated;
import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Validated
public interface WalletService {

    /**
     * Performs a transaction on a players account.
     * A lock will be applied to the
     * account database entity until the transaction is complete.
     *
     * @param type, the type of the transaction ('DEBIT' for withdrawal or 'CREDIT' for deposition).
     * @param playerId, the player id.
     * @param amount, the amount to withdraw or deposit.
     * @param transactionId, a unique transaction id.
     *
     * @return {@link Transaction}
     * @throws {@link org.springframework.web.client.HttpServerErrorException} if transaction id isn't valid or
     * if account balance is of insufficient funds
     * */
    Transaction performTransaction(final TransactionType type, @NotNull final Long playerId, @Positive final BigDecimal amount, @NotBlank final String transactionId);

    /**
     * Gets a players wallet balance.
     * */
    BigDecimal getWalletBalance(@NotNull final Long playerId);

    /**
     * Retrieves a players complete transaction history sorted by date and time in descending order.
     * */
    List<Transaction> getTransactionHistory(@NotNull final Long playerId);

    /**
     * Generates a valid unique transaction id.
     * */
    UUID generateTransactionId();
}
