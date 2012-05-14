import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;


public class Environment extends JPanel {

	protected Spoomba robot = null;
	protected Physics phys = null;
	protected Vector<Line2D.Double> junk = new Vector<Line2D.Double>();
	
	public Environment(int screen_width, int screen_height) {
		super();
		setBackground(Color.WHITE);
		
		generateJunk(30, 75, screen_width, screen_height);
		
		robot = new Spoomba(screen_width, screen_height, junk);
	}
	
	public void generateJunk(int amount, int scale, int screen_w, int screen_h) {
		double x1, x2, y1, y2;
		Line2D.Double s;
		
		for (int i = 0; i < amount; i++) {
			x1 = Math.random()*screen_w;
			x2 = x1 + Math.random()*scale;
			y1 = Math.random()*screen_h;
			y2 = y1 + Math.random()*scale;
			
			s = new Line2D.Double(x1, y1, x2, y2);
			junk.add(s);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		robot.draw(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		for (Shape s : junk) {
			g2d.draw(s);
		}
	}
	
	public void sendMouseClicked(int mouse_x, int mouse_y) {
		robot.sendMouseClicked(mouse_x, mouse_y);
	}
	
	public void sendMouseScroll(int dir) {
		robot.sendMouseScroll(dir);
	}
	
	public void sendKeyPress(int key) {
		if (robot.getBody().isSelected()) {
			if (phys == null || phys.isDone()) {
				phys = new Physics(this, robot, key);
				phys.execute();
			}
			else {
				phys.stop();
			}
		}
	}
}

class Physics extends SwingWorker<Integer, Object> {
	protected Environment e = null;
	protected Spoomba s = null;
	protected int dir;
	protected boolean done = false;
	
	public Physics(Environment e, Spoomba s, int dir) {
		super();
		
		this.e = e;
		this.s = s;
		this.dir = dir;
	}
	
	public void stop() {
		done = true;
	}
	
    @Override
    public Integer doInBackground() {
    	while (!done) {
    		if (!s.isOnScreen()) {
    			done = true;
    		}
    		synchronized(s) {
    			switch(dir) {
	    			case KeyEvent.VK_LEFT:
	    				s.getBody().translate(-0.2, 0);
	    				break;
	    			case KeyEvent.VK_RIGHT:
	    				s.getBody().translate(0.2, 0);
	    				break;
	    			case KeyEvent.VK_UP:
	    				s.getBody().translate(0, -0.2);
	    				break;
	    			case KeyEvent.VK_DOWN:
	    				s.getBody().translate(0, 0.2);
	    				break;
    			}
    			e.repaint();
    		}
    		
    		try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return new Integer(0);
    }

    @Override
    protected void done() {
        try { 
            System.out.println(get());
        } catch (Exception ignore) {
        }
    }
}
