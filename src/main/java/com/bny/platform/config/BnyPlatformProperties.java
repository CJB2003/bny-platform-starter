package com.bny.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bny.platform")
@Data
public class BnyPlatformProperties {

    private String correlationHeader = "X-BNY-Correlation-Id";
    private String healthPath = "/platform/health";
    private boolean enabled = true;
}
