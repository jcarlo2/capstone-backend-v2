package com.capstone.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CapstoneBackendV2Application.class, loader=CustomSpringApplicationContextLoader.class)
@SpringBootTest
class CapstoneBackendV2Application {

    @Test
    void contextLoads() {
    }

}
