package com.zybzyb.liangyuoj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YmlConfig {
    @Value("${path.web}")
    private String web;

    @Value("${path.local}")
    private String local;
}
