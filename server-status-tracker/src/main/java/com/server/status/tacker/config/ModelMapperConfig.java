package com.server.status.tacker.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Created 25/06/2024 - 09:58
 * @Package com.server.status.tacker.config
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
