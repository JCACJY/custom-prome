package com.custom.prome.config;


import com.custom.prome.aspect.MicrometerAspect;
import com.custom.prome.util.PromeUtils;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class PromeJavaConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return registry -> {
            try {
                registry.config().commonTags("application", PromeUtils.strIsEmpty(applicationName) ?"none":applicationName,"hostname", InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public MicrometerAspect micrometerAspect(){
        return new MicrometerAspect();
    }
}
