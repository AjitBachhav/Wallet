package se.ms.wallet.service.impl;

import org.springframework.stereotype.Service;
import se.ms.wallet.entity.Account;
import se.ms.wallet.entity.Player;
import se.ms.wallet.repository.PlayerRepository;
import se.ms.wallet.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    public PlayerServiceImpl(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(final String playerName) {

        return playerRepository.save(new Player(playerName, new Account()));
    }
}
