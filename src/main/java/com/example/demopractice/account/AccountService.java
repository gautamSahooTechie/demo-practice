package com.example.demopractice.account;

import com.example.demopractice.account.transaction.AccountTransaction;
import com.example.demopractice.account.transaction.AccountTransactionRepository;
import com.example.demopractice.common.EntityNotFoundException;
import com.example.demopractice.common.MinimumBalanceDepleted;
import com.example.demopractice.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTransactionRepository accountTransactionRepository;

    private final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Transactional
    public void accountDeposit(String accountNumber, BigDecimal depositAmount) {

        log.debug("Enter into method AccountService.accountDeposit -> params {0} {1}", accountNumber, depositAmount);

        Account account = accountRepository.getAccountByNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("Account number not present in system " + accountNumber));
        account.setBalance(account.getBalance().add(depositAmount));
        accountRepository.save(account);

        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAccount(account);
        accountTransaction.setTransactionAmount(depositAmount);
        accountTransaction.setDateTime(LocalDateTime.now());
        accountTransaction.setTransactionType(AccountTransaction.TransactionType.CREDIT);
        accountTransactionRepository.save(accountTransaction);

        log.debug("Exit from method AccountService.accountDeposit -> params {0} {1}", accountNumber, depositAmount);
    }

    @Transactional
    public void withdrawAmount(String accountNumber, BigDecimal withdrawAmount) {

        log.debug("Enter into method AccountService.withdrawAmount -> params {0} {1}", accountNumber, withdrawAmount);

        Account account = accountRepository.getAccountByNumber(accountNumber).orElseThrow(() -> new EntityNotFoundException("Account number not present in system " + accountNumber));

        BigDecimal minimumBalance = context.getBean("minimumBalance", BigDecimal.class);
        Predicate<Account> minimumBalanceDepleted = acct -> account.getBalance().subtract(withdrawAmount).compareTo( minimumBalance ) < 0;

        Optional.of(minimumBalanceDepleted.test(account)).orElseThrow(() -> new MinimumBalanceDepleted("Minimum balance "+ minimumBalance + "has to be maintained"));

        account.setBalance(account.getBalance().subtract(withdrawAmount));
        accountRepository.save(account);

        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAccount(account);
        accountTransaction.setTransactionAmount(withdrawAmount);
        accountTransaction.setDateTime(LocalDateTime.now());
        accountTransaction.setTransactionType(AccountTransaction.TransactionType.DEBIT);
        accountTransactionRepository.save(accountTransaction);

        log.debug("Exit from method AccountService.withdrawAmount -> params {0} {1}", accountNumber, withdrawAmount);

    }
}
