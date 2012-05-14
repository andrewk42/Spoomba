import java.util.*;
import java.awt.*;
import java.awt.geom.*;

public class Waldos extends RobotPart {
	
	private Shape s_claw1, s_claw2, s_gap;
	private int init_x, init_y, init_w, init_h, arm_h;
	private Vector<AffineTransform> transforms = new Vector<AffineTransform>();
	private Vector<Integer> clampforms = new Vector<Integer>();
	private int clamp_offset = 0;
	private Vector<Line2D.Double> junk;
	
	public Waldos(int x, int y, int w, int h, RobotPart child, int arm_h, Vector<Line2D.Double> junk) {
		super(x, y, w, h, child);
		
		this.init_x = x;
		this.init_y = y;
		this.init_w = w;
		this.init_h = h;
		this.arm_h = arm_h;
		this.junk = junk;
		
		s_claw1 = new Rectangle(x-w/2, y-arm_h/2, w, h);
		s_claw2 = new Rectangle(x-w/2, y+arm_h/2-h, w, h);
		s_gap = new Rectangle(x-w/2, y-arm_h/2+h, w, arm_h-h*2);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		if (selected) {
			g2d.setColor(Color.CYAN);
			g2d.fill(s_claw1);
			g2d.fill(s_claw2);
		}
		else {
			g2d.setColor(Color.WHITE);
			g2d.fill(s_claw1);
			g2d.fill(s_claw2);
		}
		g2d.setColor(Color.BLACK);
		g2d.draw(s_claw1);
		g2d.draw(s_claw2);
	}
	
	public boolean contains(int x, int y) {
		return (s_claw1.contains(x, y) || s_claw2.contains(x, y) || s_gap.contains(x, y));
	}
	
	public synchronized void translate(double delta_x, double delta_y) {
		this.x += delta_x;
		this.y += delta_y;
		
		AffineTransform a = AffineTransform.getTranslateInstance(delta_x, delta_y);
		transforms.add(a);
		
		s_claw1 = a.createTransformedShape(s_claw1);
		s_claw2 = a.createTransformedShape(s_claw2);
		s_gap = a.createTransformedShape(s_gap);
		
		super.translate(delta_x, delta_y);
	}
	
	/**Rotation about a foreign point */
	public synchronized void rotate(int dir, double x, double y) {
		AffineTransform a = AffineTransform.getRotateInstance(dir*0.1, x, y);
		transforms.add(a);
		
		s_claw1 = a.createTransformedShape(s_claw1);
		s_claw2 = a.createTransformedShape(s_claw2);
		s_gap = a.createTransformedShape(s_gap);
		
		super.rotate(dir, x, y);
	}
	
	public synchronized void clamp(int dir) {
		if (clamp_offset+dir > 5 || clamp_offset+dir < -3) return;
		
		clamp_offset += dir;
		clampforms.add(dir);
		
		s_claw1 = new Rectangle(init_x-init_w/2, init_y-arm_h/2, init_w, init_h);
		s_claw2 = new Rectangle(init_x-init_w/2, init_y+arm_h/2-init_h, init_w, init_h);
		
		for (Integer i : clampforms) {
			s_claw1 = AffineTransform.getTranslateInstance(0, i).createTransformedShape(s_claw1);
			s_claw2 = AffineTransform.getTranslateInstance(0, -i).createTransformedShape(s_claw2);
		}
		
		for (AffineTransform a : transforms) {
			s_claw1 = a.createTransformedShape(s_claw1);
			s_claw2 = a.createTransformedShape(s_claw2);
		}
		
		if (clamp_offset > 3 && dir > 0) {
			for (Line2D l : junk) {
				double m = (l.getY2() - l.getY1())/(l.getX2() - l.getX1());
				
				for (int i = 0; i < (l.getX2()-l.getX1()); i++) {
				
					if (s_gap.contains(l.getX1()+i, l.getY1()+i*m)) {
						junk.remove(l);
						System.out.println("collision detected");
						return;
					}
				}
			}
		}
	}
}
