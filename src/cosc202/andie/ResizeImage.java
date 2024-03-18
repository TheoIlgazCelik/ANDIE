package cosc202.andie;

import java.awt.image.BufferedImage;

//import javax.swing.JFrame;

import java.awt.*;

public class ResizeImage implements ImageOperation, java.io.Serializable {

    public double percentageIncrease;

    public BufferedImage apply(BufferedImage input) {
        /* 
        JFrame prompt = new JFrame("Prompt");
        prompt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        */

        percentageIncrease = 2;


        Image resizedImage = input.getScaledInstance((int)(input.getWidth() *percentageIncrease), (int)(input.getHeight() * (percentageIncrease)), 0);
        BufferedImage resizedBufferedImage = new BufferedImage(resizedImage.getWidth(null), resizedImage.getHeight(null), input.getType());
        
        Graphics2D graphics = resizedBufferedImage.createGraphics();
        graphics.drawImage(resizedImage, 0, 0, null);
        

        
        return resizedBufferedImage;
    }
    
}

