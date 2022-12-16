package window.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Config;
import window.frame.MainFrame;
import window.frame.MainFrameSingleton;
import window.panel.MainPanel;
import window.panel.MinimalPanel;

public class TaskButton extends JButton {

	private static final long serialVersionUID = 1L;

	private final int WIDTH = 359;
	private final int HEIGHT = 25;

	WindowType wtype;
	String key;
	int days;
	boolean repeats;

	final JPanel dueColorPanel = new JPanel();

	public TaskButton(WindowType wtype, String key, int days, boolean repeats) {
		this.wtype = wtype;
		this.key = key;
		this.days = days;
		this.repeats = repeats;

		setDefaults();
	}

	@Override
	public void resize(int w, int h) {
		super.resize(w, h);
		dueColorPanel.setSize(this.getWidth() - 1, this.getHeight());
	}

	private void setDueColor(int days) {

		Color backgroundColor = new Color(255, 255, 255, 40);
		if (days > 0) {
			if (days > 15)
				days = 15;
			backgroundColor = new Color(10, 100 + (days * 10), 10, 40);
		} else if (days < 0) {
			if (days < -20)
				days = -20;
			backgroundColor = new Color(50 + (-days * 10), 0, 0, 40);
		}
		dueColorPanel.setSize(this.getWidth() - 1, this.getHeight());
		dueColorPanel.setBackground(backgroundColor);
		this.add(dueColorPanel);
	}

	public void setDefaults() {
		JLabel repeat_label = new JLabel();
		repeat_label.setText((repeats) ? "R" : "");
		repeat_label.setLocation(5, 0);
		repeat_label.setSize(30, 25);
		repeat_label.setFont(new Font("Verdana", Font.BOLD, 12));
		add(repeat_label);

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.NOBUTTON) {

				} else if (e.getButton() == MouseEvent.BUTTON1) {

					boolean repeats;
					if (!Config.exists(key + "_repeating"))
						repeats = true;
					else
						repeats = Config.properties.getProperty(key + "_repeating").equals("true");

					if (repeats) {
						Config.properties.put(key + "_last_date", LocalDate.now().toString());
					} else {
						Config.properties.remove(key + "_days");
						Config.properties.remove(key + "_last_date");
						try {
							Config.properties.remove(key + "_category");
						} catch (Exception e2) {
						}
						try {
							Config.properties.remove(key + "_repeating");
						} catch (Exception e3) {
						}
					}

					Config.save();
					if (wtype == WindowType.MAIN)
						MainFrameSingleton.getInstance().loadPanel(new MainPanel());
					else if (wtype == WindowType.MINIMAL)
						MainFrameSingleton.getInstance().loadPanel(new MinimalPanel());

				} else if (e.getButton() == MouseEvent.BUTTON3) {

					MainFrame.editTask(key);
				}
			}
		});

		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setEnabled(true);
		this.setText(key + " (" + days + ")");
		setDueColor(days);

	}
}
