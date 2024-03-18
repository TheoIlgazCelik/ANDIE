package cosc202.andie;

import java.awt.image.*;

public class SharpenFilter implements ImageOperation, java.io.Serializable{

    SharpenFilter(){};

    public BufferedImage apply(BufferedImage input){
        float[] array = {0,-0.5f,0,-0.5f,3,-0.5f,0,-0.5f,0};
        Kernel kernel = new Kernel(3,3,array);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(input.getColorModel(), input.copyData(null), input.isAlphaPremultiplied(), null);
        convOp.filter(input,output);
        return output;
    }
    

}
