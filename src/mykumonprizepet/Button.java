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

/**
 * The Button class stores all the needed information to display and locate a
 * button on the user interface.
 *
 * @author Sarah Simionescu
 * @version 1
 */
public class Button extends Text {

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
    public Button(String t, int x, int y, int s, Color c, Color bc, boolean a, Display d) {
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
        g.fillRect(xpos - width / 2, ypos - height / 2, width, height);
        if (active == false) {
            g.setColor(new Color(textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 100));
        } else {
            g.setColor(textColor);
        }
        g.drawRect(xpos - width / 2, ypos - height / 2, width, height);

        g.drawString(text, (xpos - width / 2) + (width - D.metrics.stringWidth(text)) / 2, ypos - height / 2 + ((height - D.metrics.getHeight()) / 2) + D.metrics.getAscent());

    }

}
