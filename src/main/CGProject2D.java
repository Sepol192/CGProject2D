package main;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class CGProject2D extends JFrame implements ActionListener{

	public static void main(String[] args) {

		JFrame frame = new CGProject2D();
		frame.setTitle("CGProject2D");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		MyPanel panel = new MyPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	
	PrinterJob pj;
	MyPanel painter = new MyPanel();
	
	public CGProject2D() {
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb); //para criar

		JMenu menu = new JMenu("File"); //menu
		
		JMenuItem mI = new JMenuItem("New Game");
		mI.addActionListener(this);
		menu.add(mI);
		
		mI = new JMenuItem("Print");
		mI.addActionListener(this);
		menu.add(mI);
		
		mI = new JMenuItem("Exit");
		mI.addActionListener(this);
		menu.add(mI);
		
		mb.add(menu);
		
		menu = new JMenu("Process"); //menu
		
		mI = new JMenuItem("Smooth");
		mI.addActionListener(this); 
		menu.add(mI);
		
		
		mI = new JMenuItem("Edge");
		mI.addActionListener(this); 
		menu.add(mI);
		
		
		mI = new JMenuItem("Gray Scale");
		mI.addActionListener(this);
		menu.add(mI);
		
		
		mI = new JMenuItem("Gray Scale v2");
		mI.addActionListener(this);
		menu.add(mI);
		

		mI = new JMenuItem("Binarization");
		mI.addActionListener(this); 		
		menu.add(mI);

		
		mb.add(menu);
		
		pj = PrinterJob.getPrinterJob();
		pj.setPrintable(painter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if( "Exit".equals(cmd) ) {
			System.exit(0); 
			
		}else if("New Game".equals(cmd) ) {
			
			
		}else if("Print".equals(cmd))
			if(pj.printDialog()) {
				try {
					pj.print();
				}catch(PrinterException ex) {
					ex.printStackTrace();
			}
		}
		
	}
	
	
}

class MyPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener, Printable {
	
	Shape player = null;
	Shape obj1 = null;
	Shape obj2 = null;
	Shape obj3 = null;
	Shape obj4 = null;
	Shape obj5 = null;
	Shape obj6 = null;
	Shape obj7 = null;
	Shape Elli1 = null;
	Shape Elli2 = null;
	FoodShape apple = null;
	FoodShape apple1 = null;
	FoodShape apple2 = null;
	FoodShape apple3 = null;
	FoodShape apple4 = null;
	FoodShape apple5 = null;
	FoodShape apple6 = null;
	
	
	int ang = 0;
	int r = 20;
	int tx = 50;
	int ty = 550;
	int firstX = 0;
	int firstY = 0;
	int deltaX = 0;
	int deltaY = 0;
	int x, x1, x2, x3, x4, x5, x6 = 0;
	boolean colision = false;
	boolean selected = false;
	
	Font font = new Font("Vencedor", Font.PLAIN, 150);
	
	AffineTransform at = new AffineTransform();
	
	public MyPanel() {
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.WHITE);
		
		addMouseListener(this);
		this.setFocusable(true);
		
		addMouseMotionListener(this);
		addKeyListener(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void paintComponent(Graphics g) {
		  super.paintComponent(g);
		  Graphics2D g2 = (Graphics2D) g;
		  
		  //Draw the game state
		  g2.setColor(Color.white);
		  g2.fillRect(0, 0, 800, 600);
		  
		  
		  player = new Ellipse2D.Double(-r,- r, 2 * r, 2 * r);
		  obj1 = new Rectangle2D.Double(-20, -300, 40, 600);
		  obj2 = new Rectangle2D.Double(-20, -300, 40, 600);
		  obj3 = new Rectangle2D.Double(-400, -20, 800, 40);
		  obj4 = new Rectangle2D.Double(-400, -20, 800, 40);
		  obj5 = new Rectangle2D.Double(-50, -150, 100, 300);
		  obj6 = new Rectangle2D.Double(-50, -150, 100, 300);
		  obj7 = new Rectangle2D.Double(-50, -50, 100, 100);
		  Elli1 = new Rectangle2D.Double(-280, -20, 560, 40);
		  Elli2 = new Rectangle2D.Double(-20, -280, 40, 560);
		  
		  
		  at.setToTranslation(tx,ty);
		  player = at.createTransformedShape(player);
		  g2.setColor(Color.blue);
		  g2.fill(player);
		  
		  at.setToTranslation(0, 300);
		  obj1 = at.createTransformedShape(obj1);
		  g2.setColor(Color.blue);
		  g2.fill(obj1);
		  
		  at.setToTranslation(800, 300);
		  obj2 = at.createTransformedShape(obj2);
		  g2.fill(obj2);
		  
		  at.setToTranslation(400, 0);
		  obj3 = at.createTransformedShape(obj3);
		  g2.fill(obj3);
		  
		  at.setToTranslation(400, 600);
		  obj4 = at.createTransformedShape(obj4);
		  g2.fill(obj4);
		  
		  at.setToTranslation(266, 450);
		  obj5 = at.createTransformedShape(obj5);
		  g2.fill(obj5);
		  
		  at.setToTranslation(532, 150);
		  obj6 = at.createTransformedShape(obj6);
		  g2.fill(obj6);
		  
		  at.setToTranslation(532, 440);
		  at.rotate(Math.toRadians(45));
		  obj7 = at.createTransformedShape(obj7);
		  g2.fill(obj7);
		  
		  at.setToTranslation(266, 0);
		  at.rotate(Math.toRadians(ang));
		  Elli1 = at.createTransformedShape(Elli1);
		  Elli2 = at.createTransformedShape(Elli2);
		  g2.fill(Elli1);
		  g2.fill(Elli2);
		  
		  g2.setColor(Color.blue);
		  at.setToTranslation(100, 100);
		  g2.drawString("Hello2D", 0, 0);
		  
		  
		  BufferedImage image = null;
			
		  URL url = getClass().getClassLoader().getResource("images/Wood.jpg");
		  
		  try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  Rectangle2D anchor = new Rectangle2D.Double(0, 0, 800, 600);
		  TexturePaint tp = new TexturePaint(image, anchor);
		  
		  g2.setPaint(tp);
		  g2.fill(obj1);
		  g2.fill(obj2);
		  g2.fill(obj3);
		  g2.fill(obj4);
		  g2.fill(obj5);
		  g2.fill(obj6);
		  //g2.fill(obj7);
		  g2.fill(Elli1);
		  g2.fill(Elli2);
		  
		  Stroke s = new BasicStroke();  
		  float[] dash = {15, 7};
		  s = new BasicStroke(10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0);
		  
		  GradientPaint gp = new GradientPaint(10, 10, Color.green, 50, 50, Color.cyan, true);

		  g2.setStroke(s);
		  g2.setPaint(gp);
		  g2.fill(obj7);
		  g2.setPaint(tp);
		  g2.draw(obj7);
		  		  
          
		  FoodShape apple = new FoodShape( 50, 150, 30, 30);
		  FoodShape apple1 = new FoodShape( 150, 450, 30, 30);
		  FoodShape apple2 = new FoodShape( 400, 200, 30, 30);
		  FoodShape apple3 = new FoodShape( 340, 540, 30, 30);
		  FoodShape apple4 = new FoodShape( 600, 500, 30, 30);
		  FoodShape apple5 = new FoodShape( 620, 90, 30, 30);
		  FoodShape apple6 = new FoodShape( 730, 300, 30, 30);
		  
		  g2.setColor(Color.red);

		  AlphaComposite al = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);

		  
		  if(player.intersects(apple.getBounds())) {
			  x++;  
		  }else if(player.intersects(apple1.getBounds())) {
			  x1++;  
		  }else if(player.intersects(apple2.getBounds())) {
			  x2++;  
		  }else if(player.intersects(apple3.getBounds())) {
			  x3++;  
		  }else if(player.intersects(apple4.getBounds())) {
			  x4++;  
		  }else if(player.intersects(apple5.getBounds())) {
			  x5++;  
		  }else if(player.intersects(apple6.getBounds())) {
			  x6++;  
		  }
		  
		  if(x > 0) {
			  g2.setComposite(al);
			  g2.fill(apple);
			  AlphaComposite al1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g2.setComposite(al1);
		  }else
		  g2.fill(apple);
		  
		  if(x1 > 0) {
			  g2.setComposite(al);
			  g2.fill(apple1);
			  AlphaComposite al1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g2.setComposite(al1);
		  }else
		  g2.fill(apple1);
		  
		  if(x2 > 0) {
			  g2.setComposite(al);
			  g2.fill(apple2);
			  AlphaComposite al1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g2.setComposite(al1);
		  }else
		  g2.fill(apple2);
		  
		  if(x3 > 0) {
			  g2.setComposite(al);
			  g2.fill(apple3);
			  AlphaComposite al1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g2.setComposite(al1);
		  }else
		  g2.fill(apple3);
		  
		  if(x4 > 0) {
			  g2.setComposite(al);
			  g2.fill(apple4);
			  AlphaComposite al1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g2.setComposite(al1);
		  }else
		  g2.fill(apple4);
		  
		  if(x5 > 0) {
			  g2.setComposite(al);
			  g2.fill(apple5);
			  AlphaComposite al1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g2.setComposite(al1);
		  }else
		  g2.fill(apple5);
		  
		  if(x6 > 0) {
			  g2.setComposite(al);
			  g2.fill(apple6);
			  AlphaComposite al1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
			  g2.setComposite(al1);
		  }else
		  g2.fill(apple6);
		  
		  
		  if(x > 0 && x1 > 0 && x2 > 0 && x3 > 0 && x4 > 0 && x5 > 0 && x6 > 0){
				g2.setFont(font);
				g2.setColor(Color.GREEN);
				g2.drawString("WINNER", 110, 300);
			}  
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			deltaX = -8;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			deltaX = 8;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			deltaY = 8;
		if(e.getKeyCode() == KeyEvent.VK_UP)
			deltaY = -8;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		deltaX = 0;
		deltaY = 0;
		
	}

	@Override
	public void run() {
		while(true) {
			//Update game state
			tx = tx + deltaX;
			ty = ty + deltaY;
			
			ang = (ang + 1) % 360;
			
			if(player != null)
				if(player.intersects(obj5.getBounds()) || player.intersects(obj6.getBounds()) || player.intersects(obj7.getBounds())) {
					tx = 50;
					ty = 550;
				}else if(Elli1.contains(tx, ty) || Elli2.contains(tx, ty)) {
					tx = 50;
					ty = 550;
				}
			
			
			
			if(tx - r < 20) {
				tx = 50;
				ty = 550;
			}else if(tx + r > 780) {
				tx = 50;
				ty = 550;
			}
			
			if(ty - r < 20) {
				tx = 50;
				ty = 550;
			}else if(ty + r > 580) {
				tx = 50;
				ty = 550;
			}

			
			repaint();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(selected) {
			deltaX = e.getX() - firstX;
			deltaY = e.getY() - firstY;
			
			at.setToTranslation(deltaX, deltaY);
			
			player = at.createTransformedShape(player);
			
			firstX += deltaX;
			firstY += deltaY;

			repaint();	
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(player.contains(e.getX(), e.getY())) {
			firstX = e.getX();
			firstY = e.getY();
			selected = true;
			}
		else {
			selected = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		deltaX = 0;
		deltaY = 0;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
		
		switch (pageIndex) {
		case 0:
			paintComponent(g);
			break;
		case 1:
			g.translate(-(int)pf.getImageableWidth(), 0);
			paintComponent(g);
			break;
			
		default:
			return NO_SUCH_PAGE;
		}
		return PAGE_EXISTS;		
	}
}
