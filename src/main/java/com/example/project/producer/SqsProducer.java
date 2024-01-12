package com.example.project.producer;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SqsProducer {
    private final AmazonSQSClient sqsClient;

    private final ObjectMapper objectMapper;

    public SqsProducer() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("", "");
        this.sqsClient = new AmazonSQSClient(basicAWSCredentials);
        this.objectMapper = new ObjectMapper();
    }

    private void sendMessage(String queue, String message, Map<String, MessageAttributeValue> attributes) {
        final SendMessageRequest request = new SendMessageRequest(queue, message);
        request.setMessageAttributes(attributes);
        sqsClient.sendMessage(request);
    }

    public void sendMessage(String queue, Object message, Map<String, MessageAttributeValue> attributes) {
        try {
            sendMessage(queue, objectMapper.writeValueAsString(message), attributes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
