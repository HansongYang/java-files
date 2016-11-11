import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 

public class drawrect extends JFrame {
	int x,y;
	public drawrect(){
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
					getGraphics().drawRect(x, y, endX-100, endY-100);
					x = endX;
					y = endY;
					break;
				}
			}
		});
	}
}