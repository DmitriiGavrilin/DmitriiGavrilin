package ru.gavrilin.ip.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilin.ip.dto.RectangularWaveGuideSize;
import ru.gavrilin.ip.dto.Request;
import ru.gavrilin.ip.service.WaveGuidePathService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WaveGuidePathController {
    private final WaveGuidePathService waveGuidePathService;

    @PostMapping(value = "/v1/process", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RectangularWaveGuideSize> process(@RequestBody Request request) {
        return waveGuidePathService.process(request);
    }
}
