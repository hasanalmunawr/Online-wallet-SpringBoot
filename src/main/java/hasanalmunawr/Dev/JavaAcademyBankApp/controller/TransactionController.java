package hasanalmunawr.Dev.JavaAcademyBankApp.controller;

import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.DepositRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.TransferRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.request.WithdrawRequest;
import hasanalmunawr.Dev.JavaAcademyBankApp.dto.response.WithdrawResponse;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.PrimaryTransaction;
import hasanalmunawr.Dev.JavaAcademyBankApp.entity.UserEntity;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.AccountService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.PrimaryTransactionService;
import hasanalmunawr.Dev.JavaAcademyBankApp.service.TransactionService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private final AccountService accountService;
    private final PrimaryTransactionService primaryTransactionService;

    @PostMapping(path = "/deposit")
    public ResponseEntity<?> deposit(
            @RequestBody DepositRequest request,
            @AuthenticationPrincipal UserEntity currentUser) throws MessagingException, IOException {

        transactionService.deposit(request, currentUser);
        return ResponseEntity.ok("This is a deposit for " + currentUser.getFullName());
    }


    @GetMapping(path = "/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(
            @RequestBody WithdrawRequest request,
            @AuthenticationPrincipal UserEntity currentUser) {
//        UserEntity user = (UserEntity) currentUser.getPrincipal();

        return ResponseEntity.ok(transactionService.withdraw(request, currentUser));
    }


    @PostMapping(path = "/transfers")
    public ResponseEntity<?> tranfers(
            @RequestBody TransferRequest request,
            @AuthenticationPrincipal UserEntity currentUser) throws BadRequestException {
//        UserEntity user = (UserEntity) currentUser.getPrincipal();

        return ResponseEntity.ok(transactionService.transfer(request, currentUser));
    }

    public ResponseEntity<List<PrimaryTransaction>> getHistoryTransaction(
            Authentication currentUser
    ) {
        return ResponseEntity.ok(primaryTransactionService.getHistoryTransactions(currentUser));
    }
}
