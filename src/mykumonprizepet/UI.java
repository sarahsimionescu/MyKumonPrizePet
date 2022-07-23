/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.awt.Graphics;

/**
 *
 * @author sarah
 */
public interface UI {
    public void paint(Graphics g);
    public boolean checkMouse();
    public void setActive(boolean a);
    public boolean getActive();
    public void setPosition(int x, int y);
    public int getPosX();
    public int getPosY();
    public int getWidth();
    public int getHeight();
}
