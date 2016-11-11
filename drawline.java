import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 

public class drawline extends JFrame {
	int x,y;
	public drawline(){
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				x = e.getX();
				y = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter(){
		public void mouseDragged(MouseEvent e){
			while(true){
				int endX, endY;
				endX = e.getX();
				endY = e.getY();
				getGraphics().drawLine(x, y, endX, endY);
				x = endX;
				y = endY;
				break;
				}
			}
		});
	}
}