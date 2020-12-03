package org.yanwen.consumer;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.yanwen.consumer.service.ListenerService;
import org.yanwen.mvc.ApplicationBootstrap;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

@SpringBootApplication(scanBasePackages = {"org.yanwen"})
public class ConsumerApplication {
    public static void main(String[] args) throws JMSException, InterruptedException {
        AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);

        SQSConnection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageConsumer consumer = session.createConsumer(session.createQueue(System.getProperty("aws.sqs.name")));
        ListenerService processService = new ListenerService();
        consumer.setMessageListener(processService);
        connection.start();
        System.out.println("connection start");
        Thread.sleep(1000);

//        ConfigurableApplicationContext app = SpringApplication.run(ConsumerApplication.class, args);
//        SQSMessageService messageService = app.getBean(SQSMessageService.class);
//        messageService.receiveMessage();
    }
}
