package assignment.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LineGraph extends JPanel {
	
	List<Integer> list;
	private int[] xValues;
    private int[] yValues;
    
    
	public LineGraph(List<Integer> list) {
		this.list = list;
		xValues = new int[list.size() + 2];
		yValues = new int[list.size() + 2];
		
		for (int i = 0; i < xValues.length; i++) {
			xValues[i] = i;
			yValues[i] = i < list.size() ? list.get(i) : 0;
		}

	}
	
	public void draw() {
		SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Graph Drawing Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new LineGraph(list));
            frame.setSize(1000, 600);
            frame.setVisible(true);
        });
	}
	
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
            int x1 = 50 + (xValues[i] * (width - 10)) / 75;
            int y1 = height - 50 - (yValues[i] * (height - 10)) / 9;
            int x2 = 50 + (xValues[i + 1] * (width - 10)) / 75;
            int y2 = height - 50 - (yValues[i + 1] * (height - 10)) / 9;

//            g.fillOval(x1 - 4, y1 - 4, 2, 2);
            g.drawLine(x1, y1, x2, y2);
            g.drawString("" + i, 50 + (xValues[i] * (width - 10)) / 60, height - 30);
            // Display values at data points
            g.drawString("" + yValues[i], x1 - 20, y1 - 10);
        }
	}
	
}
