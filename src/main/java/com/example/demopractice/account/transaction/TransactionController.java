package com.example.demopractice.account.transaction;

import com.example.demopractice.account.AccountService;
import com.example.demopractice.common.validations.DateValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/transaction")
@Validated
public class TransactionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private DateTimeFormatter appDateFormater;

    @GetMapping("/{accountNumber}")
    public Flux<List<AccountTransactionDto>> getAllTransactions(@PathVariable("accountNumber") @NotEmpty String accountNumber,
                                                                @Param("fromDateString") @DateValidator(pattern = "yyyy-MM-dd") String fromDateString,
                                                                @Param("toDateString") @DateValidator(pattern = "yyyy-MM-dd") String toDateString) {

        List<AccountTransactionDto> transactionDtoList = accountService.getAccountTransactions(accountNumber, fromDateString, toDateString);
        return Flux.just(transactionDtoList);
    }
}
