package se.ms.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import se.ms.wallet.entity.Transaction;

import java.util.UUID;

public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
}
