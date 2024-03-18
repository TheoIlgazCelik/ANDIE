package cosc202.andie;

import java.awt.image.*;
import java.util.Arrays;

public class MedianFilter implements ImageOperation, java.io.Serializable {

    private int radius;

    MedianFilter(int radius) {
        this.radius = radius;
    }

    MedianFilter() {
        this(1);
    }

    public BufferedImage apply(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        int size = (2 * radius + 1) * (2 * radius + 1);

        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {

                int[] neighbourhoodValuesA = new int[size];
                int[] neighbourhoodValuesR = new int[size];
                int[] neighbourhoodValuesG = new int[size];
                int[] neighbourhoodValuesB = new int[size];

                for (int i = -radius, count = 0; i <= radius; i++) {
                    for (int j = -radius; j <= radius; j++, count++) {
                        int xVal = x + j;
                        int yVal = y + i;

                        // check for x,y outside width,height
                        if (xVal < 0) {
                            xVal = 0;
                        } else if (xVal >= input.getWidth()) {
                            xVal = input.getWidth() - 1;
                        }

                        if (yVal < 0) {
                            yVal = 0;
                        } else if (yVal >= input.getHeight()) {
                            yVal = input.getHeight() - 1;
                        }

                        int argb = input.getRGB(xVal, yVal);
                        int a = (argb & 0xFF000000) >> 24;
                        int r = (argb & 0x00FF0000) >> 16;
                        int g = (argb & 0x0000FF00) >> 8;
                        int b = (argb & 0x000000FF);

                        neighbourhoodValuesA[count] = a;
                        neighbourhoodValuesR[count] = r;
                        neighbourhoodValuesG[count] = g;
                        neighbourhoodValuesB[count] = b;
                    }
                }

                Arrays.sort(neighbourhoodValuesA);
                Arrays.sort(neighbourhoodValuesR);
                Arrays.sort(neighbourhoodValuesG);
                Arrays.sort(neighbourhoodValuesB);

                int medianA = neighbourhoodValuesA[size / 2];
                int medianR = neighbourhoodValuesR[size / 2];
                int medianG = neighbourhoodValuesG[size / 2];
                int medianB = neighbourhoodValuesB[size / 2];

                int argb = (medianA << 24) | (medianR << 16) | (medianG << 8) | medianB;

                output.setRGB(x, y, argb);
            }

        }
        
        return output;
    }

}