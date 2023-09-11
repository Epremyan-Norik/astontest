package com.example.astontest.controllers;

import com.example.astontest.exceptions.BaseApiException;
import com.example.astontest.exceptions.ErrorHandleUtils;
import com.example.astontest.model.Account;
import com.example.astontest.model.BaseResponse;
import com.example.astontest.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@SuppressWarnings("java:S3740")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public UUID test() {
        log.info("/test request");
        return  UUID.randomUUID();
    }

    @PostMapping(value = "/createAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAccount(@RequestBody Map<String, String> payload) {
        log.info("/createAccount request");
        try {
            Account account = accountService.createAccount(payload.get("name"), payload.get("accessCode"));
            return new ResponseEntity(account, HttpStatus.OK);
        } catch (BaseApiException e){
            log.error("create account error", e);
            return ResponseEntity.status(e.getStatus()).body(ErrorHandleUtils.buildErrorMessage(e.getMessage()));
        }
    }

    @PostMapping(value = "/topUp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity topUp(@RequestBody Map<String, String> payload) {
        log.info("/topUp request");
        try {
            accountService.topUp(payload.get("accountId"), payload.get("value"));
        } catch (BaseApiException e){
            log.error("topUp error", e);
            return ResponseEntity.status(e.getStatus()).body(ErrorHandleUtils.buildErrorMessage(e.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transfer(@RequestBody Map<String, String> payload) {
        log.info("/transfer request");
        try {
            accountService.transfer(payload.get("accountId"), payload.get("value"), payload.get("recipientId"), payload.get("accessCode"));
        } catch (BaseApiException e){
          log.error("transfer error", e);
          return ResponseEntity.status(e.getStatus()).body(ErrorHandleUtils.buildErrorMessage(e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/readAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity readAll(){
        log.info("/readAll request");
        List<Account> accountList = accountService.getAll();
        return new ResponseEntity(accountList, HttpStatus.OK);
    }

}
