package se.ms.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.ms.wallet.entity.Player;
import se.ms.wallet.service.PlayerService;

@RestController
@RequestMapping("/api/player")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(final PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping(path = "/{name}")
    public ResponseEntity<String> createPlayer(@PathVariable final String name) {
        final Player player = playerService.createPlayer(name);
        return ResponseEntity.ok("Player " + player.getName() + " created.");
    }

    @GetMapping(path = "/{name}/id")
    public ResponseEntity<Long> getPlayerIdByName(@PathVariable final String name) {
        return ResponseEntity.ok(playerService.getPlayerIdByName(name));
    }
}
