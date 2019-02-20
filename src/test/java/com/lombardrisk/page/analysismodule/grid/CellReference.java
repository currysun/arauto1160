package com.lombardrisk.page.analysismodule.grid;

import java.util.Objects;

public class CellReference {

    private final String name;
    private final String description;

    public CellReference(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellReference that = (CellReference) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
