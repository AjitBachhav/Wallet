package se.ms.wallet.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import se.ms.wallet.entity.Account;
import se.ms.wallet.entity.Player;
import se.ms.wallet.repository.PlayerRepository;
import se.ms.wallet.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(final String playerName) {
        return playerRepository.save(new Player(playerName, new Account()));
    }

    @Override
    public Long getPlayerIdByName(final String name) {
        final Player player = playerRepository.findByName(name).orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
        return player.getId();
    }
}
