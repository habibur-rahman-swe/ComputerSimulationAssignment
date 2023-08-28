package assignment.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LineGraph extends JPanel {

	private int[] xValues;
    private int[] yValues;
    
    public LineGraph(int[] a, int[] b) {
    	xValues = a;
    	yValues = b;
    }
	
	public void draw() {
		
		System.out.println(xValues.length);
		
		SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph Drawing Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new LineGraph(xValues, yValues));
            frame.setSize(1000, 500);
            frame.setVisible(true);
        });
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int xDiv = 30;
        int yDiv = 60;
        
        // Draw x and y axes
        g.drawLine(50, height - 50 - 50, width - 50, height - 50 - 50);
        g.drawLine(50, height - 50, 50, 50);

        // Draw data points and connect them with lines
        g.setColor(Color.RED);
        for (int i = 0; i < xValues.length - 1; i++) {
            int x1 = 50 + (i * (width - 10)) / xDiv;
            int y1 = height - 50 - (yValues[i] * (height - 10)) / yDiv;
            int x2 = 50 + ((i + 1) * (width - 10)) / xDiv;
            int y2 = height - 50 - (yValues[i + 1] * (height - 10)) / yDiv;
            
//            g.fillOval(x1 - 4, y1 - 4, 2, 2);
            g.drawLine(x1, y1 - 50, x2, y2 - 50);
            g.drawString("" + (i + 1), 50 + (i * (width - 9)) / xDiv, height - 30 - 50);
            // Display values at data points
            g.drawString("" + Math.abs(yValues[i]), 50 + (i * (width - 10)) / xDiv, y1 - 10 - 50);
        }
	}
	
}
