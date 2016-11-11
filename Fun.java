import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 


public class Fun extends JFrame{
	JButton circles = new JButton("Circles");
	JButton line = new JButton("Line");
	JButton rectangles = new JButton("Rectangles");
	JButton polygon = new JButton("Polygon");
	
	public Fun(String title){
	super(title);
	 getContentPane().setLayout(null);

	
	circles.setSize(100,100);
	line.setSize(100,100);
	rectangles.setSize(100,100);
	polygon.setSize(100,100);
	
	line.setLocation(100,0);
	rectangles.setLocation(0,100);
	polygon.setLocation(100,100);
	
	circles.setBackground(Color.RED);
	circles.setForeground(Color.blue);
	line.setBackground(Color.GREEN);
	line.setForeground(new Color(70, 0, 70));
	rectangles.setBackground(Color.YELLOW);
	rectangles.setForeground(Color.cyan);
	polygon.setBackground(Color.BLACK);
	polygon.setForeground(Color.WHITE);
	
	circles.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent a){
			drawOval oval = new drawOval();
			oval.setSize(1600,730);
			oval.setVisible(true);
		}
	});
	
	line.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent a){
			drawline line = new drawline();
			line.setSize(1600,730);
			line.setVisible(true);
			
		}
	});
	
	rectangles.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent a){
			drawrect rect = new drawrect();
			rect.setSize(1600,730);
			rect.setVisible(true);
		}
	});
	
	polygon.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent a){
			drawpolygon polygon = new drawpolygon();
			polygon.setSize(1600,730);
			polygon.setVisible(true);
		}
	});
	
	getContentPane().add(circles);
	getContentPane().add(line);
	getContentPane().add(rectangles);
	getContentPane().add(polygon);
	
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(300,300);
	}

	
	public static void main(String[] args){
		new Fun("Just For Fun").setVisible(true);
	}
}
