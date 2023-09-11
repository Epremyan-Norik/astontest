package com.example.astontest;


import com.example.astontest.exceptions.BaseApiException;
import com.example.astontest.model.Account;
import com.example.astontest.repo.AccountRepo;
import com.example.astontest.service.AccountService;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @MockBean
    AccountRepo accountRepo;

    @Before
    public void setUp() {
        Account alex = Account.builder()
                .accountId("23ce039b-953b-45b3-8e67-46fc031296ee")
                .name("alex")
                .accessCode("1111")
                .balance(320.0)
                .build();

        Account kate = Account.builder()
                .accountId("44e00605-d438-48dd-83f2-6aeab03ca98d")
                .name("kate")
                .accessCode("2222")
                .balance(130.0)
                .build();

        Mockito.when(accountRepo.findById("23ce039b-953b-45b3-8e67-46fc031296ee"))
                .thenReturn(Optional.of(alex));
        Mockito.when(accountRepo.findById("44e00605-d438-48dd-83f2-6aeab03ca98d"))
                .thenReturn(Optional.of(kate));
        Mockito.when(accountRepo.findAll()).thenReturn(List.of(alex, kate));

        Mockito.when(accountRepo.save(alex)).thenReturn(alex);
        Mockito.when(accountRepo.save(kate)).thenReturn(kate);
    }

    @Test
    @SneakyThrows
    public void transferTest(){
        accountService.transfer("23ce039b-953b-45b3-8e67-46fc031296ee",
                "120",
                "44e00605-d438-48dd-83f2-6aeab03ca98d",
                "1111");

        Account alex = accountRepo.findById("23ce039b-953b-45b3-8e67-46fc031296ee").orElseThrow();
        Account kate = accountRepo.findById("44e00605-d438-48dd-83f2-6aeab03ca98d").orElseThrow();

        assertThat(alex.getBalance()).isEqualTo(200.0);
        assertThat(kate.getBalance()).isEqualTo(250.0);

    }

    @Test
    public void getAllTest(){
        List<Account> accounts = accountService.getAll();

        assertThat(accounts).hasSize(2);

    }

    @Test
    @SneakyThrows
    public void transferWithInvalidPinTest(){
        assertThrows(BaseApiException.class,
                () -> accountService.transfer("23ce039b-953b-45b3-8e67-46fc031296ee",
                "120",
                "44e00605-d438-48dd-83f2-6aeab03ca98d",
                "1211"));

    }

    @Test
    public void pinLenTest(){
        assertThrows(BaseApiException.class, () -> accountService.createAccount("alex", "123a"));
    }

    @Test
    @SneakyThrows
    public void topUpTest() {
        accountService.topUp("23ce039b-953b-45b3-8e67-46fc031296ee", "10");
        Account account = accountRepo.findById("23ce039b-953b-45b3-8e67-46fc031296ee").orElseThrow();

        assertEquals(330.0, account.getBalance());
    }



}
