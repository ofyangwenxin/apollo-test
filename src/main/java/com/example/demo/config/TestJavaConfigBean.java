package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class TestJavaConfigBean {

    @Value("${server.port}")
    private String port;

}
