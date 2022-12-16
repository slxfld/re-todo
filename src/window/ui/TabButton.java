package window.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.Config;
import window.frame.MainFrame;
import window.frame.MainFrameSingleton;
import window.panel.CategoryPanel;
import window.panel.MainPanel;

public class TabButton extends JButton {

	private static final long serialVersionUID = 1L;

	String name;

	public TabButton(String name) {
		this.name = name;

		setDefaults();
	}

	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
	}

	public void setDefaults() {

		this.setLayout(null);
		this.setSize(100, 25);
		this.setEnabled(true);

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

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void addColorPanel() {

		JPanel bgPanel = new JPanel();

		bgPanel.paintComponents(getGraphics());
		if (name.equals(MainFrame.currentTab)) {
			bgPanel.setSize(this.getWidth(), this.getHeight());
			bgPanel.setBackground(new Color(50, 50, 50, 40));
			this.add(bgPanel);
		}

		JLabel textLabel = new JLabel();
		textLabel.setText(name);
		textLabel.setLocation(10, 0);
		textLabel.setSize(this.getWidth(), this.getHeight());
		textLabel.setFont(new Font("", Font.BOLD, 16));
		add(textLabel);
	}
}
