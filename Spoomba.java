import java.util.*;
import java.awt.*;
import java.awt.geom.*;

public class Spoomba {
	
	private int screen_w;
	private int screen_h;
	private int body_w = 100;
	private int body_h = 100;
	private int seg_w = 70;
	private int seg_h = 20;
	private int claw_w = 24; //even number
	private int claw_h = 5;
	
	protected Body body = null;
	protected ArmSegment top_seg = null;
	protected ArmSegment mid_seg = null;
	protected ArmSegment low_seg = null;
	protected Waldos claws = null;
	
	public Spoomba(int screen_w, int screen_h, Vector<Line2D.Double> junk) {
		this.screen_w = screen_w;
		this.screen_h = screen_h;
		
		claws = new Waldos(screen_w/2-body_w/2-seg_w*3-claw_w/2, screen_h/2, claw_w, claw_h, null, seg_h, junk);
		
		low_seg = new ArmSegment(screen_w/2-body_w/2-seg_w*2, screen_h/2, seg_w, seg_h, claws);
		mid_seg = new ArmSegment(screen_w/2-body_w/2-seg_w, screen_h/2, seg_w, seg_h, low_seg);
		top_seg = new ArmSegment(screen_w/2-body_w/2, screen_h/2, seg_w, seg_h, mid_seg);
		
		body = new Body(screen_w/2, screen_h/2, body_w, body_h, top_seg);
	}
	
	public Body getBody() {
		return body;
	}
	
	public void draw(Graphics g) {
		body.draw(g);
		top_seg.draw(g);
		mid_seg.draw(g);
		low_seg.draw(g);
		claws.draw(g);
	}
	
	public void sendMouseClicked(int mouse_x, int mouse_y) {
		if (claws.contains(mouse_x, mouse_y)) {
			claws.setSelected();
			low_seg.clearSelected();
			mid_seg.clearSelected();
			top_seg.clearSelected();
			body.clearSelected();
		}
		else if (low_seg.contains(mouse_x, mouse_y)) {
			low_seg.setSelected();
			claws.clearSelected();
			mid_seg.clearSelected();
			top_seg.clearSelected();
			body.clearSelected();
		}
		else if (mid_seg.contains(mouse_x, mouse_y)) {
			mid_seg.setSelected();
			claws.clearSelected();
			low_seg.clearSelected();
			top_seg.clearSelected();
			body.clearSelected();
		}
		else if (top_seg.contains(mouse_x, mouse_y)) {
			top_seg.setSelected();
			claws.clearSelected();
			low_seg.clearSelected();
			mid_seg.clearSelected();
			body.clearSelected();
		}
		else if (body.contains(mouse_x, mouse_y)) {
			body.setSelected();
			claws.clearSelected();
			low_seg.clearSelected();
			mid_seg.clearSelected();
			top_seg.clearSelected();
		}
		else {
			claws.clearSelected();
			low_seg.clearSelected();
			mid_seg.clearSelected();
			top_seg.clearSelected();
			body.clearSelected();
		}
	}
	
	public void sendMouseScroll(int dir) {
		if (body.isSelected()) {
			//System.out.println("Scroll body "+dir);
			body.rotate(dir);
		}
		
		if (top_seg.isSelected()) {
			top_seg.rotate(dir);
		}
		
		if (mid_seg.isSelected()) {
			mid_seg.rotate(dir);
		}
		
		if (low_seg.isSelected()) {
			low_seg.rotate(dir);
		}
		
		if (claws.isSelected()) {
			claws.clamp(dir);
		}
	}
	
	public boolean isOnScreen() {
		if (body.getX()-body_w/2 < 0 || body.getX()+body_w/2 > screen_w || body.getY()-body_h/2 < 0 || body.getY()+body_h/2 > screen_h) {
			return false;
		}
		else if (top_seg.getX() < 0 || top_seg.getX() > screen_w || top_seg.getY() < 0 || top_seg.getY() > screen_h) {
			return false;
		}
		else if (mid_seg.getX() < 0 || mid_seg.getX() > screen_w || mid_seg.getY() < 0 || mid_seg.getY() > screen_h) {
			return false;
		}
		else if (low_seg.getX() < 0 || low_seg.getX() > screen_w || low_seg.getY() < 0 || low_seg.getY() > screen_h) {
			return false;
		}/*
		else if (claws.getX() < 0 || claws.getX() > screen_w || claws.getY() < 0 || claws.getY() > screen_h) {
			return false;
		}*/
		else {
			return true;
		}
	}
}
