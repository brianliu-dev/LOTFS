
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Spacewarp extends JPanel implements MouseListener, KeyListener, MouseMotionListener {
	int dimension1 = 1550;
	int dimension2 = 800;
	Dimension d = new Dimension(dimension1,dimension2);
	int delay = 20;
	Timer ticker = new Timer(delay, new ObjectMover());
	Ship first;
	Enemy e;
	Bullet l; 
	int cooldown = 0;
	double up, down, left, right;
	boolean bup = false, bdown = false, bleft = false, bright = false;
	int maxspeed = 3;
	double wallbounce = 1;
	double acceleration = 0.1;
	int rainbowshit = 0;
	int score = 0;
	int chomp = 0;
	int mlx, mly,ouch;
	double angle;
	boolean mouse = false;
	boolean endgame;
	Rectangle explosions;
	ArrayList <Ship> ships = new ArrayList <Ship>();
	ArrayList <Enemy> enemies = new ArrayList <Enemy>();
	ArrayList <Enemy> dead = new ArrayList <Enemy>();
	ArrayList <Bullet> bullets = new ArrayList <Bullet>();
	ArrayList <Color> Rainbow = new ArrayList<Color>();
	ArrayList<Rectangle> Death = new ArrayList<Rectangle>();
	class ObjectMover implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Every time the timer ticks, based on the value of delay, it moves
			// everything in movers List.  How they move depends upon context...
		//	for(Moveable m : movers){
		//		m.move();
		//	}
			checkCollisions();
			for (Ship s: ships){
				if (bup && up <=maxspeed)
					up+=acceleration;
					s.snakey-=up;
					if (!bup || up>maxspeed)
					up-=0.5;
				if (bdown && down <=maxspeed)
						down+=acceleration;
					s.snakey+=down;
					if (!bdown || down>maxspeed)
					down-=0.5;
				if (bleft && left <=maxspeed)
						left+=acceleration;
					s.snakex-=left;
					if (!bleft || left>maxspeed)
					left-=0.5;
				if (bright && right <=maxspeed)
						right+=acceleration;
					s.snakex+=right;
					if (!bright || right>maxspeed)
					right-=0.5;
					////////////BOUNDARIES///////////////////
				if (s.snakey>dimension2-s.snakeh){
						s.snakey = dimension2 - s.snakeh;
						up = down/wallbounce;
					}
				if (s.snakey<0){
						s.snakey = 0;
						down = up/wallbounce;
					}
				if (s.snakex>dimension1-s.snakew){
					s.snakex=dimension1-s.snakew;
						left = right/wallbounce;
				}
				if (s.snakex<0){
					s.snakex = 0;
						right = left/wallbounce;
				}
			}
			for(Bullet b: bullets){
				b.move();
			}
			for (Enemy e: enemies){
				e.calcAngle(first.snakecx, first.snakecy);
				e.solveSpeed(first);
				e.move();
			}
			
			checkCollisions();
			ouch--;
			cooldown--;
			if (mouse == true){
				shoot();
			}
			repaint();
			
		}
	}
	public Spacewarp(){
		this.setPreferredSize(d);
		addMouseListener(this);
		addKeyListener(this);
		addMouseMotionListener(this);
		this.setFocusable(true);
		setVisible(true);
		startgame();
		
	}
	public void startgame(){
		first = new Ship(dimension1/2, dimension2/2,75,75);
		ships.add(first);
		for (int x=0; x<30; x++){
			Random r = new Random();
			if (r.nextInt(2)>0){
				e = new Enemy(r.nextInt(dimension1), r.nextInt(2)*dimension2, 70,70, 5);
				enemies.add(e);
			}
			else{
				e = new Enemy(r.nextInt(2)*dimension1, r.nextInt(dimension2), 70,70,5);
				enemies.add(e);
			}
		}
		ticker.start();
	}
	
	public void shoot(){
		if (cooldown <= 0){
			for (int x = 0; x<1; x++){
		l = new Bullet(first,first.snakex+(first.snakew/2),first.snakey+(first.snakeh/2),mlx+(x*5),mly+(x*5));
		bullets.add(l);
			}
			if (rainbowshit<Rainbow.size()-1){
			rainbowshit++;
			}
			else{
				rainbowshit = 0;
			}
			cooldown = 1;
		}
		
	}
	public void checkCollisions(){
		int a = 0;
		int b = 0;
	 for (Ship s: ships){
		 for (Enemy e: enemies){
		if (s.collides(e)){
			a++;
			e.hit=true;
			s.hit=true;
		}
	 }
	 }
	 for (Bullet l: bullets){
		 for (Enemy e: enemies){
		if (l.collides(e)||e.collides(l)){
			b++;
			e.hit = true;
			l.hit = true;
		}
	 }
	 }
	 for (int x = 0; x< bullets.size(); x++){
		 if (bullets.get(x).hit || bullets.get(x).x>dimension1 
		|| bullets.get(x).y>dimension2 || bullets.get(x).x<0 
		|| bullets.get(x).y<0)
			 bullets.remove(x);
	 }
	 if (b>0){
		 hit();
	 }
	 if (a>0){
		 shiphit();
	 }
		
	}
	
	public void hit(){
		//e = null;
		for (int x = 0; x<enemies.size(); x++){
			if (enemies.get(x).hp>0 && enemies.get(x).hit){
				enemies.get(x).hp--;
				enemies.get(x).hit = false;
			}
			else if (enemies.get(x).hp == 0 && enemies.get(x).hit){
				score+=10;
				enemies.get(x).death = true;
//				for (int y = 0; y<50; y++){
//					System.out.println("DEATH");
//					explosions = new Rectangle(enemies.get(x).fcx,enemies.get(x).fcy,10,10);
//					Death.add(explosions);
//				}
//				dead.add(enemies.get(x));
				enemies.remove(x);
				Random r = new Random();
				double rand = Math.random();
				
				if (r.nextInt(2)>0){
					if (Math.random()<.7){
						System.out.println(rand);
					e = new Enemy2(r.nextInt(dimension1), r.nextInt(2)*dimension2, 70,70,15);
					enemies.add(e);
					}
					else{
						System.out.println("h");
						e = new Enemy(r.nextInt(2)*dimension1, r.nextInt(dimension2), 70,70,5);
						enemies.add(e);
					}
				}
				else{
					e = new Enemy(r.nextInt(2)*dimension1, r.nextInt(dimension2), 70,70,5);
					enemies.add(e);
				}
			}
		}
		repaint();
	}
	public void shiphit(){
		first.hp--;
		for (int x = 0; x<enemies.size(); x++){
			if (enemies.get(x).hit && first.hit){
				enemies.get(x).death = true;
				repaint();
				enemies.remove(x);
				first.hit = false;
				Random r = new Random();
				if (r.nextInt(2)>0){
					e = new Enemy(r.nextInt(dimension1), r.nextInt(2)*dimension2, 70,70,5);
					enemies.add(e);
				}
				else{
					e = new Enemy(r.nextInt(2)*dimension1, r.nextInt(dimension2), 70,70,5);
					enemies.add(e);
				}
			}
		}
		if (first.hp==0){
			endgame();
		}
		
		repaint();
		ouch = 3;
	}
	
	public void endgame(){
		endgame = true;
		
		ticker.stop();
		repaint();
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		//BACKGROUND///////////////////////////////////////
		//Home:/home/bliu/Amador/AP Comp Sci/Workspace/Lord of the Fencing Stars/images/Abstract.jpg
		//Class:/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Abstract.jpg
		ImageIcon background = new ImageIcon("/media/student/MULTIBOOT/Brian/Computer Science/Lord of the Fencing Stars/images/Space6.jpg");
		Image back = background.getImage();
		g.drawImage(back, 0, 0,dimension1,dimension2, null);
		///////////////Colors///////////////////////////////////
		if (Rainbow.size() == 0){
		Rainbow.add(Color.white);
		Rainbow.add(Color.black);
		Rainbow.add(Color.blue);
		Rainbow.add(Color.red);
		Rainbow.add(Color.orange);
		Rainbow.add(Color.yellow);
		Rainbow.add(Color.green);
		Rainbow.add(Color.cyan);
		Rainbow.add(Color.magenta);
		}
		/////////////HealthBar//////////////////////////////////
		g.setFont(this.getFont().deriveFont(Font.LAYOUT_RIGHT_TO_LEFT, 25));
		g.setColor(Color.magenta);
		g.drawString("HP: ", 10, 25);
		Color origColor = g.getColor();
		g.setColor(Color.magenta);
		g.fill3DRect(10, 30, first.hp*8, 10, true);
		g.setColor(origColor);
		/////////////HIT!!!!!!/////////////////////////////
//		if(ouch>0){
//			Color origColor2 = g.getColor();
//			g.setColor(Color.RED);
//			g.fill3DRect(0, 0, dimension1, dimension2, true);
//			g.setColor(origColor2);
//		}
		/////////////Ship/////////////////////////////////
        first.draw(g);
		/////////////Enemies////////////////////////
		for (Enemy e: enemies){
			e.draw(g);
		}
		///////////////////Explosions//////////////////////
		for (int y = 0; y<Death.size(); y++){
			Graphics2D g2d = (Graphics2D)g;
					AffineTransform old3 = g2d.getTransform();
					g2d.rotate(-y*(90*(Math.PI/180)),Death.get(y).x,Death.get(y).y);
					g2d.draw(Death.get(y));
					g2d.setTransform(old3);
				}
//		for (Enemy d: dead){
//			d.draw(g);
//		}
		/////////////Lasers///////////////////////////////
		g.setColor(Rainbow.get(rainbowshit));
		for (Bullet b: bullets){
			b.draw(g);
		}
		g.setColor(origColor);
		/////////////Score////////////////////////////
		g.setFont(this.getFont().deriveFont(Font.BOLD, 20));
		g.setColor(Color.white);
		g.drawString("Score: " + score, dimension1/2, 100);
		if (endgame){
			g.setFont(this.getFont().deriveFont(Font.BOLD, 300));
			g.setColor(Color.white);
			g.drawString("LOSER", 100, dimension2/2);
		}
		
		/*if (chomp>0){
			g.setFont(this.getFont().deriveFont(Font.BOLD, 300));
			g.setColor(Color.red);
			g.drawString("CHOMP!", 100, 500);
		}
		*/
		

	}
	
	/*public void drawBow(Graphics g){
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		bow = 50 + Hero.herodx;
		
		
		
		if(b.key==1){
			at.setToTranslation(bow,500);
			anglebow = Math.atan2(500 - mousey, bow  - mousex);
			
	//		mouse = Math.toDegrees(mouseup);
		
			at.rotate(anglebow+Math.PI,75/2,75/2);
		
			g2d.drawImage(b.getImage(),at, null);
		
			
			//move bow with hero
			if(Hero.herokey == 1){
				Hero.herodx+=5;
				Hero.herokey = 0;
			}
			if(Hero.herokey == 2){
				Hero.herodx-=5;
				Hero.herokey = 0;
			}
		}
	
	}
*/
	
	@Override
	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		
		if (k.getKeyCode() == k.VK_UP ||k.getKeyCode() == k.VK_W){
			bup = true;
			
		}
		if (k.getKeyCode() == k.VK_DOWN || k.getKeyCode() == k.VK_S){
			bdown = true;
		}
		if (k.getKeyCode() == k.VK_LEFT || k.getKeyCode() == k.VK_A){
			bleft=  true;
		}
		if (k.getKeyCode() == k.VK_RIGHT || k.getKeyCode() == k.VK_D){
			bright = true;
		}
		if (k.getKeyCode() == k.VK_SPACE){
			shoot();
		}	
	}

	@Override
	public void keyReleased(KeyEvent k) {
		if (k.getKeyCode() == k.VK_UP ||k.getKeyCode() == k.VK_W){
			bup = false;
		}
		if (k.getKeyCode() == k.VK_DOWN || k.getKeyCode() == k.VK_S){
			bdown = false;
		}
		if (k.getKeyCode() == k.VK_LEFT || k.getKeyCode() == k.VK_A){
			bleft = false;
		}
		if (k.getKeyCode() == k.VK_RIGHT || k.getKeyCode() == k.VK_D){
			bright = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	/*	if (cooldown<=0){
		l = new Bullet(first,first.snakex+30,first.snakey+30,e.getX(),e.getY());
		bullets.add(l);
		cooldown = 5;
		}*/
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouse = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouse = false;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		mlx = e.getX();
		mly = e.getY();
		first.calcAngle(mlx, mly);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mlx = e.getX();
		mly = e.getY();
		first.calcAngle(mlx, mly);
	}

	
}
