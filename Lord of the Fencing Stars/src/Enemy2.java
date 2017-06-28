import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;


public class Enemy2 extends Enemy {
	ImageIcon i = new ImageIcon("/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Sphere.png");
	Image enemy = i.getImage();
	public Enemy2(int x, int y, int w, int h, int health) {
		super(x, y, w, h, health*3);
		// TODO Auto-generated constructor stub
	}
	@Override
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
	}
}
