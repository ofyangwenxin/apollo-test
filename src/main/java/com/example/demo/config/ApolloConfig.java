package com.example.demo.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig
public class ApolloConfig {

    @Bean
    public TestJavaConfigBean javaConfigBean() {
        return new TestJavaConfigBean();
    }

}
