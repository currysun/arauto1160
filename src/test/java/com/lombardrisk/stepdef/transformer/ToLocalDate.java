package com.lombardrisk.stepdef.transformer;

import cucumber.api.Transformer;

import java.time.LocalDate;

import static com.lombardrisk.page.PageUtils.DATE_FORMATTER;

public class ToLocalDate extends Transformer<LocalDate> {

    @Override
    public LocalDate transform(final String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}
