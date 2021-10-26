package com.example.demopractice.account;

import com.example.demopractice.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@RestController
@Slf4j
@RequestMapping("/account")
public class AccountController {


    @Autowired
    private AccountService accountService;

    /**
     * This controller method exposes /account/{amount}/{accountNumber}/deposit URI which is useful to perform account deposit for a customer.
     *
     * @param amount        transaction amount
     * @param accountNumber account number to customer where transaction has to happen
     * @return status
     */
    @PostMapping("/deposit/{amount}/{accountNumber}")
    public Mono<Status> depositAmount(@PathVariable("amount") @Digits(integer = 14, fraction = 3) @Positive BigDecimal amount,
                                      @PathVariable("accountNumber") @NotEmpty String accountNumber) {
        long startTime = System.currentTimeMillis();
        accountService.accountDeposit(accountNumber, amount);
        log.info("AccountController.depositAmount method took time in millis " + (System.currentTimeMillis() - startTime));
        return Mono.just(Status.SUCCESS);
    }

    /**
     * This controller method exposes /account/{amount}/{accountNumber}/deposit URI which is useful to perform account deposit for a customer.
     *
     * @param amount        transaction amount
     * @param accountNumber account number to customer where transaction has to happen
     * @return status
     */
    @PostMapping("/withdraw/{amount}/{accountNumber}")
    public Mono<Status> withdrawAmount(@PathVariable("amount") @Digits(integer = 14, fraction = 3) @Positive BigDecimal amount,
                                       @PathVariable("accountNumber") @NotEmpty String accountNumber) {
        long startTime = System.currentTimeMillis();
        accountService.withdrawAmount(accountNumber, amount);
        log.info("AccountController.withdrawAmount method took time in millis " + (System.currentTimeMillis() - startTime));
        return Mono.just(Status.SUCCESS);
    }

    @GetMapping("/balance/{accountNumber}")
    public Mono<BigDecimal> getBalance(@PathVariable("accountNumber") @NotEmpty String accountNumber) {
        long startTime = System.currentTimeMillis();
        BigDecimal balance = accountService.getBalance(accountNumber);
        log.info("AccountController.getBalance method took time in millis " + (System.currentTimeMillis() - startTime));
        return Mono.just(balance);
    }
}
