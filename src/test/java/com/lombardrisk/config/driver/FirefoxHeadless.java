package com.lombardrisk.config.driver;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class FirefoxHeadless extends FirefoxDriverProvider {

    @Override
    List<String> getExtraArguments() {
        return ImmutableList.of(
                "-headless",
                "disable-gpu",
                "window-size=1920,1080");
    }
}