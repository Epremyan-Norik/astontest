package com.example.astontest.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Account")
@Getter
@Setter
@Builder
public class Account {

    @Id
    private String accountId;
    private String name;
    @JsonIgnore
    private String accessCode;
    private double balance;
}
