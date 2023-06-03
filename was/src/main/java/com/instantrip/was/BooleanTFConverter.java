package com.instantrip.was;

import jakarta.persistence.AttributeConverter;

public class BooleanTFConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "T" : "F";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "T".equals(dbData);
    }
}