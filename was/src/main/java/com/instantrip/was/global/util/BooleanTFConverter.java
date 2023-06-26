package com.instantrip.was.global.util;

import jakarta.persistence.AttributeConverter;

public class BooleanTFConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null)
            return "T";
        return (attribute != null && attribute) ? "T" : "F";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "T".equals(dbData);
    }
}
