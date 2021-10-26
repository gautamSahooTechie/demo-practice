package com.example.demopractice.account;

import com.example.demopractice.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atMostOnce;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AccountController.class)
@Import(AccountService.class)
class AccountControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private AccountService accountService;

    @Test
    void should_deposit_positive_Amount() {
        BigDecimal amount = BigDecimal.valueOf(2000);
        String accountNumber = "00000123456";

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/account/deposit/{amount}/{accountNumber}").build(amount, accountNumber))
                .body(BodyInserters.empty())
                .exchange()
                .expectStatus().isOk().expectBody(Status.class);

        Mockito.verify(accountService, atMostOnce()).accountDeposit(anyString(), any());
    }

    @Test
    void should_withdraw_positive_Amount() {
        BigDecimal amount = BigDecimal.valueOf(2000);
        String accountNumber = "00000123456";

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/account/withdraw/{amount}/{accountNumber}").build(amount, accountNumber))
                .body(BodyInserters.empty())
                .exchange()
                .expectStatus().isOk().expectBody(Status.class);

        Mockito.verify(accountService, atMostOnce()).withdrawAmount(anyString(), any());
    }

    @Test
    void should_show_account_Balance() {

        Mockito.when(accountService.getBalance(anyString())).thenReturn(BigDecimal.valueOf(500));

        String accountNumber = "00000123456";
        BigDecimal balance = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/account/balance/{accountNumber}").build(accountNumber))
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BigDecimal.class)
                .returnResult().getResponseBody();

        Mockito.verify(accountService, atMostOnce()).getBalance(anyString());
        assertEquals(BigDecimal.valueOf(500), balance);
    }

}