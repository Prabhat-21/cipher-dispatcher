package com.example.project.consumer;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqsConsumer {
    private final AmazonSQSClient amazonSQSClient;

    private final ObjectMapper objectMapper;

    public SqsConsumer() {
        AWSCredentials awsCredentials = new BasicAWSCredentials("","");
        this.amazonSQSClient = new AmazonSQSClient(awsCredentials);
        this.objectMapper=new ObjectMapper();
    }

    public List<Message> receiveMessage(String queue, String pollTime){
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest();
        receiveMessageRequest.setWaitTimeSeconds(5);
        receiveMessageRequest.setQueueUrl("");
        ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(receiveMessageRequest);
        return receiveMessageResult.getMessages();
    }


}
