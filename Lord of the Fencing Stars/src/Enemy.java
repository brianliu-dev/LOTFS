
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

public class Enemy {
	int fx,fy,fw,fh,dx,dy,ex,ey, fcx, fcy;
	int hp;
	double angle = 0;
	Rectangle rectangle;
	//Home:/home/bliu/Amador/AP Comp Sci/Workspace/Lord of the Fencing Stars/images/Square3.png
	//Class:/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Square3.png
	ImageIcon i = new ImageIcon("/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Square3.png");
	Image enemy = i.getImage();
	boolean hit = false;
	boolean death = false;
	ArrayList<Rectangle> DEATH = new ArrayList<Rectangle>();
	
	public Enemy (int x, int y, int w, int h,int health){
		fx=x; fy = y; fw = w; fh = h; hp = health;
		rectangle = new Rectangle(fx,fy,fw,fh);
		}
	
	public void solveSpeed(Ship s){
			double r = 2/(Math.sqrt((s.snakex-fx)*(s.snakex-fx) + (s.snakey-fy)*(s.snakey-fy)));
			dx = (int)(r*(s.snakex-fx));
			dy = (int)(r*(s.snakey-fy));
		}
	public void calcAngle(double mx, double my) {
		// TODO Auto-generated method stub
		angle = Math.atan((mx-(fx))/(my-(fy)));
	}
	public void move(){
			fx += dx;
			fy += dy;
		}
	public void draw(Graphics g) {
		// TODO Auto-generated method stub300
		fcx = fx + (fw/2);
		fcy = fy + (fh/2);
		 Graphics2D g2d = (Graphics2D)g;
	        AffineTransform old = g2d.getTransform();
	        Image img = enemy;
	        g2d.rotate(-angle,fcx,fcy);
	        g.drawImage(img, fx, fy, fw, fh, null);
	        rectangle = new Rectangle(fx,fy,fw,fh);
	        g2d.draw(rectangle);
	      	g2d.setTransform(old);
		
		if(death){
			
			for (int x = 0; x<50; x++){
				System.out.println("DEATH");
				rectangle = new Rectangle(fx,fy,fw,fh);
				DEATH.add(rectangle);
			}
			for (int y = 0; y<DEATH.size(); y++){
				AffineTransform old2 = g2d.getTransform();
				g2d.rotate(-y*(90*(Math.PI/180)),fx,fy);
				g2d.draw(DEATH.get(y));
				g2d.setTransform(old2);
			}
		}
//			Graphics2D g2d = (Graphics2D)g;
//			AffineTransform old = g2d.getTransform();
//			g2d.rotate(-angle + (90*(Math.PI/180)),x,y);
//			g2d.draw(rectangle);
//			g2d.drawImage(img, x, y,50,15, null);
//			hitbox = new Rectangle(x,y,50,15);	
//			g2d.draw(hitbox);
//			g2d.setTransform(old);
//			for (Rectangle c: DEATH){
//			AffineTransform old2 = g2d.getTransform();
//			g2d.rotate(x*(360/50),fx,fy);
//			rectangle = new Rectangle(fx,fy,fw,fh);
//			g2d.draw(rectangle);
//			g2d.setTransform(old2);
			
		
		//	Color origColor = g.getColor();
		//	g.setColor(Color.black);
		//	g.drawRoundRect(fx, fy, fw, fh, 15, 15);
		//	g.setColor(origColor);
	}
	public boolean collides(Bullet b){
		if (rectangle == null || b.hitbox == null)
			return false;
		else{
		return b.hitbox.intersects(rectangle);
			}
	}
	
}
