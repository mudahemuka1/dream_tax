import java.awt.*;

public class SmileyFaceCanvas extends Canvas implements Drawing {
    private static final int FACE_X = 50;
    private static final int FACE_Y = 50;
    private static final int FACE_DIAMETER = 200;

    public SmileyFaceCanvas() {
        setBackground(Color.WHITE);
    }

    public void paint(Graphics g) {
        draw(g);
    }

    public void draw(Graphics g) {
        // Face Outline
        g.setColor(new Color(139, 69, 19));
        g.drawOval(FACE_X, FACE_Y, FACE_DIAMETER, FACE_DIAMETER);

        // Eyes
        g.setColor(new Color(139, 69, 19));
        int eyeDiameter = 20;
        int eyeY = FACE_Y + 70;
        g.fillOval(FACE_X + 50, eyeY, eyeDiameter, eyeDiameter);
        g.fillOval(FACE_X + FACE_DIAMETER - 50 - eyeDiameter, eyeY, eyeDiameter, eyeDiameter);

        // Mouth (Arc)
        g.setColor(new Color(255, 140, 0));
        int mouthX = FACE_X + 50;
        int mouthY = FACE_Y + 120;
        int mouthWidth = 100;
        int mouthHeight = 50;
        g.drawArc(mouthX, mouthY, mouthWidth, mouthHeight, 200, 140);
    }
}