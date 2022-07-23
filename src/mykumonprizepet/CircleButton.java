/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * The Button class stores all the needed information to display and locate a
 * button on the user interface.
 *
 * @author Sarah Simionescu
 * @version 1
 */
public class CircleButton extends Button {

    /**
     * Initializes the button and sets a font color that will contrast with it's
     * background color.
     *
     * @param t The text on the button.
     * @param x The x position of the button.
     * @param y The y position of the button.
     * @param s The size of text on the button.
     * @param c The background color of the button.
     * @param a Whether or not you can currently click on the button.
     * @param d Access to the user interface.
     */
    public CircleButton(String t, int x, int y, int s, Color c, Color bc, boolean a, Display d) {
        super(t, x, y, s, c, bc, a, d);

    }

    /**
     * Paints the button on to the user interface.
     *
     * @param g Graphics object used to create graphics.
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        g.setFont(new Font("bold", Font.PLAIN, size));
        D.metrics = g.getFontMetrics(new Font("bold", Font.PLAIN, size));
        width = D.metrics.stringWidth(text) + D.metrics.getAscent();
        height = ((D.metrics.getHeight()) / 2) + D.metrics.getAscent();
        if (checkMouse() == true && active == true) {
            g.setColor(backgroundColor.brighter());
        } else if (active == false) {
            g.setColor(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 100));
        } else {
            g.setColor(backgroundColor);
        }
        g.fillOval(xpos - width / 2, ypos - width / 2, width, width);;
        if (active == false) {
            g.setColor(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 100));
        } else {
            g.setColor(textColor);
        }
        g.drawOval(xpos - width / 2, ypos - width / 2, width, width);

        g.drawString(text, (xpos - width / 2) + (width - D.metrics.stringWidth(text)) / 2, ypos - height / 2 + ((height - D.metrics.getHeight()) / 2) + D.metrics.getAscent());

    }

    /**
     * Checks whether or not the mouse is currently on the button.
     *
     * @return Whether or not the mouse is currently on the button.
     */
    @Override
    public boolean checkMouse() {
        if (sqrt(pow(D.mouse.x-xpos,2)+pow(D.mouse.y-ypos,2)) < width/2) {
            return true;
        } else {
            return false;
        }
    }

}
