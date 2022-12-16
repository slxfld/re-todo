package window.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import window.frame.MainFrameSingleton;

public class AbstractPanel extends JPanel implements IAbstractPanel {
	private static final long serialVersionUID = 1L;

	JButton new_button;

	JPanel thisPanel = this;

	JButton closeButton = new JButton("");
	JLabel closeLabel = new JLabel("x");

	public void resize() {
		closeButton.setLocation(getWidth() - 41, 1);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(21, 81, 89));
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.setColor(new Color(41, 164, 181));
		g.drawRect(0, 0, getWidth(), getHeight());
	}

	public void setupUIComponents() {
		this.setLayout(null);
		MainFrameSingleton.getInstance().setSize(this.getSize());

		closeButton.setFocusable(false);
		closeButton.setBounds(getWidth() - 41, 1, 40, 34);
		closeButton.setLayout(null);

		closeLabel.setBounds(16, 5, 20, 20);
		closeButton.add(closeLabel);

		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (thisPanel.getClass() == MainPanel.class) {
					System.exit(0);
				} else {
					MainFrameSingleton.getInstance().loadPanel(new MainPanel());
				}
			}
		});
		this.add(closeButton);
	}
}
