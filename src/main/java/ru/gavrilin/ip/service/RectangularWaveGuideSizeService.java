package ru.gavrilin.ip.service;

import ru.gavrilin.ip.dto.RectangularWaveGuideSize;

import java.math.BigDecimal;
import java.util.List;

public interface RectangularWaveGuideSizeService {
    List<RectangularWaveGuideSize> filter(
            final BigDecimal waveLengthHalf,
            final BigDecimal waveLengthWork
    );

    List<RectangularWaveGuideSize> getAll();

    RectangularWaveGuideSize create(final RectangularWaveGuideSize rectangularWaveGuideSize);

    RectangularWaveGuideSize update(final RectangularWaveGuideSize rectangularWaveGuideSize);
}
