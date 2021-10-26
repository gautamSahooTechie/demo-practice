package com.example.demopractice.account.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.constraints.NotEmpty;

@RestController
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {

    @GetMapping("/{accountNumber}")
    public Flux<AccountTransactionDto> getAllTransactions(@PathVariable("accountNumber") @NotEmpty String accountNumber,
                                                          @Param("fromDate") String fromDate, @Param("toDate") String toDate) {
        return Flux.just(null);
    }
}
