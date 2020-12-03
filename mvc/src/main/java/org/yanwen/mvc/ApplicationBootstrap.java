package org.yanwen.mvc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {"org.yanwen"})
@ServletComponentScan(basePackages = {"org.yanwen.filter"})
@EnableCaching
public class ApplicationBootstrap extends SpringBootServletInitializer
{
    public static void main( String[] args )
    {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
