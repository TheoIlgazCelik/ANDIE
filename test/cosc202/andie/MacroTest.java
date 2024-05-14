package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

// JUnit
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Stack;
import java.util.Iterator;

public class MacroTest {
    
    @Test
    void macroStackTest(){

        // creates the stack to compare it to
        Stack<ImageOperation> trueStack = new Stack<>();
        EditableImage testImage = new EditableImage(); //the test image
        try{
        testImage.open("test/test-image.png");
        } catch (Exception e){
            System.out.println("error");
        }
        //Add these stack items
        trueStack.add(new SharpenFilter());
        trueStack.add(new GaussianBlur());
        trueStack.add(new HorizontalSobelFilter());
        trueStack.add(new Crop(20,20,10,10));
        trueStack.add(new MedianFilter(8));
        trueStack.add(new ResizeImage(30));
        trueStack.add(new InvertColor());
        // The test stack
        testImage.apply(new SharpenFilter());
        testImage.apply(new GaussianBlur());
        testImage.apply(new HorizontalSobelFilter());
        testImage.apply(new Crop(20,20,10,10));
        testImage.apply(new MedianFilter(8));
        testImage.apply(new ResizeImage(30));
        testImage.apply(new InvertColor());
        //The assertion test
        Iterator<ImageOperation> test = testImage.getMacroStack().iterator();
        Iterator<ImageOperation> proper = trueStack.iterator();
        while(test.hasNext()){
            ImageOperation expected = proper.next();
            ImageOperation actual = test.next();
            assertEquals(expected, actual);
        }

        
    }

}
