package com.llye.mbassignment.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeConverter {
    private static final Logger logger = LoggerFactory.getLogger(TimeConverter.class);

    public static Time convertStringToTime(String timeString) {
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.util.Date utilDate = timeFormat.parse(timeString);
            return new Time(utilDate.getTime());
        } catch (ParseException e) {
            logger.error("TimeConverter.ParseException: error={} ", e.getMessage());
            return null;
        }
    }
}
