package com.example.astontest.serialize;

import com.example.astontest.model.Account;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class AccountSerializer implements RedisSerializer<Account> {
    @Override
    public byte[] serialize(Account account) throws SerializationException {
        return new byte[0];
    }

    @Override
    public Account deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
