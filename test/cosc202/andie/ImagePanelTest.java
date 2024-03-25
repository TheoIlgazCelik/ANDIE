package test;

import org.junit.jupiter.api.Assertions;
import cosc202.andie.ImagePanel;
import org.junit.jupiter.api.Test;

public class ImagePanelTest {

    @Test
    void initialDummyTest(){

    }

    @Test
    void getZoomInitialValue(){
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
    }
    @Test
    void getZoomAftersetZoom(){
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(0.0);
        Assertions.assertFalse(testPanel.getZoom() == 100.0);
        Assertions.assertTrue(testPanel.getZoom() >= 50.0);
    }
}
