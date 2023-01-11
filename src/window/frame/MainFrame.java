package window.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;

import config.Config;
import util.ComponentResizer;
import window.panel.AbstractPanel;
import window.panel.NewTaskPanel;
import window.ui.WindowType;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Point point = new Point();
	public static MainFrame window;
	private AbstractPanel panel = new AbstractPanel();

	public static String currentTab = "";
	public static WindowType currentWindowType = WindowType.MAIN;
	public static int WIDTH = 0x171;
	public static int HEIGHT = 0x200;

	public MainFrame() {
		setup_frame();
	}

	public MainFrame(Point location) {
		setup_frame();
		this.setLocation(location);
	}

	public void loadPanel(AbstractPanel panel) {
		remove(this.panel);
		this.panel = panel;
		add(this.panel);
		repaint();
	}

	private void setup_frame() {
		setup_frame_settings();
		addListeners();
		this.setVisible(true);
	}

	ComponentResizer cr;

	private void setup_frame_settings() {
		this.setTitle("re-todo");
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setType(Type.UTILITY);
		this.setUndecorated(true);
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(true);

		cr = new ComponentResizer();
		cr.setSnapSize(new Dimension(15, 15));
		cr.registerComponent(this);
	}

	private void addListeners() {
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				point.x = e.getX();
				point.y = e.getY();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				if (!cr.resizing) {
					Point p = MainFrameSingleton.getInstance().getLocation();
					MainFrameSingleton.getInstance().setLocation(p.x + e.getX() - point.x,
							p.y + e.getY() - point.y);
				}
			}
		});

		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				panel.resize();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}

		});
	}

	public static void editTask(String name) {

		boolean repeats;
		String category = "";

		if (Config.properties.getProperty(name + "_category") != null)
			category = Config.properties.getProperty(name + "_category");

		if (Config.properties.getProperty(name + "_repeating") == null)
			repeats = true;
		else
			repeats = Config.properties.getProperty(name + "_repeating").equals("true");

		MainFrameSingleton.getInstance().loadPanel(new NewTaskPanel(false, name, category,
				Config.properties.getProperty(name + "_days"), repeats));
	}
}
