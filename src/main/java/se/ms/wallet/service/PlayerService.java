package se.ms.wallet.service;

import org.springframework.validation.annotation.Validated;
import se.ms.wallet.entity.Player;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
public interface PlayerService {

    Player createPlayer(@NotBlank @Size(max = 20) final String playerName);
}
