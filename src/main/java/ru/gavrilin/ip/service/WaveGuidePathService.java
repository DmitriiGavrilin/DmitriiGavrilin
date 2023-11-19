package ru.gavrilin.ip.service;

import ru.gavrilin.ip.dto.RectangularWaveGuideSize;
import ru.gavrilin.ip.dto.Request;

import java.util.List;

public interface WaveGuidePathService {
    List<RectangularWaveGuideSize> process(final Request request);
}
