package com.example.demopractice.account;

import com.example.demopractice.account.transaction.AccountTransactionRepository;
import com.example.demopractice.common.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Spy
    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountTransactionRepository accountTransactionRepository;

    @Test
    void should_deposit_positive_Amount() {

        //GIVEN
        Account testAccount = new Account();
        testAccount.setAccountNumber("00000123456");
        testAccount.setBalance(BigDecimal.TEN);
        //WHEN
        when(accountRepository.getAccountByNumber(anyString())).thenReturn(Optional.of(testAccount));
        accountService.accountDeposit("00000123456", BigDecimal.valueOf(10000));
        //THEN
        verify(accountRepository, atMostOnce()).getAccountByNumber("00000123456");
        verify(accountRepository, atMostOnce()).save(testAccount);
        verify(accountTransactionRepository, atMostOnce()).save(any());
    }

    @Test
    void should_fail_deposit_account_not_found() {

        //GIVEN
        Account testAccount = new Account();
        testAccount.setAccountNumber("00000123456");
        testAccount.setBalance(BigDecimal.TEN);
        //WHEN
        when(accountRepository.getAccountByNumber(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.accountDeposit("00000123456", BigDecimal.valueOf(10000)));
        //THEN
        verify(accountRepository, atMostOnce()).getAccountByNumber("00000123456");
        verify(accountRepository, never()).save(testAccount);
        verify(accountTransactionRepository, never()).save(any());
    }
}