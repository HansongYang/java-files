import java.awt.event.*;
import javax.swing.*;
import java.awt.*; 

public class draw extends JPanel{
	int x1, x2, y1, y2;
	int[] x = new int[4];
	int[] y = new int[4];
	int type = 0;
	int color = 0;
	int thickness = 1;
	boolean freeDraw = false;
	boolean fill = false;
	boolean erase = false;
	
	public draw(){
		setBackground(Color.WHITE);
		setup();
		freeDraw(1);
	}
	
	public void freeDraw(int arg){
		freeDraw = true;
		if(arg == 0){
			erase = true;
		}else{
			erase = false;
		}
	}
		
	public void fill(){
		if(fill){
			fill = false;
		}else{
			fill = true;
		}
	}
	
	public void setup(){
	    addMouseListener(new MouseAdapter(){
	        public void mousePressed(MouseEvent m){
	        	x1 = m.getX();
	            y1 = m.getY();
				x[0] = m.getX();
				x[1] = m.getY();
				x[2] = 140;
				x[3] = m.getX();
	        }
	        
	        public void mouseReleased(MouseEvent m){
	        	x2 = m.getX();
	            y2 = m.getY();
	          }
	        });

	    addMouseMotionListener(new MouseMotionAdapter(){
	         public void mouseDragged(MouseEvent m){
	        	 if(freeDraw){
	        		int endX, endY;
	 				endX = m.getX();
	 				endY = m.getY();
	 				Graphics g = getGraphics();
	 				Graphics2D g2 = (Graphics2D) g;
	 				if(!erase){
		 				switch(color){
		 				   	case 1:
		 				   		g.setColor(Color.BLUE); break;
		 				   	case 2:
		 				   		g.setColor(Color.pink); break;
		 				   	case 3:
		 				   		g.setColor(Color.red); break;
		 				   	case 4:
		 				   		g.setColor(Color.green); break;
		 				   	case 5:
		 				   		g.setColor(Color.black); break;
		 				   	case 6:
		 				   		g.setColor(Color.yellow); break;
		 				   	case 7:
		 				   		g.setColor(Color.orange); break;
		 				   	case 8:
		 				   		g.setColor(Color.magenta); break;
		 				   	case 9:
		 			   			g.setColor(Color.white); break;
		 					default:
		 						g.setColor(Color.BLACK); break;
		 				}
	 				}else{
	 					g.setColor(Color.WHITE);
	 				}
	 				g2.setStroke(new BasicStroke(thickness));
	 				g2.drawLine(x1, y1, endX, endY);
	 				x1 = endX;
	 				y1 = endY;
	        	 }else{
	        		 x2 = m.getX();
	        		 y2 = m.getY();
	 				 y[0] = m.getY();
	 				 y[1] = 200;
	 				 y[2] = m.getY();
	 				 y[3] = m.getX();
	        		 repaint();
	        	 }
	          }
	     });
	}
	
	public void set(int arg){
		freeDraw = false;
		if(arg == 1){
			type = 1;
		}else if(arg == 2){
			type = 2;
		}else if(arg == 3){
			type = 3;
		}else if(arg == 4){
			type = 4;
		}else if(arg == 5){
			type = 5;
		}
	}
	
	public void setColor(int arg){
		switch(arg){
			case 1: color = 1; break;
			case 2: color = 2; break;
			case 3: color = 3; break;
			case 4: color = 4; break;
			case 5: color = 5; break;
			case 6: color = 6; break;
			case 7: color = 7; break;
			case 8: color = 8; break;
			case 9: color = 9; break;
		}
	}
	
	public void setThickness(int arg){
		switch(arg){
			case 1: thickness = 1; break;
			case 2: thickness = 5; break;
			case 3: thickness = 10; break;
			case 4: thickness = 15; break;
			case 5: thickness = 20; break;
			case 6: thickness = 25; break;
			case 7: thickness = 30; break;
		}
	}
	
	public void paintComponent(Graphics g){ 
	   super.paintComponent(g);
	   Graphics2D g2 = (Graphics2D) g;
	   switch(color){
	   	case 1:
	   			g.setColor(Color.blue); break;
	   	case 2:
	   			g.setColor(Color.pink); break;
	   	case 3:
			    g.setColor(Color.red); break;
	   	case 4:
			    g.setColor(Color.green); break;
	   	case 5:
			    g.setColor(Color.black); break;
	   	case 6:
			    g.setColor(Color.yellow); break;
	   	case 7:
			    g.setColor(Color.orange); break;
	   	case 8:
			    g.setColor(Color.magenta); break;
	   	case 9:
	   			g.setColor(Color.white); break;
		default:
			    g.setColor(Color.BLACK); break;
	   }
	   
	   if(type == 1){
		   int minX = Math.min(x1, x2);
	       int minY = Math.min(y1, y2);
	       int maxX = Math.max(x1, x2);
	       int maxY = Math.max(y1, y2);
	       g2.setStroke(new BasicStroke(thickness));
	       g2.drawOval(minX,minY, maxX-minX, maxY-minY);
	       if(fill){
	    	   g2.fillOval(minX,minY, maxX-minX, maxY-minY);
	       }
	  }
	   else if (type == 2){
		   g2.setStroke(new BasicStroke(thickness));
		   g2.drawLine(x1,y1,x2,y2);
	   }
	   else if(type == 3){		  
          int minX = Math.min(x1, x2);
          int minY = Math.min(y1, y2);
          int maxX = Math.max(x1, x2);
          int maxY = Math.max(y1, y2);
          g2.setStroke(new BasicStroke(thickness));
		  g2.drawRect(minX,minY, maxX-minX, maxY-minY);	
	       if(fill){
	    	   g2.fillRect(minX,minY, maxX-minX, maxY-minY);
	       }
	   }else if(type == 4){
		   g2.setStroke(new BasicStroke(thickness));
		   g2.drawPolygon(x,y,4);
		   if(fill){
		    	g2.fillPolygon(x,y,4);
		   }
	   }
	}
}



