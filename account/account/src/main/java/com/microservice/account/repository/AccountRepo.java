package com.microservice.account.repository;

import com.microservice.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AccountRepo extends JpaRepository<Account,Integer> {


    Account findByCustomerId(int customerId);
}
