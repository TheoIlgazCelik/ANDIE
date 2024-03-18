package cosc202.andie;

import java.awt.image.*;

public class ColourCycle implements ImageOperation, java.io.Serializable {


    ColourCycle() {
    }

    public BufferedImage apply (BufferedImage input){
        for (int y = 0; y < input.getHeight(); ++y){
            for (int x = 0; x < input.getWidth(); ++x){
                int argb = input.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                
            }
        }

        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        return output;
    }

}