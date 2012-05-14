import java.awt.*;
import java.awt.geom.*;

public class ArmSegment extends RobotPart {
	
	private Shape s_joint, s_seg;
	
	public ArmSegment(int x, int y, int w, int h, RobotPart child) {
		super(x, y, w, h, child);
		
		s_joint = new Ellipse2D.Double(x-h*0.75, y-h*0.75, h*1.5, h*1.5);
		s_seg = new Rectangle(x-w, y-h/2, w, h);
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		if (selected) {
			g2d.setColor(Color.CYAN);
			g2d.fill(s_seg);
		}
		else {
			g2d.setColor(Color.WHITE);
			g2d.fill(s_seg);
		}
		g2d.setColor(Color.BLACK);
		g2d.draw(s_seg);
		if (selected) {
			g2d.setColor(Color.CYAN);
			g2d.fill(s_joint);
		} else {
			g2d.setColor(Color.WHITE);
			g2d.fill(s_joint);
		}
		g2d.setColor(Color.BLACK);
		g2d.draw(s_joint);
	}
	
	public boolean contains(int x, int y) {
		return (s_joint.contains(x, y) || s_seg.contains(x, y));
	}
	
	public synchronized void translate(double delta_x, double delta_y) {
		s_joint = AffineTransform.getTranslateInstance(delta_x, delta_y).createTransformedShape(s_joint);
		s_seg = AffineTransform.getTranslateInstance(delta_x, delta_y).createTransformedShape(s_seg);
		
		this.x = s_joint.getBounds2D().getCenterX();
		this.y = s_joint.getBounds2D().getCenterY();
		
		super.translate(delta_x, delta_y);
	}
	
	/**Rotation about local point */
	public synchronized void rotate(int dir) {
		s_seg = AffineTransform.getRotateInstance(dir*0.1, this.x, this.y).createTransformedShape(s_seg);
		
		super.rotate(dir);
	}
	
	/**Rotation about a foreign point */
	public synchronized void rotate(int dir, double x, double y) {
		s_joint = AffineTransform.getRotateInstance(dir*0.1, x, y).createTransformedShape(s_joint);
		s_seg = AffineTransform.getRotateInstance(dir*0.1, x, y).createTransformedShape(s_seg);
		
		this.x = s_joint.getBounds2D().getCenterX();
		this.y = s_joint.getBounds2D().getCenterY();
		
		super.rotate(dir, x, y);
	}
}
