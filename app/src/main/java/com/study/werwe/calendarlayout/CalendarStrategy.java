package com.study.werwe.calendarlayout;

import java.util.Calendar;

/**
 * Created by werwe on 14. 12. 5..
 */
public interface CalendarStrategy {

    public int getMonth(); //1~12

    public int getDay(); //1~28,31

    public void setMonth(int month);

    public void setDay(int day);

    public void moveToFirstDayOfFirstWeek();

    public void moveToLastDayOfLastWeek();

    public void add(int amount);
}
