package cosc202.andie;

import java.awt.image.*;

public class EmbossFilterSouthWest implements ImageOperation, java.io.Serializable{

    public BufferedImage apply(BufferedImage input){
        float[] array = {-1,0,0,0,0,0,0,0,1};
        Kernel kernel = new Kernel(3,3,array);
        Convolution convolution = new Convolution(kernel, true);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convolution.filter(input,output);
        
        return output;
 
    }


}