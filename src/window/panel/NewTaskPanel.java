package window.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import config.Config;
import window.frame.MainFrameSingleton;

public class NewTaskPanel extends AbstractPanel {
	private static final long serialVersionUID = 1L;

	boolean nameChangeable;
	boolean repeating = false;

	String presetName = "";
	String presetDays = "";
	String category;

	public NewTaskPanel(boolean nameChangeable, String task_name, String category, String days,
			boolean repeats) {
		this.nameChangeable = nameChangeable;
		this.presetName = task_name;
		this.category = category;
		this.presetDays = days;
		this.repeating = repeats;

		this.setSize(0x171, 0x0A0);
		setupUIComponents();
	}

	@Override
	public void setupUIComponents() {
		super.setupUIComponents();

		JLabel nameLabel = new JLabel((nameChangeable) ? "todo name" : presetName);
		nameLabel.setBounds(25, 0, 150, 25);
		this.add(nameLabel);

		JTextField taskNameField = new JTextField();
		taskNameField.setEnabled(nameChangeable);
		taskNameField.setBounds(25, 25, 300, 25);
		if (!presetName.isEmpty()) {
			taskNameField.setText(presetName);
		}
		this.add(taskNameField);

		JLabel daysLabel = new JLabel("days");
		daysLabel.setBounds(25, 50, 50, 25);
		this.add(daysLabel);

		JSpinner taskRepeatDays = new JSpinner();
		taskRepeatDays.setBounds(25, 75, 125, 25);
		if (!presetDays.isEmpty()) {
			taskRepeatDays.setValue(Integer.parseInt(presetDays));
		}
		this.add(taskRepeatDays);

		JLabel repeatingLabel = new JLabel("repeating");
		repeatingLabel.setBounds(25, 100, 120, 30);
		add(repeatingLabel);

		JCheckBox repeatingCheckbox = new JCheckBox();
		repeatingCheckbox.setBounds(100, 110, 25, 16);
		repeatingCheckbox.setSelected(repeating);
		repeatingCheckbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				repeating = e.getStateChange() == 1;
			}
		});
		add(repeatingCheckbox);

		JLabel categoryLabel = new JLabel("category");
		categoryLabel.setBounds(180, 50, 100, 25);
		add(categoryLabel);

		JTextField categoryField = new JTextField(category);
		categoryField.setBounds(180, 70, 100, 25);
		add(categoryField);

		JButton saveButton = new JButton("Save");
		saveButton.setBounds(135, 108, 70, 25);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.put("" + taskNameField.getText() + "_days", "" + taskRepeatDays.getValue());
				Config.put("" + taskNameField.getText() + "_category",
						"" + categoryField.getText());
				Config.put("" + taskNameField.getText() + "_last_date", LocalDate.now().toString());

				System.out.println("date:" + Config.get(taskNameField.getText() + "_last_date"));

				System.out.println(LocalDate.now().toString());

				Config.put("" + taskNameField.getText() + "_repeating", "" + repeating);

				Config.save();

				MainFrameSingleton.getInstance().loadPanel(new MainPanel());
			}
		});
		this.add(saveButton);

		JButton cancelButton = new JButton("cancel");
		cancelButton.setBounds(215, 108, 70, 25);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrameSingleton.getInstance().loadPanel(new MainPanel());
			}
		});
		this.add(cancelButton);

		JButton deleteButton = new JButton("delete");
		deleteButton.setBounds(295, 108, 70, 25);
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Config.remove(taskNameField.getText() + "_days");
				Config.remove(taskNameField.getText() + "_last_date");
				try {
					Config.remove(taskNameField.getText() + "_repeating");
				} catch (Exception e2) {
				}
				try {
					Config.remove(taskNameField.getText() + "_category");
				} catch (Exception e2) {
				}

				Config.save();
				MainFrameSingleton.getInstance().loadPanel(new MainPanel());
			}
		});
		this.add(deleteButton);

	}
}