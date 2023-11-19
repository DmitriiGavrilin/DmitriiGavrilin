package ru.gavrilin.ip.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gavrilin.ip.dto.Request;

@SpringBootTest
class WaveGuidePathServiceImplTest {
    @Autowired
    private WaveGuidePathService waveGuidePathService;

    @Test
    void process() {
        final var request = Request.builder()
                .frequencyMin(9000000000L)
                .frequencyMax(12000000000L)
                .build();
        final var response = waveGuidePathService.process(request);

        Assertions.assertFalse(response.isEmpty());
        Assertions.assertEquals(4, response.size());
    }
}