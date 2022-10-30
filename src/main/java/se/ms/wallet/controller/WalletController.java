package se.ms.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;
import se.ms.wallet.service.WalletService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private WalletService walletService;

    public WalletController(final WalletService walletService) {
        this.walletService = walletService;
    }

    @PutMapping(path = "/transaction/{type}/{playerId}/{amount}/{transactionId}")
    public ResponseEntity<Transaction> performTransaction(
            @PathVariable final TransactionType type,
            @PathVariable final Long playerId,
            @PathVariable final BigDecimal amount,
            @PathVariable final String transactionId) {

        return ResponseEntity.ok(walletService.performTransaction(type, playerId, amount, transactionId));
    }

    @GetMapping(path = "/balance/{playerId}")
    public ResponseEntity<BigDecimal> getWalletBalance(@PathVariable final Long playerId) {
        return ResponseEntity.ok(walletService.getWalletBalance(playerId));
    }

    @GetMapping(path = "/history/{playerId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable final Long playerId) {
        return ResponseEntity.ok(walletService.getTransactionHistory(playerId));
    }

    @GetMapping(path = "/generateTransactionId")
    public ResponseEntity<UUID> generateTransactionId() {
        return ResponseEntity.ok(walletService.generateTransactionId());
    }
}
