package se.ms.wallet.repository;

import org.springframework.data.repository.CrudRepository;
import se.ms.wallet.entity.Player;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Player> findByName(String name);
}
