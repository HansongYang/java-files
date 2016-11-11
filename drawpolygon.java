import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 

public class drawpolygon extends JFrame {
	int[] x = new int[4];
	int[] y = new int[4];
	public drawpolygon(){
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				x[0] = e.getX();
				x[1] = e.getY();
				x[2] = 90;
				x[3] = e.getX();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter(){			
			public void mouseDragged(MouseEvent e){
				y[0] = e.getY();
				y[1] = 50;
				y[2] = e.getX();
				y[3] = e.getY();
				getGraphics().setColor(Color.BLUE);
				getGraphics().drawPolygon(x,y,4);
				
			}
		});
	}
}
