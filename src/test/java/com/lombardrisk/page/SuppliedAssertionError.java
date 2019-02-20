package com.lombardrisk.page;

import java.util.function.Supplier;

public class SuppliedAssertionError extends AssertionError {

    private static final long serialVersionUID = -1320256790818786936L;

    public SuppliedAssertionError(final Supplier<String> messageSupplier) {
        super(messageSupplier.get());
    }
}
