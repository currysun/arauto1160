package com.lombardrisk.stepdef.transformer;

import cucumber.api.Transformer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.lombardrisk.page.PageUtils.DATE_FORMATTER;
import static java.util.stream.Collectors.toList;

public class ToLocalDates extends Transformer<List<LocalDate>> {

    @Override
    public List<LocalDate> transform(final String dates) {
        String[] parsedDates = dates.split(",\\s*");

        return Arrays.stream(parsedDates)
                .map(date -> LocalDate.parse(date, DATE_FORMATTER))
                .collect(toList());
    }
}
