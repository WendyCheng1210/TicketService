package org.yanwen.service;

import com.amazonaws.services.sqs.AmazonSQS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yanwen.ApplicationBootstrap;
import org.yanwen.service.MessageService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationBootstrap.class)
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Autowired
    private AmazonSQS sqsClient;

    @Test
    public void sendMessageTest() {
        messageService.sendMessage("test", 1);
    }

    @Test
    public void getQueueUrlTest(){
        messageService.getQueueUrl("123");
        verify(sqsClient, times(1)).getQueueUrl("123");
    }
}
