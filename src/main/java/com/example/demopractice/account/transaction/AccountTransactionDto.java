package com.example.demopractice.account.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/***
 * @author Gautam Sahoo
 * @version 1.0
 * @apiNote AccountTransactionDto is the class exposes information for front end
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionDto implements Serializable {

    private static final long serialVersionUID = -1832973080479696704L;
    private BigDecimal transactionAmount;
    private String dateTime;
    private AccountTransaction.TransactionType transactionType;
    private String accountNumber;
    private String currency;

}
