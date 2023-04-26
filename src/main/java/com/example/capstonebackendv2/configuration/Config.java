package com.example.capstonebackendv2.configuration;

import com.example.capstonebackendv2.gui.GUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public GUI getGUI() {
        return new GUI();
    }
}
