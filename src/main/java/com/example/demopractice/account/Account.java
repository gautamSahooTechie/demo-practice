package com.example.demopractice.account;

import com.example.demopractice.account.transaction.AccountTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/***
 * @author Gautam Sahoo
 * @version 1.0
 * @apiNote Account is the entity class maintains all account details in Database
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "account", uniqueConstraints = {@UniqueConstraint(name = "uni_acc", columnNames = {"account_number"})})
public class Account implements Serializable {


    private static final long serialVersionUID = 3624882877278539618L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;

    @NotNull
    @Column(name = "account_number")
    private String accountNumber;

    @NotNull
    @Column(name = "currency")
    private String currency;

    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "transactions")
    private List<AccountTransaction> transactions;
}
