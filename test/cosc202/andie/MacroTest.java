package cosc202.andie;

// JUnit
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Stack;
import java.util.Iterator;



/**
 * <p>
 * Test class to test recording of macros, through the use of stacks to determine whether the test stack of 
 * operations matches the expected stack of operations when recording macros
 * </p>
 * 
 * @author Finn Rimmer
 * @version 1.0
 */
public class MacroTest {
    
    /**
     * <p> Test method for {@link MacroActions}.
     * Creates test stack and a real stack of operations 
     * testing to see whether they are identical
     * </p>
     */
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
