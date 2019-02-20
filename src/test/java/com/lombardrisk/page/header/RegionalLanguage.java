package com.lombardrisk.page.header;

import com.lombardrisk.page.Dropdown;

@SuppressWarnings("unused")
public enum RegionalLanguage implements Dropdown {
    ZH_CN("China / Chinese"),
    EN_GB("United Kingdom / English"),
    EN_US("United States / English");

    private final String fullName;

    RegionalLanguage(final String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String fullName() {
        return fullName;
    }
}
