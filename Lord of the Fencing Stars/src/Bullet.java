import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;


public class Bullet {
	//Home:/home/bliu/Amador/AP Comp Sci/Workspace/Lord of the Fencing Stars/images/Laser.png
	//Class:/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Laser.png
	ImageIcon i = new ImageIcon("/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Laser.png");
	Image laser = i.getImage();
	int x,y,ex,ey,speed;
	double dx,dy;
	Rectangle hitbox;
	boolean hit = false;
	public double angle;
	public Bullet(Ship s, int fx, int fy, int bx, int by){
		x = s.snakecx; y = s.snakecy; ex=bx; ey=by;
		solveSpeed(ex,ey);
	}
	private void solveSpeed(int xclick, int yclick){
		double r = 15/(Math.sqrt((ex-x)*(ex-x) + (ey-y)*(ey-y)));
		dx = (r*(ex-x));
		dy = (r*(ey-y));
		angle = Math.atan((dy/dx));
		//System.out.println(angle);
	}
	public void move(){
		x += dx;
		y += dy;
	}
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform old = g2d.getTransform();
		Image img = laser;
		if (((ex-x)>0)){
		g2d.rotate(angle,x,y);
		g2d.drawImage(img, x, y,50,15, null);
		hitbox = new Rectangle(x,y,50,15);	
		g2d.draw(hitbox);
		g2d.setTransform(old);
		}
		else{
			g2d.rotate(angle+Math.PI,x,y);
			g2d.drawImage(img, x, y,50,15, null);
		hitbox = new Rectangle(x,y,50,15);	
		g2d.draw(hitbox);
			g2d.setTransform(old);
		}

		
	}
	

	public boolean collides(Enemy e){
		if (e.rectangle == null || hitbox == null)
			return false;
		else{
		return hitbox.intersects(e.rectangle);
	}
	}
	

}
