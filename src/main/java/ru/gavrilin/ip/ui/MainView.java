package ru.gavrilin.ip.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import ru.gavrilin.ip.dto.RectangularWaveGuideSize;
import ru.gavrilin.ip.dto.Request;
import ru.gavrilin.ip.service.WaveGuidePathService;

import java.util.Collections;
import java.util.List;


@Slf4j
@Route("")
public class MainView extends VerticalLayout {
    private final WaveGuidePathService waveGuidePathService;

    public MainView(WaveGuidePathService waveGuidePathService) {
        this.waveGuidePathService = waveGuidePathService;
        final var label = new Label("Расчет волноводного тракта");

        final var selectMinFrequency = new Select<>();
        selectMinFrequency.setLabel("Единица измерения");
        selectMinFrequency.setItems("ГГц", "МГц", "Гц");
        selectMinFrequency.setValue("ГГц");
        final var minFrequencyEdit = new NumberField("Минимальная частота");
        minFrequencyEdit.setValue((double) 1);
        minFrequencyEdit.setWidth("300px");
        final var minFrequencyHorizontalLayout = new HorizontalLayout();
        minFrequencyHorizontalLayout.add(minFrequencyEdit, selectMinFrequency);

        final var selectMaxFrequency = new Select<>();
        selectMaxFrequency.setLabel("Единица измерения");
        selectMaxFrequency.setItems("ГГц", "МГц", "Гц");
        selectMaxFrequency.setValue("ГГц");
        final var maxFrequencyEdit = new NumberField("Максимальная частота");
        maxFrequencyEdit.setValue((double) 1);
        maxFrequencyEdit.setWidth("300px");
        final var maxFrequencyHorizontalLayout = new HorizontalLayout();
        maxFrequencyHorizontalLayout.add(maxFrequencyEdit, selectMaxFrequency);

        final var grid = new Grid<>(RectangularWaveGuideSize.class, false);
        grid.addColumn(RectangularWaveGuideSize::getName).setHeader("Наименование");
        grid.addColumn(RectangularWaveGuideSize::getWidth).setHeader("Ширина");
        grid.addColumn(RectangularWaveGuideSize::getHeight).setHeader("Высота");
        grid.addColumn(RectangularWaveGuideSize::getFrequencyMin).setHeader("Минимальная частота");
        grid.addColumn(RectangularWaveGuideSize::getFrequencyMax).setHeader("Максимальная частота");
        grid.setItems(Collections.emptyList());

        final var button = new Button("Рассчитать");
        button.setWidth("300px");
        button.addClickListener(listener -> {
            final var selectedMinFrequency = selectMinFrequency.getValue().toString();
            final var selectedMaxFrequency = selectMaxFrequency.getValue().toString();

            final var frequencyMin = frequency(minFrequencyEdit.getValue(), selectedMinFrequency);
            final var frequencyMax = frequency(maxFrequencyEdit.getValue(), selectedMaxFrequency);
            final var request = Request.builder()
                    .frequencyMin(frequencyMin)
                    .frequencyMax(frequencyMax)
                    .build();
            final var response = waveGuidePathService.process(request);
            grid.setItems(response);
            log.info("Request: {} response size: {}", request, response.size());
        });

        this.add(label, minFrequencyHorizontalLayout, maxFrequencyHorizontalLayout, button, grid);
    }

    private Long frequency(
            final Double value,
            final String type
    ) {
        if (type.equals("Гц")) {
            return value.longValue();
        } else if (type.equals("МГц")) {
            return ((Double) (value * 1000000L)).longValue();
        } else {
            return ((Double) (value * 1000000000L)).longValue();
        }
    }
}
