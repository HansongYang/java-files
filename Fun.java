import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 

public class Fun extends JFrame{
	draw panel = new draw();
	JPanel aPanel = new JPanel();
	JFrame window = new JFrame();
	JButton free = new JButton("Free Draw");
	JButton circles = new JButton("O");
	JButton line = new JButton("------");
	JButton rectangles = new JButton("[]");
	JButton polygon = new JButton("Polygon");
	JButton blue = new JButton();
	JButton pink = new JButton();
	JButton red = new JButton();
	JButton green = new JButton();
	JButton black = new JButton();
	JButton yellow = new JButton();
	JButton orange = new JButton();
	JButton magenta = new JButton();
	JButton white = new JButton();
	JButton fill = new JButton("Fill in Color");
	JButton erase = new JButton("Eraser");
	JButton newPage = new JButton("New Page");
	JMenuBar menubar = new JMenuBar();
	JMenu thick = new JMenu("Thickness");
	JMenuItem one = new JMenuItem("1");
	JMenuItem five = new JMenuItem("5");
	JMenuItem ten = new JMenuItem("10");
	JMenuItem fifteen = new JMenuItem("15");
	JMenuItem twenty = new JMenuItem("20");
	JMenuItem twentyFive = new JMenuItem("25");
	JMenuItem thirty = new JMenuItem("30");
	
	public Fun(String title){
		super(title);
		setUp();
	}
	
	public void setUp(){
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		aPanel.setBounds(0, 0, 300, 100);
		panel.setBounds(0, 50, 1600, 700);
		window.add(panel);
		window.add(aPanel);
		aPanel.setBackground(Color.GRAY);
		
		Dimension d = new Dimension(100,40);
		free.setPreferredSize(d);
		newPage.setPreferredSize(d);
		circles.setPreferredSize(d);
		line.setPreferredSize(d);
		rectangles.setPreferredSize(d);
		polygon.setPreferredSize(d);
		fill.setPreferredSize(d);
		
		d = new Dimension(20,20);
		blue.setPreferredSize(d);
		pink.setPreferredSize(d);
		red.setPreferredSize(d);
		green.setPreferredSize(d);
		black.setPreferredSize(d);
		yellow.setPreferredSize(d);
		orange.setPreferredSize(d);
		magenta.setPreferredSize(d);
		white.setPreferredSize(d);
		
		free.setBackground(Color.WHITE);
		newPage.setBackground(Color.WHITE);
		circles.setBackground(Color.RED);
		circles.setForeground(Color.blue);
		line.setBackground(Color.GREEN);
		line.setForeground(new Color(70, 0, 70));
		rectangles.setBackground(Color.YELLOW);
		rectangles.setForeground(new Color(22,145,217));
		polygon.setBackground(Color.BLACK);
		polygon.setForeground(Color.WHITE);
		fill.setBackground(Color.lightGray);
		fill.setForeground(Color.darkGray);
		blue.setBackground(Color.BLUE);
		pink.setBackground(Color.PINK);
		red.setBackground(Color.RED);
		green.setBackground(Color.GREEN);
		black.setBackground(Color.BLACK);
		yellow.setBackground(Color.YELLOW);
		orange.setBackground(Color.ORANGE);
		magenta.setBackground(Color.MAGENTA);
		white.setBackground(Color.WHITE);

		menubar.add(thick);
		thick.add(one);
		thick.add(five);
		thick.add(ten);
		thick.add(fifteen);
		thick.add(twenty);
		thick.add(twentyFive);
		thick.add(thirty);


		aPanel.add(newPage);
		aPanel.add(free);
		aPanel.add(menubar);
		aPanel.add(circles);
		aPanel.add(line);
		aPanel.add(rectangles);
		aPanel.add(polygon);
		aPanel.add(fill);
		aPanel.add(erase);
		aPanel.add(blue);
		aPanel.add(pink);
		aPanel.add(red);
		aPanel.add(green);
		aPanel.add(black);
		aPanel.add(yellow);
		aPanel.add(orange);
		aPanel.add(magenta);
		aPanel.add(white);
		window.setVisible(true);
		window.setSize(1450,730);
	}
	
	public void click(){	
		free.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.freeDraw(1);
			}
		});
		
		newPage.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.removeAll();
				panel.revalidate();
				panel.repaint();
			}
		});
		
		circles.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.set(1);
			}
		});
		
		line.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.set(2);
				
			}
		});
		
		rectangles.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.set(3);
			}
		});
		
		polygon.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.set(4);
			}
		});
		
		fill.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.fill();
			}
		});
		
		erase.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.freeDraw(0);
			}
		});
		
		blue.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(1);
			}
		});
		
		pink.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(2);
			}
		});
		
		red.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(3);
			}
		});
		
		green.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(4);
			}
		});
		
		black.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(5);
			}
		});
		
		yellow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(6);
			}
		});
		
		orange.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(7);
			}
		});
		
		magenta.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(8);
			}
		});
		
		white.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setColor(9);
			}
		});
		
		one.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setThickness(1);
			}
		});
		
		five.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setThickness(2);
			}
		});
		
		ten.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setThickness(3);
			}
		});

		fifteen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setThickness(4);
			}
		});
		
		twenty.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setThickness(5);
			}
		});
		
		twentyFive.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setThickness(6);
			}
		});
		
		thirty.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent a){
				panel.setThickness(7);
			}
		});
	}	
	
	public static void main(String[] args){
		Fun f = new Fun("Just For Fun");
		f.click();
	}
}
