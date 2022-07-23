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
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author sarah
 */
public class GIF extends Graphic{
    Image[] frame;
    int frameNum;

    public GIF (int x, int y, double w, double h, String f, int r, Display d) {
        super(x, y, w, h, f, d);
        frame = new Image[new File("PNG/" + f + "/" + f + " (" + r + ")").listFiles().length];
        for (int i = 0; i < frame.length; i++) {
            myIcon = new ImageIcon("PNG/" + f + "/" + f + " (" + r + ")/" + f + " (" + r + ")-" + i + ".png");
            
            frame[i] = myIcon.getImage();     
            if (w > 0 && h > 0 && w != 1 && h != 1) {              
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(),(int) (myIcon.getIconWidth() * w),(int) (myIcon.getIconHeight() * h)));
                frame[i] = myIcon.getImage();
            }
        }
        width = myIcon.getIconWidth();
        height = myIcon.getIconHeight();
        xpos = x - width / 2;
        ypos = y - height / 2;
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

    @Override
    public void paint(Graphics g) {

        animate();
        g.drawImage(frame[frameNum], xpos, ypos, D.displayScreen);
    }

    public void position(int x, int y) {
        xpos = x;
        ypos = y;
    }

    @Override
    public boolean checkMouse() {
        if (D.mouse.x < xpos + width / 2 && D.mouse.x > xpos - width / 2 && D.mouse.y < ypos + height / 2 && D.mouse.y > ypos - height / 2) {
            return true;
        } else {
            return false;
        }
    }
    
     public void animate()
    {
        frameNum = (D.counter % (frame.length*10))/10;
    }

}
