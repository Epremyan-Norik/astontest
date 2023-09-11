package com.example.astontest.service;


import com.example.astontest.exceptions.BaseApiException;
import com.example.astontest.model.Account;

import com.example.astontest.repo.AccountRepo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public Account createAccount(String name, String accessCode) throws BaseApiException{
        if (name == null || name.isEmpty()) throw new BaseApiException("Name must be provided");
        if (accessCode.length() != 4 || accessCode.replaceAll("\\d", "").length() > 0) throw new BaseApiException("Pin must be 4 digit length");
        UUID uuid = UUID.randomUUID();
        Account account = Account.builder()
                .accountId(uuid.toString())
                .name(name)
                .accessCode(accessCode)
                .build();
        accountRepo.save(account);
        return account;

    }

    @Transactional
    public void topUp(String accountId, String value) throws BaseApiException {
        double dValue = Double.parseDouble(value);
        if (dValue > 0) {
            Account account = accountRepo.findById(accountId).orElseThrow(() -> new BaseApiException("Account not found"));
            account.setBalance(account.getBalance() + dValue);
            accountRepo.save(account);
        } else {
            throw new BaseApiException("Top up value must be more than 0");
        }

    }

    @Transactional
    public void transfer(String accountId, String value, String recipientId, String accessCode) throws BaseApiException{
        double dValue = Double.parseDouble(value);
        if (dValue > 0) {
            Account sender = accountRepo.findById(accountId).orElseThrow(() -> new BaseApiException("Sender not found"));
            if (sender.getBalance() - dValue < 0) throw new BaseApiException("Have not enough money");
            if (!sender.getAccessCode().equals(accessCode)) throw new BaseApiException("Incorrect PIN");
            Account recipient = accountRepo.findById(recipientId).orElseThrow(() -> new BaseApiException("Recipient not found"));
            recipient.setBalance(recipient.getBalance() + dValue);
            sender.setBalance(sender.getBalance() - dValue);
            accountRepo.save(recipient);
            accountRepo.save(sender);
        } else {
            throw new BaseApiException("Transfer must be more than 0");
        }
    }

    public List<Account> getAll() {
        List<Account> response = new ArrayList<>();
        accountRepo.findAll().forEach(response::add);
        return response;
    }


}
