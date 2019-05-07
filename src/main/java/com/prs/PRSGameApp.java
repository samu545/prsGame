/*
 * Copyright (C) SAM - All Rights Reserved.
 * Proprietary and confidential.
 */
package com.prs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class PRSGameApp extends WebMvcAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(PRSGameApp.class);

    public static void main(String[] args) {
        logger.info("Start Paper-Rock-Scissors game: ");
        configureApplication(new SpringApplicationBuilder()).run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(PRSGameApp.class).bannerMode(Banner.Mode.OFF);
    }
}


