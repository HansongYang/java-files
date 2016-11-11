	import java.awt.*; 
	import java.awt.event.*;
	import javax.swing.*;
	
	public class MasterPiece extends JPanel{
	  public static final int WINDOW_WIDTH = 1600 ;
	  public static final int WINDOW_HEIGHT = 700;
	  
	  public MasterPiece() {
	    setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
	    setBackground(Color.WHITE);
	  }
	  
	  public void paint(Graphics pen){
	    pen.setColor(Color.YELLOW);
	    pen.fillOval(1100, 70, 150, 150);
	    pen.setColor(Color.BLACK);
	    pen.fillOval(1140,120,10,20);
	    pen.fillOval(1200,120,10,20);
	    pen.drawArc(1125,140,100,60,180,180);
	    pen.setColor(Color.RED);
	    pen.fillArc(1190,180,30,30,40,150);
	    
	    pen.setColor(Color.BLUE);
	    pen.drawString("Twinkle, twinkle, little star",1120,300);
	    pen.drawString("How I wonder what you are",1120,320);
	    pen.drawString("Up above the world so high", 1120, 340);
	    pen.drawString("Like a diamond in the sky",1120,360);
	    pen.drawString("Twinkle, twinkle, little star",1120,380);
	    pen.drawString("How I wonder what you are",1120,400);
	    pen.drawString("When the blazing sun is gone",1120,420);
	    pen.drawString("When he nothing shines upon",1120,440);
	    pen.drawString("Then you show your little light",1120,460);
	    pen.drawString("Twinkle, twinkle, little star",1120,480);
	    pen.drawString("How I wonder what you are",1120,500);
	    
	    for (int i = 0; i < 200; i++) { 
	        int c1, c2, c3, w, h, r; 
	        c1 = (int) (Math.random() * 255); 
	        c2 = (int) (Math.random() * 255); 
	        c3 = (int) (Math.random() * 255); 
	        Color c = new Color(c1, c2, c3); 
	        pen.setColor(c);
	        w = (int) (Math.random() * 1000); 
	        h = (int) (Math.random() * 800); 
	        r = (int) (Math.random() * 80); 
	        MasterPiece(pen, w, h, r, "fill"); 
	    }
	  }
	  
	    public void MasterPiece(Graphics g, int x0, int y0, int r, String f) { 
	        double ch = 72 * Math.PI / 180;
	        int x1 = x0, x2 = (int) (x0 - Math.sin(ch) * r), x3 = (int) (x0 + Math 
	                                                                       .sin(ch) * r), x4 = (int) (x0 - Math.sin(ch / 2) * r), x5 = (int) (x0 + Math 
	                                                                                                                                            .sin(ch / 2) * r); 
	        int y1 = y0 - r, y2 = (int) (y0 - Math.cos(ch) * r), y3 = y2, y4 = (int) (y0 + Math 
	                                                                                    .cos(ch / 2) * r), y5 = y4; 
	        int bx = (int) (x0 + Math.cos(ch) * Math.tan(ch / 2) * r); 
	        int by = y2; 
	        Polygon a = new Polygon(); 
	        Polygon b = new Polygon(); 
	        a.addPoint(x2, y2); 
	        a.addPoint(x5, y5);
	        a.addPoint(bx, by);
	        b.addPoint(x1, y1); 
	        b.addPoint(bx, by); 
	        b.addPoint(x3, y3); 
	        b.addPoint(x4, y4); 
	        
	        if (f.equals("draw")) { 
	          g.drawPolygon(a); 
	          g.drawPolygon(b); 
	        } 
	        if (f.equals("fill")) { 
	          g.fillPolygon(a); 
	          g.fillPolygon(b); 
	        } 
	    }
	    
	     public static void main(String args[]) {
	     JFrame frame = new JFrame("A Masterpiece");
	        frame.add(new MasterPiece());
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack(); 
	        frame.setVisible(true);
	   }
	}
