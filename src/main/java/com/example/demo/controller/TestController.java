package com.example.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.demo.config.TestJavaConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestJavaConfigBean testJavaConfigBean;

    @RequestMapping("/port")
    public String getPort() {
        return testJavaConfigBean.getPort();
    }

    @SentinelResource(value = "com.example.demo.controller.TestController:test",
            blockHandler = "testblockHandler")
    @RequestMapping("/test")
    public String test() {
        System.out.println("正常执行");
        return "test";
    }

    public String testblockHandler(BlockException ex) {
        System.err.println("流控执行, " + ex);
        return "流控执行";
    }
}
