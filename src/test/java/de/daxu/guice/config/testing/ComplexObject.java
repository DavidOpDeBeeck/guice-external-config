package de.daxu.guice.config.testing;

public class ComplexObject {

    private String stringField;

    @SuppressWarnings("unused")
    private ComplexObject() {
        // used by jackson
    }

    public ComplexObject(String stringField) {
        this.stringField = stringField;
    }

    public String getStringField() {
        return stringField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexObject that = (ComplexObject) o;

        return stringField != null ? stringField.equals(that.stringField) : that.stringField == null;
    }

    @Override
    public int hashCode() {
        return stringField != null ? stringField.hashCode() : 0;
    }
}