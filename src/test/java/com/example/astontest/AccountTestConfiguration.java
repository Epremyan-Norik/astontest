package com.example.astontest;


import com.example.astontest.repo.AccountRepo;
import com.example.astontest.service.AccountService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@TestConfiguration
public class AccountTestConfiguration {

    @Bean
    @Primary
    public AccountService accountService() {
        return new AccountService();
    }

    @Bean
    @Primary
    public AccountRepo nameService() {
        return Mockito.mock(AccountRepo.class);
    }


}
