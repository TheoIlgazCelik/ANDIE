package cosc202.andie;

import java.awt.image.*;
import java.util.*;

public class EmbossFilterNorth implements ImageOperation, java.io.Serializable{

    EmbossFilterNorth(){}

    public BufferedImage apply(BufferedImage input){
        float[] array = {0,1,0,0,0,0,0,-1,0};
        Kernel kernel = new Kernel(3,3,array);
        Convolution convolution = new Convolution(kernel, true);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convolution.filter(input,output);
        
        return output;
 
    }


}