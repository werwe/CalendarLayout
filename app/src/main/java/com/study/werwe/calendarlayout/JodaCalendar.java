package com.study.werwe.calendarlayout;

import org.joda.time.MutableDateTime;

/**
 * Created by werwe on 14. 12. 5..
 */
public class JodaCalendar implements CalendarStrategy {
    private MutableDateTime mDateTime;

    private JodaCalendar() {
        mDateTime = MutableDateTime.now();
    }

    public static CalendarStrategy getInstance() {
        return new JodaCalendar();
    }

    @Override
    public int getMonth() {
        return mDateTime.getMonthOfYear();
    }

    @Override
    public int getDay() {
        return mDateTime.getDayOfMonth();
    }

    @Override
    public void setMonth(int month) {
        mDateTime.setMonthOfYear(month);
    }

    @Override
    public void setDay(int day) {
        mDateTime.setDayOfMonth(day);
    }

    @Override
    public void moveToFirstDayOfFirstWeek() {
        mDateTime.setDayOfMonth(1);
        mDateTime.addDays(-mDateTime.getDayOfWeek());
    }

    @Override
    public void moveToLastDayOfLastWeek() {
        mDateTime.setDayOfMonth(mDateTime.dayOfMonth().getMaximumValue());
        mDateTime.addDays(6 - mDateTime.getDayOfWeek());
    }

    @Override
    public void add(int amount) {
        mDateTime.addDays(amount);
    }
}
