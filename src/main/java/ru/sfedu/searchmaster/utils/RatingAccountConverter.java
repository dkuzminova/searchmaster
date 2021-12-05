package ru.sfedu.searchmaster.utils;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.searchmaster.models.Rating;

public class RatingAccountConverter extends AbstractBeanField<Rating, Integer> {
    private static final Logger log = LogManager.getLogger(RatingAccountConverter.class);

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String indexString = s.substring(1, s.length() - 1);
        Rating rating = new Rating();
        if (!indexString.isEmpty()) {
            rating.setId(Long.parseLong(indexString));
        }
        return rating;
    }

    public String convertToWrite(Object value) {
        Rating rating = (Rating) value;
        StringBuilder builder = new StringBuilder("[");
        builder.append(rating.getId());
        builder.append(",");
        builder.delete(builder.length() - 1, builder.length());
        builder.append("]");
        return builder.toString();
    }
}
