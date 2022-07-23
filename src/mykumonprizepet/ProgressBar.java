/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author sarahsimionescu
 */
public class ProgressBar implements UI {
    Display D;
    int value;
    int range;
    int xpos, ypos, width, height;
    boolean active;
    Color valueColor;
    Color barColor;
    
    public ProgressBar(int v, int r, int x, int y, int w, int h, Color vc, Color bc, Display d)
    {
        D=d;
        value =v;
        range = r;
        xpos = x;
        ypos = y;
        width = w;
        height = h;
        valueColor = vc;
        barColor = bc;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(barColor);
        g.fillRoundRect(xpos - width / 2, ypos - height / 2, width, height, 20,20);
        g.setColor(valueColor);
        g.fillRoundRect(xpos-width/2, ypos-height/2, (int)new MathTool().map(value, 0, range, 0, width), height, 20,20);
        g.setColor(valueColor);
        g.drawRoundRect(xpos - width / 2, ypos - height / 2, width, height, 20,20);
    }

    @Override
    public boolean checkMouse() {
        if (D.mouse.x < xpos + width / 2 && D.mouse.x > xpos - width / 2 && D.mouse.y < ypos + height / 2 && D.mouse.y > ypos - height / 2 && active == true) {
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
