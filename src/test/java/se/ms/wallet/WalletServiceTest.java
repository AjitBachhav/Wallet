package se.ms.wallet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpServerErrorException;
import se.ms.wallet.entity.Account;
import se.ms.wallet.entity.Transaction;
import se.ms.wallet.enums.TransactionType;
import se.ms.wallet.repository.AccountRepository;
import se.ms.wallet.repository.TransactionRepository;
import se.ms.wallet.service.WalletService;
import se.ms.wallet.service.impl.WalletServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class WalletServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private WalletService walletService;

    @BeforeEach
    void setUp() {
        walletService = new WalletServiceImpl(accountRepository, transactionRepository);
    }

    @Test
    void verifyNonExistingAccountThrowsException() {
        final Long playerId = 1L;

        when(accountRepository.findById(playerId)).thenReturn(Optional.empty());

        var exception = assertThrows(HttpServerErrorException.class,
                () -> walletService.performTransaction(TransactionType.CREDIT, playerId, BigDecimal.ONE, UUID.randomUUID().toString()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals(exception.getStatusText(), "Couldn't find an account by given id.");
    }

    @Test
    void verifyNonUniqueTransactionIdThrowsException() {
        final Long playerId = 1L;
        final UUID transactionId = UUID.randomUUID();

        when(accountRepository.findById(playerId)).thenReturn(Optional.of(new Account()));
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(new Transaction()));

        var exception = assertThrows(HttpServerErrorException.class,
                () -> walletService.performTransaction(TransactionType.CREDIT, playerId, BigDecimal.ONE, transactionId.toString()));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals(exception.getStatusText(), "Transaction id is not unique.");
    }

    @Test
    void verifyInvalidTransactionIdThrowsException() {
        final Long playerId = 1L;
        when(accountRepository.findById(playerId)).thenReturn(Optional.of(new Account()));

        var exception = assertThrows(HttpServerErrorException.class,
                () -> walletService.performTransaction(TransactionType.CREDIT, playerId, BigDecimal.ONE, "Invalid id"));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals(exception.getStatusText(), "Transaction id is of invalid format.");
    }

    @Test
    void verifyDebitTransactionFailsWhenNonSufficientFunds() {
        final Long playerId = 1L;
        when(accountRepository.findWithLockById(playerId)).thenReturn(Optional.of(new Account()));

        var exception = assertThrows(HttpServerErrorException.class,
                () -> walletService.performTransaction(TransactionType.DEBIT, playerId, BigDecimal.ONE, UUID.randomUUID().toString()));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals(exception.getStatusText(), "Transaction failed due to non-sufficient funds.");
    }
}
