package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class ResizeImage implements ImageOperation, java.io.Serializable {


    private int percentageIncrease100;//percentage increase as an int

    

    /**
     * @param percentageIncrease100
     * Constructor for percentageIncrease
     */
    ResizeImage(int percentageIncrease100){
        this.percentageIncrease100=percentageIncrease100;
    }


    /**
     * Blank constructor
     */
    ResizeImage(){
            percentageIncrease100=100;
    }


    public BufferedImage apply(BufferedImage input) {
        double percentageIncrease = (double)percentageIncrease100/100; // changes the number from int to double and divides by 100

        Image resizedImage = input.getScaledInstance((int)(input.getWidth() *percentageIncrease), (int)(input.getHeight() * (percentageIncrease)), 0);//gets scaled version of image
        BufferedImage resizedBufferedImage = new BufferedImage(resizedImage.getWidth(null), resizedImage.getHeight(null), input.getType());
        
        Graphics2D graphics = resizedBufferedImage.createGraphics();
        graphics.drawImage(resizedImage, 0, 0, null);
        

        
        return resizedBufferedImage;
    }
    
}

