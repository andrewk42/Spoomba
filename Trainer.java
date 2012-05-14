import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Trainer extends JFrame {
	
	private int screen_width = 1024;
	private int screen_height = 768;
	protected Environment env = null;
	
	protected MouseAdapter mouse = new MouseAdapter() {

		public void mouseClicked(MouseEvent e) {
            System.out.println("mouseClicked at x="+e.getX()+", y="+e.getY());
            env.sendMouseClicked(e.getX(), e.getY());
            env.repaint();
		}
		
		public void mousePressed(MouseEvent e) {

		}

		public void mouseDragged(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {

		}
		
		public void mouseWheelMoved(MouseWheelEvent e) {
			env.sendMouseScroll(e.getWheelRotation());
			env.repaint();
		}

	};
	
	protected KeyListener keyboard = new KeyListener() {
		@Override
		public void keyPressed(KeyEvent e) {
			env.sendKeyPress(e.getKeyCode());
			env.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//System.out.println("KeyReleased: "+e.getKeyLocation());
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println("KeyTyped: "+e.getKeyChar());
		}
	};
	
	public Trainer() {
		this.setTitle("Spoomba Training by Andrew K");
		this.setLayout(new BorderLayout());
		this.setSize(screen_width, screen_height);
		this.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		env = new Environment(screen_width, screen_height);
		this.add(env, BorderLayout.CENTER);
		
		env.addMouseListener(mouse);
		env.addMouseMotionListener(mouse);
		env.addMouseWheelListener(mouse);
		env.requestFocusInWindow(); //required for keyboard
		env.addKeyListener(keyboard);
	}
	
	public static void main (String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Trainer();
			}
		});
	}
}
