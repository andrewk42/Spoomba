import java.awt.*;
import java.awt.geom.*;


public class Body extends RobotPart {

	private Shape s;
	
	public Body(int x, int y, int w, int h, RobotPart child) {
		super(x, y, w, h, child);
		
		s = new Rectangle(x-w/2, y-h/2, w, h);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		if (selected) {
			g2d.setColor(Color.CYAN);
			g2d.fill(s);
		}
		g2d.setColor(Color.BLACK);
		g2d.draw(s);
	}
	
	public boolean contains(int x, int y) {
		return s.contains(x, y);
	}
	
	public synchronized void translate(double delta_x, double delta_y) {
		this.x += delta_x;
		this.y += delta_y;
		
		s = AffineTransform.getTranslateInstance(delta_x, delta_y).createTransformedShape(s);
		
		super.translate(delta_x, delta_y);
	}
	
	public synchronized void rotate(int dir) {
		s = AffineTransform.getRotateInstance(dir*0.1, this.x, this.y).createTransformedShape(s);
		
		super.rotate(dir);
	}
	
	public synchronized void rotate(int dir, double x, double y) {
		s = AffineTransform.getRotateInstance(dir*0.1, x, y).createTransformedShape(s);
		
		super.rotate(dir, x, y);
	}
}
