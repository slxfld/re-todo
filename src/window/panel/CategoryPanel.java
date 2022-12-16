package window.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import config.Config;
import window.frame.MainFrameSingleton;

public class CategoryPanel extends AbstractPanel {

	private static final long serialVersionUID = -6781531014352929134L;

	JLabel categoryLabel = new JLabel();
	String categoryName;

	boolean showInMinimal = false;

	public CategoryPanel(boolean showInMinimal, String categoryName) {
		this.showInMinimal = showInMinimal;
		this.categoryName = categoryName;
		this.setSize(0x171, 0x0A0);
		setupUIComponents();
	}

	@Override
	public void setupUIComponents() {

		super.setupUIComponents();

		categoryLabel.setText(categoryName);
		categoryLabel.setBounds(5, 5, 100, 25);
		this.add(categoryLabel);

		JCheckBox showInMinimalCheck = new JCheckBox();
		showInMinimalCheck.setBounds(100, 110, 25, 16);
		showInMinimalCheck.setSelected(showInMinimal);
		showInMinimalCheck.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				showInMinimal = e.getStateChange() == 1;
			}
		});
		add(showInMinimalCheck);
		showInMinimalCheck.setBounds(20, 50, 300, 25);
		add(showInMinimalCheck);

		JLabel showLabel = new JLabel();
		showLabel.setBounds(30, 30, 300, 25);
		showLabel.setText("Show In Minimal Panel:");
		add(showLabel);

		JButton saveButton = new JButton("Save");
		saveButton.setSize(70, 25);
		saveButton.setLocation(135, 85 + 23);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.properties.put("category_show_in_minimal_" + categoryName,
						showInMinimal ? "true" : "false");

				Config.save();

				MainFrameSingleton.getInstance().loadPanel(new MainPanel());
			}
		});
		this.add(saveButton);

	}
}
