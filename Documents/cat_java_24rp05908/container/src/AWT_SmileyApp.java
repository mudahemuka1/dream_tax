import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AWT_SmileyApp {
    public static void main(String[] args) {
        Frame frame = new Frame("My shapes");
        SmileyFaceCanvas canvas = new SmileyFaceCanvas();

        frame.add(canvas);
        frame.setSize(310, 340);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}