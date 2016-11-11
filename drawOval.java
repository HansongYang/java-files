import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 

public class drawOval extends JFrame{
	int x,y;
	public drawOval(){
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
					getGraphics().drawOval(x, y, endX-50, endY-50);
					x = endX;
					y = endY;
					break;
				}
			}
		});
	}
}
