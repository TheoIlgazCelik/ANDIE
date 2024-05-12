package cosc202.andie;

import java.awt.image.*;

public class VerticalSobelFilter implements ImageOperation, java.io.Serializable{

    public BufferedImage apply(BufferedImage input){
        float[] array = {-0.5f,-1,-0.5f,0,0,0,0.5f,1,0.5f};
        Kernel kernel = new Kernel(3,3,array);
        Convolution convolution = new Convolution(kernel, true);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convolution.filter(input,output);
        
        return output;
 
    }


}