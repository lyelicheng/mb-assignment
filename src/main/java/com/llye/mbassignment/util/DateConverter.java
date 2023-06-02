package com.llye.mbassignment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter {
    private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);

    public static Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(dateString);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            logger.error("DateConverter.ParseException: error={} ", e.getMessage());
            return null;
        }
    }
}
