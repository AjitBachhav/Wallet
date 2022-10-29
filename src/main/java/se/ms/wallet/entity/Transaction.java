package se.ms.wallet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ms.wallet.enums.TransactionType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private UUID id;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Account account;

    private LocalDateTime transactionDateTime;

    public Transaction(final UUID id, final BigDecimal amount, final TransactionType type, final LocalDateTime transactionDateTime) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.transactionDateTime = transactionDateTime.truncatedTo(ChronoUnit.SECONDS);
    }
}
