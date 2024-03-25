package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to Resize an Immage.
 * </p>
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @see java.awt.image.BufferedImage
 * @author Ilgaz Celik
 * @version 1.0
 */
public class ResizeImage implements ImageOperation, java.io.Serializable {

    /**
     * The percentage change of the resize. A value of 100 is the same size, anything less than 100 is smaller, anything larger than 100 is larger.
     */
    private int percentageIncrease100;

    

    /**
     * @param percentageIncrease100
     * Constructor for percentageIncrease
     */
    ResizeImage(int percentageIncrease100){
        this.percentageIncrease100=percentageIncrease100;
    }


    /**
     * <p>
     * Resize the image with the given percentageIncrease100.
     * </p>
     * 
     * 
     * @param percentgaeIncrease100 the percentage value of the resize
     */
    ResizeImage(){
            percentageIncrease100=100;
    }

    /**
     * <p>
     * Resize an image.
     * </p>
     * 
     * <p>
     * Image Resize is using getScaledInstance which is a part of the BufferedImage class 
     * </p>
     * 
     * @param input The image to apply the Resize to.
     * @return The resulting (resized) image.
     */
    public BufferedImage apply(BufferedImage input) {

        //if percentageIncrease100 is the same size, return the input
        if (percentageIncrease100 == 100) {
            return input;
        }

        // changes the number from int to double and divides by 100
        double percentageIncrease = (double)percentageIncrease100/100;

        int operation;

        // if the resize is larger, use SCALE_SMOOTH, else use SCALE_AREA_AVERAGING
        if (percentageIncrease > 1) {
            operation = Image.SCALE_SMOOTH;
        } else {
            operation = Image.SCALE_AREA_AVERAGING;
        }

        // creating a new image with new size
        Image resizedImage = input.getScaledInstance((int)(input.getWidth() *percentageIncrease), (int)(input.getHeight() * (percentageIncrease)), operation);//gets scaled version of image
        BufferedImage resizedBufferedImage = new BufferedImage(resizedImage.getWidth(null), resizedImage.getHeight(null), input.getType());
        

        // draws image
        Graphics2D graphics = resizedBufferedImage.createGraphics();
        graphics.drawImage(resizedImage, 0, 0, null);
        

        
        return resizedBufferedImage;
    }
    
}

