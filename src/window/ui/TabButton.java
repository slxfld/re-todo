package window.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.Config;
import window.frame.MainFrame;
import window.frame.MainFrameSingleton;
import window.panel.CategoryPanel;
import window.panel.MainPanel;

public class TabButton extends JButton {

	private static final long serialVersionUID = 1L;

	String name;
	boolean undoneTasks = false;

	final JPanel dueColorPanel = new JPanel();

	public TabButton(boolean undoneTasks, String name) {
		this.name = name;
		this.undoneTasks = undoneTasks;

		setDefaults();
	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		dueColorPanel.setSize(w - 1, h);
	}

	private void setDueColor(boolean undoneTasks) {

		Color backgroundColor = new Color(255, 255, 255, 40);
		if (undoneTasks == true) {
			backgroundColor = new Color(200, 50, 50, 40);
		} else {
			backgroundColor = new Color(50, 50, 50, 40);
		}
		dueColorPanel.setSize(this.getWidth() - 1, this.getHeight());
		dueColorPanel.setBackground(backgroundColor);
		this.add(dueColorPanel);
	}

	public void setDefaults() {

		this.setLayout(null);
		this.setSize(100, 25);
		this.setEnabled(true);
		this.setText(name);

		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.NOBUTTON) {

				} else if (e.getButton() == MouseEvent.BUTTON1) {

					MainFrame.currentTab = name;
					MainFrameSingleton.getInstance().loadPanel(new MainPanel());

				} else if (e.getButton() == MouseEvent.BUTTON3) {

					boolean showInMinimal = true;
					if (Config.exists("category_show_in_minimal_" + name))
						showInMinimal = Config.properties
								.getProperty("category_show_in_minimal_" + name).equals("true");

					MainFrameSingleton.getInstance()
							.loadPanel(new CategoryPanel(showInMinimal, name));
				}
			}
		});
		setDueColor(undoneTasks);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
