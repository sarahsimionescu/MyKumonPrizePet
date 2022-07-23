/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author sarahsimionescu
 */
public class DateBox implements UI{
    Display D;
    Date date;
    int xpos,ypos,width,height;
    boolean active;
    Color boxColor;
    
    public DateBox(Date dt, int x, int y, int w,int h, Color bc, Display d)
    {
        D = d;
        date = dt;
        xpos = x;
        ypos = y;
        width = w;
        height = h;
        active = true;
        boxColor = bc;
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(new Font("bold", Font.PLAIN, 20));
        if (date.state == 0) {
            g.setColor(boxColor);
        } else if (date.state == 1) {
            g.setColor(new Color(179, 255, 172));
        } else if (date.state == 2) {
            g.setColor(new Color(107, 237, 95));
        } else if (date.state == 3) {
            g.setColor(new Color(200, 200, 200));
        }
        if (checkMouse() == true && active == true) {
            g.setColor(g.getColor().brighter());
        }
        g.fillRect(xpos - width, ypos - height, width, height);
        g.setColor(Color.black);
        g.drawString(String.valueOf(date.date.getDayOfMonth()), xpos - width + 5, ypos - height + 20);
        g.drawRect(xpos - width, ypos - height, width, height);
    }

    @Override
    public boolean checkMouse() {
        if (D.mouse.x < xpos && D.mouse.x > xpos - width && D.mouse.y < ypos && D.mouse.y > ypos - height&& active == true) {
            return true;
        } else {
            return false;
        }
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
