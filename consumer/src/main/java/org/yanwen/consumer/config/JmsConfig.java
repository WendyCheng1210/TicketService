package org.yanwen.consumer.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import javax.jms.Session;

@Configuration
public class JmsConfig {
    @Value("${aws.region}")
    private String region;

    @Bean(name="connectionFactory")
    public SQSConnectionFactory getSQSConnectionFactory(){
        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).build();
        SQSConnectionFactory factory = new SQSConnectionFactory(new ProviderConfiguration(),amazonSQS);
        return factory;
    }

    @Bean
    public DynamicDestinationResolver getTopicDynamicDestinationResolver(){
        return new DynamicDestinationResolver();
    }

    @Bean(name = "jmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory getDefaultJmsListenerContainerFactory(
            @Autowired SQSConnectionFactory sqsConnectionFactory,
            @Autowired DynamicDestinationResolver destinationResolver){
        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        jmsListenerContainerFactory.setSessionTransacted(false);
        jmsListenerContainerFactory.setConnectionFactory(sqsConnectionFactory);
        jmsListenerContainerFactory.setDestinationResolver(destinationResolver);
        jmsListenerContainerFactory.setConcurrency("1");
        jmsListenerContainerFactory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return jmsListenerContainerFactory;
    }

}
