package com.example.demopractice.account.transaction;

import com.example.demopractice.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/***
 * @author Gautam Sahoo
 * @version 1.0
 * @apiNote AccountTransaction is the entity class maintains all account transaction details in Database
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "account_transaction")
public class AccountTransaction implements Serializable {

    private static final long serialVersionUID = 2506803248968895792L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "transaction_amount")
    private BigDecimal transactionAmount;

    @NotNull
    @Column(name = "transaction_time")
    private LocalDateTime dateTime;

    @NotNull
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public enum TransactionType {
        DEBIT, CREDIT
    }

    private String accountNumber;
    private String currency;

    public String getAccountNumber() {
        return this.account.getAccountNumber();
    }

    public String getCurrency() {
        return this.account.getCurrency();
    }

}
