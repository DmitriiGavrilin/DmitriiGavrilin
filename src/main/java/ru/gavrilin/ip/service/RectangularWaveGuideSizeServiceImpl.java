package ru.gavrilin.ip.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gavrilin.ip.config.AppConfiguration;
import ru.gavrilin.ip.dto.RectangularWaveGuideSize;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RectangularWaveGuideSizeServiceImpl implements RectangularWaveGuideSizeService {
    private final AppConfiguration appConfiguration;
    private final List<RectangularWaveGuideSize> rectangularWaveGuideSizeList = new ArrayList<>();

    @PostConstruct
    private void init() throws IOException {
        final var fileLocation = appConfiguration.getFileLocation();
        final var file = new File(fileLocation);
        if (!file.exists()) {
            throw new RuntimeException("File not found, path: " + fileLocation);
        }

        Files.readAllLines(Paths.get(file.getAbsolutePath())).forEach(fileLine -> {
            final var fileParams = fileLine.split(";");
            final var rectangularWaveGuideSize = RectangularWaveGuideSize.builder()
                    .name(fileParams[0])
                    .width(new BigDecimal(fileParams[1]))
                    .height(new BigDecimal(fileParams[2]))
                    .frequencyMin(Long.valueOf(fileParams[3]))
                    .frequencyMax(Long.valueOf(fileParams[4]))
                    .build();
            rectangularWaveGuideSizeList.add(rectangularWaveGuideSize);
        });
        log.info("Data file read completed, size: {}", rectangularWaveGuideSizeList.size());
    }

    @Override
    public List<RectangularWaveGuideSize> filter(
            final BigDecimal waveLengthHalf,
            final BigDecimal waveLengthWork
    ) {
        return rectangularWaveGuideSizeList.stream().filter(rectangularWaveGuideSize -> {
            return rectangularWaveGuideSize.getWidth().compareTo(waveLengthHalf.multiply(BigDecimal.valueOf(1000))) >= 0
                    && rectangularWaveGuideSize.getWidth().compareTo(waveLengthWork.multiply(BigDecimal.valueOf(1000))) <= 0;
        }).toList();
    }

    @Override
    public List<RectangularWaveGuideSize> getAll() {
        return null;
    }

    @Override
    public RectangularWaveGuideSize create(RectangularWaveGuideSize rectangularWaveGuideSize) {
        return null;
    }

    @Override
    public RectangularWaveGuideSize update(RectangularWaveGuideSize rectangularWaveGuideSize) {
        return null;
    }
}
