package cosc202.andie;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import cosc202.andie.ImagePanel;



public class ImagePanelTest {

    // Dummy test to see if JUnit works
    @Test
    void initialDummyTest(){

    }

    /**
     * <p>
     * Test method for expected zoom
     * </p>
     */
    @Test
    void getZoomInitialValue(){
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
    }

    /**
     * <p>
     * Test method for zoom after setting value
     * </p>
     */
    @Test
    void getZoomAftersetZoom(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(0.0);
        Assertions.assertFalse(testPanel.getZoom() == 100.0);
        Assertions.assertTrue(testPanel.getZoom() >= 50.0);
    }
}
