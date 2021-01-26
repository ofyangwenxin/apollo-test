package com.example.demo.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

public class ApolloDataSourceListener implements InitializingBean {

    private static final String FLOW_RULE_TYPE = "flow";

    private static final String DEGRADE_RULE_TYPE = "degrade";

    private static final String PARAM_FLOW_RULE_TYPE = "param-flow";

    private static final String SYSTEM_RULE_TYPE = "system";

    private static final String AUTHORITY_RULE_TYPE = "authority";

    // *-flow-rules
    private static final String FLOW_DATA_ID_POSTFIX = "-" + FLOW_RULE_TYPE + "-rules";

    // *-degrade-rules
    private static final String DEGRADE_DATA_ID_POSTFIX = "-" + DEGRADE_RULE_TYPE + "-rules";

    // *-param-flow-rules
    private static final String PARAM_FLOW_DATA_ID_POSTFIX = "-" + PARAM_FLOW_RULE_TYPE + "-rules";

    // *-system-rules
    private static final String SYSTEM_DATA_ID_POSTFIX = "-" + SYSTEM_RULE_TYPE + "-rules";

    // *-authority-rules
    private static final String AUTHORITY_DATA_ID_POSTFIX = "-" + AUTHORITY_RULE_TYPE + "-rules";

    private String applicationName;

    public ApolloDataSourceListener(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initFlowRules();
        initDegradeRules();
        initParamFlowRules();
        initSystemRules();
        initAuthorityRules();
    }

    /**
     * 黑白名单空值
     */
    private void initAuthorityRules() {
        String authorityRuleKey = applicationName + AUTHORITY_DATA_ID_POSTFIX;
        // 动态监听
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleDataSource =
                new ApolloDataSource<>("application",
                        authorityRuleKey,
                        "[]",
                        source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                        }));
        // 刷新内存
        AuthorityRuleManager.register2Property(authorityRuleDataSource.getProperty());
    }

    /**
     * 系统自适应限流
     */
    private void initSystemRules() {
        String systemRuleKey = applicationName + SYSTEM_DATA_ID_POSTFIX;
        // 动态监听
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource =
                new ApolloDataSource<>("application",
                        systemRuleKey,
                        "[]",
                        source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                        }));
        // 刷新内存
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
    }

    /**
     * 热点参数限流
     */
    private void initParamFlowRules() {
        String paramFlowRuleKey = applicationName + PARAM_FLOW_DATA_ID_POSTFIX;
        // 动态监听
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleDataSource =
                new ApolloDataSource<>("application",
                        paramFlowRuleKey,
                        "[]",
                        source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                        }));
        // 刷新内存
        ParamFlowRuleManager.register2Property(paramFlowRuleDataSource.getProperty());
    }

    /**
     * 熔断降级
     */
    private void initDegradeRules() {
        String degradeRuleKey = applicationName + DEGRADE_DATA_ID_POSTFIX;
        // 动态监听
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource =
                new ApolloDataSource<>("application",
                        degradeRuleKey,
                        "[]",
                        source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                        }));
        // 刷新内存
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
    }

    /**
     * 流量控制
     */
    private void initFlowRules() {
        //apollo-test-flow-rules
        String flowRuleKey = applicationName + FLOW_DATA_ID_POSTFIX;
        // 动态监听
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource =
                new ApolloDataSource<>("application",
                        flowRuleKey,
                        "[]",
                        source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                        }));
        // 刷新内存
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

}
