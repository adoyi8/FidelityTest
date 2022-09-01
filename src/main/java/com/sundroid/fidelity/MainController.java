package com.sundroid.fidelity;



import com.sundroid.fidelity.models.*;
import com.sundroid.fidelity.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MainController {
    @Autowired
    BankService accountService;

    @PostMapping("/create_account")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody AppUser request) {
        return accountService.addAccount(request);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Map<String, Object>> withdrawal(@AuthenticationPrincipal AppUser user, @RequestBody WithdrawalModel request) {
        return accountService.withdraw(user, request);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, Object>> deposit(@AuthenticationPrincipal AppUser user, @RequestBody DepositModel request) {
        return accountService.deposit(user, request);
    }

    @GetMapping("/account_info")
    public ResponseEntity<Map<String, Object>> getAccountInfo(@AuthenticationPrincipal AppUser user) {
        return accountService.getAccountInfo(user);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transfer(@AuthenticationPrincipal AppUser user, @RequestBody TransactionModel request) {
        return accountService.transfer(user, request);
    }

    @GetMapping("/all_transactions")
    public ResponseEntity<List<TransactionModel>> transactions(@RequestBody Optional<FilterModel> request) {
        return accountService.getAllTransactions(request);
    }

    @GetMapping("/today_transactions")
    public ResponseEntity<List<TransactionModel>> todayTransactions() {
        return accountService.todayTransactions();
    }

    @PostMapping("/disable_user")
    public ResponseEntity<Map<String, Object>> disableUser(@AuthenticationPrincipal AppUser user, @RequestBody AppUser account) {
        return accountService.disableUser(user,account);
    }

    @PostMapping("/delete_user")
    public ResponseEntity<Map<String, Object>> deleteUser(@AuthenticationPrincipal AppUser user, @RequestBody AppUser account) {
        return accountService.deleteUser(user,account);
    }
    @PostMapping("/enable_user")
    public ResponseEntity<Map<String, Object>> enabledUser(@AuthenticationPrincipal AppUser user, @RequestBody AppUser account) {
        return accountService.enableUser(user,account);
    }

    @PostMapping("/make_admin")
    public ResponseEntity<Map<String, Object>> makeAdmin(@AuthenticationPrincipal AppUser user, @RequestBody AppUser account) {
        return accountService.makeAdmin(user,account);
    }
    @PostMapping("/remove_admin")
    public ResponseEntity<Map<String, Object>> removeAdmin(@AuthenticationPrincipal AppUser user, @RequestBody AppUser account) {
        return accountService.removeAdmin(user,account);
    }
}
