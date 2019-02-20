package com.lombardrisk.page;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public interface Dropdown {

    String fullName();

    default boolean isDifferentFrom(final String currentOption) {
        return !equalsIgnoreCase(fullName(), currentOption);
    }
}
