package se.ms.wallet.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private Long id;

    private BigDecimal balance = BigDecimal.ZERO;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(final Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setAccount(this);
    }
}
