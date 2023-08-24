package assignment.drawing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphDrawing extends JPanel {

    private int[] xValues = {1, 5, 7, 9, 10, 25, 29};
    private int[] yValues = {2, 5, 58, 93, 54, 51, 29};

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        // Draw x and y axes
        g.drawLine(50, height - 50, width - 50, height - 50);
        g.drawLine(50, height - 50, 50, 50);

        // Draw data points and connect them with lines
        g.setColor(Color.RED);
        for (int i = 0; i < xValues.length - 1; i++) {
            int x1 = 50 + (xValues[i] * (width - 100)) / 29;
            int y1 = height - 50 - (yValues[i] * (height - 100)) / 93;
            int x2 = 50 + (xValues[i + 1] * (width - 100)) / 29;
            int y2 = height - 50 - (yValues[i + 1] * (height - 100)) / 93;

            g.fillOval(x1 - 4, y1 - 4, 8, 8);
            g.drawLine(x1, y1, x2, y2);

            // Display values at data points
            g.drawString("(" + xValues[i] + ", " + yValues[i] + ")", x1 - 20, y1 - 10);
        }

        // Draw the last data point
        int x = 50 + (xValues[xValues.length - 1] * (width - 100)) / 29;
        int y = height - 50 - (yValues[yValues.length - 1] * (height - 100)) / 93;
        g.fillOval(x - 4, y - 4, 8, 8);
        g.drawString("(" + xValues[xValues.length - 1] + ", " + yValues[yValues.length - 1] + ")", x - 20, y - 10);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph Drawing Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GraphDrawing());
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}