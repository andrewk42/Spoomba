import java.awt.*;

public abstract class RobotPart {
	
	protected double x, y;
	protected boolean selected = false;
	protected RobotPart child = null;
	
	public RobotPart(int x, int y, int w, int h, RobotPart child) {
		this.x = x;
		this.y = y;
		
		if (child != null) {
			this.child = child;
		}
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public abstract void draw(Graphics g);
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected() {
		if (selected) {
			selected = false;
		}
		else {
			selected = true;
		}
	}
	
	public void clearSelected() {
		selected = false;
	}
	
	public abstract boolean contains(int x, int y);
	
	public synchronized void translate(double delta_x, double delta_y) {
		if (child != null) {
			child.translate(delta_x, delta_y);
		}
	}
	
	/**Rotation about local point */
	public synchronized void rotate(int dir) {
		if (child != null) {
			child.rotate(dir, this.x, this.y);
		}
	}
	
	/**Rotation about a foreign point */
	public synchronized void rotate(int dir, double x, double y) {
		if (child != null) {
			child.rotate(dir, x, y);
		}
	}

}
