/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mykumonprizepet;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * The Graphic class stores all the needed information to display and locate a
 * .png file on the user interface.
 *
 * @author Sarah Simionescu
 * @version 1
 */
public class Graphic implements UI {

    /**
     * Access to user interface.
     */
    protected Display D;
    /**
     * The name of the .png file.
     */
    String fileName;
    /**
     * The x position of the graphic.
     */
    int xpos;
    /**
     * The y position of the graphic.
     */
    int ypos;
    /**
     * The width of the graphic.
     */
    int width;
    /**
     * The height of the graphic.
     */
    int height;
    /**
     * The object used to initialize and resize the graphic.
     */
    ImageIcon myIcon;
    /**
     * The object where the graphic is stored.
     */
    Image image;
    /**
     * Whether or not you can click on the object.
     */
    boolean active;

    /**
     * Initializes and, if desired, resizes the graphic.
     *
     * @param x The x position of the graphic.
     * @param y The y position of the graphic.
     * @param w The new width of the graphic.
     * @param h The new height of the graphic.
     * @param f The name of the .png file.
     * @param d Accesses the user interface.
     */
    public Graphic(int x, int y, double w, double h, String f, Display d) {
        D = d;
        active = true;
        if (this.getClass().getName().equals("mykumonprizepet.Graphic")) {
            myIcon = new ImageIcon("PNG/" + f + ".png");
            image = myIcon.getImage();
            if (w > 0 && h > 0 && w != 1 && h != 1) {
                   myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), (int) (myIcon.getIconWidth() * w), (int) (myIcon.getIconHeight() * h)));

                image = myIcon.getImage();
            }
            width = myIcon.getIconWidth();
            height = myIcon.getIconHeight();
            xpos = x - width / 2;
            ypos = y - height / 2;
        }
    }

    /**
     * Resizes an image.
     *
     * @param srcImg Image to be resized.
     * @param w New width of image.
     * @param h New height of image.
     * @return Returns the resized image.
     */
    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    /**
     * Paints the graphic on to the user interface.
     * @param g Graphics object used to create graphics.
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(image, xpos, ypos, D.displayScreen);
    }
    
    /**
     * Checks whether or not the mouse is currently on the graphic.
     * @return Whether or not the mouse is currently on the graphic.
     */
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
