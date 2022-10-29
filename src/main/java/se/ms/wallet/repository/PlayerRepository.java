package se.ms.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import se.ms.wallet.entity.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
