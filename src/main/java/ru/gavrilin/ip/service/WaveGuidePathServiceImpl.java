package ru.gavrilin.ip.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gavrilin.ip.dto.RectangularWaveGuideSize;
import ru.gavrilin.ip.dto.Request;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WaveGuidePathServiceImpl implements WaveGuidePathService {
    private final RectangularWaveGuideSizeService rectangularWaveGuideSizeService;
    private static final Long SPEED_OF_LIGHT = 300000000L;

    @Override
    public List<RectangularWaveGuideSize> process(Request request) {
        log.info("Process request: {}", request);

        final var waveLengthMax = processWaveLength(request.getFrequencyMin());
        final var waveLengthMin = processWaveLength(request.getFrequencyMax());

        final var frequencyWork = BigDecimal.valueOf(request.getFrequencyMin()).add(BigDecimal.valueOf(request.getFrequencyMax())).divide(BigDecimal.valueOf(2), 4, RoundingMode.UP);
        final var waveLengthWork = BigDecimal.valueOf(SPEED_OF_LIGHT).divide(frequencyWork, 4, RoundingMode.UP);
        final var waveLengthHalf = waveLengthWork.divide(BigDecimal.valueOf(2), 4, RoundingMode.UP);

        log.info("wave min: {}, max: {}, frequencyWork: {}, waveLengthWork: {}, waveLengthHalf: {}",
                waveLengthMin,
                waveLengthMax,
                frequencyWork,
                waveLengthWork,
                waveLengthHalf
        );

        return rectangularWaveGuideSizeService.filter(
                waveLengthHalf,
                waveLengthWork
        );
    }

    private BigDecimal processWaveLength(final long frequency) {
        return BigDecimal.valueOf(SPEED_OF_LIGHT).divide(BigDecimal.valueOf(frequency), 4, RoundingMode.UP);
    }
}
