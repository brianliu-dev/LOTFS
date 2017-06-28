
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;


public class Ship {
	//Home:/home/bliu/Amador/AP Comp Sci/Workspace/Lord of the Fencing Stars/images/Hexagon3.png
	//Class:/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Hexagon3.png
	ImageIcon i = new ImageIcon("/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Square2.png");
	Image snake = i.getImage();
	int up = 0;
	int down = 0;
	int right = 0;
	int left = 0;
	int mousex;
	public int snakex,snakey,snakew,snakeh,hp, snakecx,snakecy;
	double angle = 0;
	boolean hit=false;
	Rectangle Snakerect;
	
	public Ship(int x, int y, int w, int h){
		snakex = x;
		snakey = y;
		snakew = w;
		snakeh = h;
		snakecx= snakex+(snakew/2);
		snakecy= snakey+(snakeh/2);
		Snakerect = new Rectangle (snakex,snakey,snakew,snakeh);
		hp = 100;
	}
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		snakecx= snakex+(snakew/2);
		snakecy= snakey+(snakeh/2);
		Image img = snake;
		 Graphics2D g2d = (Graphics2D)g;
	        AffineTransform old = g2d.getTransform();
//	        g2d.rotate(-angle,snakex+(snakew/2),snakey+(snakeh/2));
//	        g.drawImage(img, snakex, snakey, snakew, snakeh, null);
//	        Snakerect = new Rectangle(snakex,snakey,snakew,snakeh);
//	        g2d.draw(Snakerect);
//	        g2d.setTransform(old);
	        
	        if (((mousex-snakecx)>0)){
	    		g2d.rotate(-angle,snakecx,snakecy);
	    		g2d.drawImage(img, snakex, snakey, snakew, snakeh, null);
	    		Snakerect = new Rectangle(snakex,snakey,snakew,snakeh);
		        g2d.draw(Snakerect);
	    		g2d.setTransform(old);
	    		}
	    		else{
	    			g2d.rotate(-angle - Math.PI,snakecx,snakecy);
	    			g2d.drawImage(img, snakex, snakey, snakew, snakeh, null);
		    		Snakerect = new Rectangle(snakex,snakey,snakew,snakeh);
			        g2d.draw(Snakerect);
	    			g2d.setTransform(old);
	    		}
			
	}
	public boolean collides(Enemy e){
		return e.rectangle.intersects(Snakerect);
	}
	public void calcAngle(double mx, double my) {
		// TODO Auto-generated method stub
		mousex = (int) mx;
		angle = Math.atan((mx-(snakecx))/(my-(snakecy)));
	}
}
