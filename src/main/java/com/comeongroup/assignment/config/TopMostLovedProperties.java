package com.comeongroup.assignment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("params.top-count")
public class TopMostLovedProperties {
    private Integer count;
}