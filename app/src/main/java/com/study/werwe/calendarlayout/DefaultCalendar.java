package com.study.werwe.calendarlayout;

import java.util.Calendar;

/**
 * Created by werwe on 14. 12. 5..
 */
public class DefaultCalendar implements CalendarStrategy {

    Calendar mCalendar;

    private DefaultCalendar() {
        mCalendar = Calendar.getInstance();
    }

    @Override
    public int getMonth() {
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    @Override
    public int getDay() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void setMonth(int month) {
        mCalendar.set(Calendar.MONTH, month - 1);
    }

    @Override
    public void setDay(int day) {
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
    }

    @Override
    public void moveToFirstDayOfFirstWeek() {
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mCalendar.add(Calendar.DAY_OF_MONTH, -mCalendar.get(Calendar.DAY_OF_WEEK));
        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    @Override
    public void moveToLastDayOfLastWeek() {
        mCalendar.set(Calendar.DAY_OF_MONTH, mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        mCalendar.add(Calendar.DAY_OF_MONTH, 7 - mCalendar.get(Calendar.DAY_OF_WEEK));
    }

    @Override
    public void add(int amount) {
        mCalendar.add(Calendar.DAY_OF_MONTH, amount);
    }

    public static DefaultCalendar getInstance() {
        return new DefaultCalendar();
    }
}