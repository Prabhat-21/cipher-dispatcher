package com.example.project.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.example.project.dto.Cipher;
import com.example.project.producer.SqsProducer;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Service
public class CipherDispatherService {
    private final SqsProducer sqsProducer;

    private final Cache<String, Boolean> cipherCache;

    @Autowired
    public CipherDispatherService(SqsProducer sqsProducer) {
        this.sqsProducer = sqsProducer;
        this.cipherCache = Caffeine.newBuilder().build();
    }

    public String dispatch(final Cipher cipher) {
        final String secret = UUID.randomUUID().toString();
        cipherCache.put(secret, true);
        Map<String, MessageAttributeValue> attributes = new HashMap<>();
        attributes.put("secret", new MessageAttributeValue().withDataType("String").withStringValue(secret));
        sqsProducer.sendMessage("card.event", cipher, attributes);
        return "success";
    }

    public String closeLoop(final String secret) {
        cipherCache.invalidate(secret);
        return "success";
    }
}
