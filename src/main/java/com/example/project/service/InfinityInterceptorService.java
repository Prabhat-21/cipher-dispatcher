package com.example.project.service;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.project.consumer.SqsConsumer;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfinityInterceptorService {


    private final SqsConsumer sqsConsumer;

    @Autowired
    public InfinityInterceptorService(SqsConsumer sqsConsumer) {
        this.sqsConsumer = sqsConsumer;
    }

    public List<Message> receiveMessages(String queue, String pollTime) throws Exception{
        if(getRandomNumber()<0.4){
            throw new Exception("Message Missed by Interceptor");
        }
        return sqsConsumer.receiveMessage(queue,pollTime);
    }

    public Double getRandomNumber(){
        return Math.random();
    }
}
