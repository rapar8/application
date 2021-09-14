package com.mywarehouse.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@Log4j2
public class MessageSourceConfiguration {

    @Value("${application.message-sources:sp-sales-assistant-config-repo/messages/msg}")
    String messageSourceLocation;

    @Bean
    public MessageSource messageSource() {
        log.info("Local profile messageSource");
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messageSourceLocation);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
