package com.example.demopractice.account;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    /**
     * This method returns DB Account entity searched by account number
     * @param accountNumber
     * @return Account entity
     */
    @Query("SELECT acc FROM Account acc WHERE acc.accountNumber=(:accountNumber)")
    Optional<Account> getAccountByNumber(@Param("accountNumber") String accountNumber);
}
