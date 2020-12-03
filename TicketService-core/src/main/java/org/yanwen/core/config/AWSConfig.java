package org.yanwen.core.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@Configuration
@Profile({"dev","prod"})
public class AWSConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonSQS getAmazonSQS(){
        return AmazonSQSClientBuilder
                .standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

//    private String amazonS3Bucket="aws.s3.bucket";
//
//    @Bean
//    public StorageService getStorageServiceClass(@Autowired @Qualifier("applicationProperties") PropertiesFactoryBean propertiesFactoryBean) throws IOException {
//        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).build();
//        StorageService storageService = new StorageService(s3Client);
//        storageService.setBucket(propertiesFactoryBean.getObject().getProperty(amazonS3Bucket));
//        return storageService;
//    }
}
