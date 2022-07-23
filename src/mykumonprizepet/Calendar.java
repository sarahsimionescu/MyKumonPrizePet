/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.awt.Color;
import java.awt.Graphics;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import static java.time.format.TextStyle.FULL;
import static java.time.format.TextStyle.SHORT;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import static java.util.Locale.CANADA;

/**
 *
 * @author sarahsimionescu
 */
public class Calendar implements UI {

    String month;
    String year;
    int xpos, ypos, width, height;
    Display D;
    DateBox[] dates;
    Color calendarColor;
    boolean active;

    public Calendar(YearMonth bd, int x, int y, int w, int h, Color cc, Display d) {
        month = bd.getMonth().getDisplayName(FULL, CANADA);
        year = String.valueOf(bd.getYear());
        dates = new DateBox[bd.lengthOfMonth()];
        xpos = x;
        ypos = y;
        width = w;
        height = h;
        D = d;
        calendarColor = cc;
        active = true;
        int firstDayOfMonth = LocalDate.of(bd.getYear(), bd.getMonth(), 1).getDayOfWeek().getValue();
        int numOfWeeks = (firstDayOfMonth + dates.length + 5) / 7;
        for (int i = 0; i < dates.length; i++) {
            LocalDate date = LocalDate.of(bd.getYear(), bd.getMonth(), i + 1);
            dates[i] = new DateBox(new Date(date, 0), xpos - width / 2 + date.getDayOfWeek().getValue() * (width / 7), y - height / 2 + (height / (numOfWeeks)) * (int) ((LocalDate.of(bd.getYear(), bd.getMonth(), 1).getDayOfWeek().getValue() + date.getDayOfMonth() + 5) / 7), width / 7, (height / numOfWeeks), calendarColor.brighter(), D);
            if (date.isBefore(D.user.start) || date.isAfter(LocalDate.now())) {
                dates[i].active = false;
                dates[i].date.state = 3;
            } else {
                try {
                    dates[i].date.state = D.user.dates.get(Collections.binarySearch(D.user.dates, dates[i].date, new compareDate())).state;
                } catch (Exception e) {
                    dates[i].date.state = 0;
                }

            }
        }
    }

    @Override
    public void paint(Graphics g) {
        new Text(month + " " + year, xpos, ypos - height / 2 - 80, 30, Color.white, calendarColor, true, D).paint(g);
        for (int i = 1; i <= 7; i++) {
            new Text(DayOfWeek.of(i).getDisplayName(SHORT, CANADA), xpos - (width / 2) + (i - 1) * (width / 7) + (width / 14), ypos - height / 2 - 30, 22, Color.white, calendarColor, true, D).paint(g);
        }
        g.setColor(calendarColor);
        g.fillRoundRect(xpos - (width + 10) / 2, ypos - (height + 10) / 2, width + 10, height + 10, 20, 20);
        for (int i = 0; i < dates.length; i++) {
            dates[i].paint(g);
        }

    }

    void removeDate(Date d) {
        
        clearWeeks(d);
        D.user.dates.remove(Collections.binarySearch(D.user.dates, d, new compareDate()));
        D.user.XP -= 10;
        try {
            addWeeks(D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(d.date.plusDays(1), 1), new compareDate())));
        } catch (Exception e) {

        }
        try {
            addWeeks(D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(d.date.minusDays(1), 1), new compareDate())));
        } catch (Exception e) {

        }
    }

    void addDate(Date d) {
        try {
            clearWeeks(D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(d.date.plusDays(1), 1), new compareDate())));
        } catch (Exception e) {

        }
        try {
            clearWeeks(D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(d.date.minusDays(1), 1), new compareDate())));
        } catch (Exception e) {

        }
        D.user.dates.add(new Date(d.date, 1));
        Collections.sort(D.user.dates, new compareDate());
        D.user.XP += 10;
        addWeeks(d);
        if (D.user.XP > D.user.levelXP) {
            D.user.tokens++;
            D.user.level++;
            D.user.XP -= D.user.levelXP;
        }
    }

    Date min(Date d) {
        Date m = d;
        boolean next = true;
        int n = 0;
        while (next) {
            n++;
            try {
                m = D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(d.date.minusDays(n), 1), new compareDate()));
            } catch (Exception e) {
                next = false;
            }
        }
        return m;
    }

    Date max(Date d) {
        Date m = d;
        boolean next = true;
        int n = 0;
        while (next) {
            n++;
            try {
                m = D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(d.date.plusDays(n), 1), new compareDate()));
            } catch (Exception e) {
                next = false;
            }
        }
        return m;
    }

    void clearWeeks(Date d) {
        Date min = min(d);
        Date max = max(d);
        for (int i = 0; i < ChronoUnit.DAYS.between(min.date, max.date) + 1; i++) {
            D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(min.date.plusDays(i), 1), new compareDate())).state = 1;
        }
        D.user.XP -= 30 * ((int) (ChronoUnit.DAYS.between(min.date, max.date) + 1) / 7);
    }

    void addWeeks(Date d) {
        Date min = min(d);
        Date max = max(d);
        int numOfWeeks = (int) (ChronoUnit.DAYS.between(min.date, max.date) + 1) / 7;
        for (int i = 0; i < numOfWeeks; i++) {
            for (int j = 0; j < 7; j++) {
                D.user.dates.get(Collections.binarySearch(D.user.dates, new Date(min.date.plusDays(i * 7 + j), 1), new compareDate())).state = 2;
            }
        }
        D.user.XP += 30*numOfWeeks;
    }

   

    @Override
    public boolean checkMouse() {
        if (D.mouse.x < xpos + width / 2 && D.mouse.x > xpos - width / 2 && D.mouse.y < ypos + height / 2 && D.mouse.y > ypos - height / 2 && active == true) {
            for (int i = 0; i < dates.length; i++) {
                if (dates[i].checkMouse() == true) {

                    if (dates[i].date.state == 0) {                    
                       addDate(dates[i].date);
                    } else {
                        removeDate(dates[i].date);
                    }
                    return true;
                }
            }

        }
        return false;
    }

    
    @Override
    public void setActive(boolean a)
    {
        active = a;
    }
    @Override
    public boolean getActive()
    {
        return active;
    }
    @Override
    public void setPosition(int x, int y)
    {
        xpos = x;
        ypos = y;
    }
    
    @Override
    public int getPosX()
    {
        return xpos;
    }
    
    @Override
    public int getPosY()
    {
        return ypos;
    }
    
    @Override
    public int getWidth()
    {
        return width;
    }
    
    @Override
    public int getHeight()
    {
        return height;
    }
    
}
