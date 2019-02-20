package com.lombardrisk.config.driver;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class ChromeGui extends ChromeDriverProvider {

    @Override
    List<String> getExtraArguments() {
        return ImmutableList.of("start-maximized");
    }
}
