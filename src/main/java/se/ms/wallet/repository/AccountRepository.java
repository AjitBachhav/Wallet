package se.ms.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import se.ms.wallet.entity.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
