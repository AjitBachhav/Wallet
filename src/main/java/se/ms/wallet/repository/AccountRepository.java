package se.ms.wallet.repository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import se.ms.wallet.entity.Account;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockById(Long id);
}
