package window.panel;

import static window.ui.WindowType.MAIN;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import config.Config;
import lombok.Getter;
import window.frame.MainFrame;
import window.frame.MainFrameSingleton;
import window.ui.TabButton;
import window.ui.TaskButton;
import window.ui.WindowType;

@Getter
public class MainPanel extends AbstractPanel {

	private static final String RESOURCES_ICONS_COLLAPSE_PNG = "/resources/icons/collapse.png";
	private static final String RESOURCES_ICONS_ADD_PNG = "/resources/icons/add.png";

	private static final long serialVersionUID = 1L;

	int row_x = 5;
	int row_y = 0;

	private List<String> categories;

	public MainPanel this_obj = this;

	List<TaskButton> taskButtons = new ArrayList<>();
	List<TabButton> tabButtons = new ArrayList<>();

	public MainPanel() {
		MainFrame.currentWindowType = WindowType.MAIN;

		Config.load();
		this.setSize(0x171, 0x200);
		setupUIComponents();
	}

	@Override
	public void resize() {
		super.resize();

		for (TaskButton tb : taskButtons)
			tb.resize(this.getWidth() - 9, tb.getHeight());

		resizeTabButtons();
	}

	private void resizeTabButtons() {
		int index = 0;
		int width = ((this.getWidth() - 2) / categories.size());

		for (TabButton tb : tabButtons) {
			tb.setBounds(3 + (index * width), 37, width - 2, 25);
			index++;
		}
	}

	@Override
	public void setupUIComponents() {
		super.setupUIComponents();

		JButton addButton = new JButton(
				new ImageIcon(this.getClass().getResource(RESOURCES_ICONS_ADD_PNG)));

		addButton.setFocusable(false);
		addButton.setBounds(1, 1, 35, 34);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrameSingleton.getInstance()
						.loadPanel(new NewTaskPanel(true, "", MainFrame.currentTab, "", false));
			}
		});
		this.add(addButton);

		JButton collapseButton = new JButton(
				new ImageIcon(this.getClass().getResource(RESOURCES_ICONS_COLLAPSE_PNG)));
		collapseButton.setFocusable(false);
		collapseButton.setBounds(37, 1, 35, 34);
		collapseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrameSingleton.getInstance().loadPanel(new MinimalPanel());
			}
		});
		this.add(collapseButton);

		loadCategories();
		addTabButtons();
		addTaskButtons();
	}

	private void loadCategories() {
		categories = new ArrayList<>();
		categories.add("");

		Config.properties.entrySet().stream()
				.filter(entry -> entry.getKey().toString().contains("_category")).forEach(entry -> {
					if (!categories.contains(entry.getValue()))
						categories.add((String) entry.getValue());
				});
	}

	private void addTabButtons() {
		int index = 0;
		int width = ((0x170 - 2) / categories.size());
		for (String category : categories) {
			addTabButton(category, 3 + (index * width), 37, width - 2, 25);
			index++;
		}
	}

	private void addTabButton(String name, int x, int y, int w, int h) {
		TabButton tabButton = new TabButton(name);
		tabButton.setBounds(x, y, w, h);
		tabButton.addColorPanel();
		add(tabButton);
		tabButtons.add(tabButton);
	}

	public void addTaskButtons() {
		for (Entry<Object, Object> entry : Config.properties.entrySet())
			addTaskButton((String) entry.getKey(), (String) entry.getValue(), 5, 65 + row_y);
	}

	private void addTaskButton(String property, String value, int x, int y) {

		String key = (property.contains("_days")) ? property.substring(0, property.length() - 5)
				: "";

		if (!key.isEmpty()) {
			String category = "";
			if (Config.exists(key + "_category"))
				category = Config.properties.getProperty(key + "_category");

			if (category.equals(MainFrame.currentTab)) {
				boolean repeats = isTaskRepeating(key);
				TaskButton task_button = new TaskButton(MAIN, key, getExpiredTime(property, key),
						repeats);
				task_button.setLocation(x, y);
				add(task_button);

				taskButtons.add(task_button);
				row_y += 30;
				MainFrameSingleton.getInstance().setSize(this.getWidth(), 65 + row_y);
			}
		}
	}

	private boolean isTaskRepeating(String key) {
		boolean repeats;

		if (Config.properties.getProperty(key + "_repeating") == null)
			repeats = true;
		else
			repeats = Config.properties.getProperty(key + "_repeating").equals("true");
		return repeats;
	}

	/**
	 * returns the days until the task expires.
	 * 
	 * @param key
	 * @param name
	 * @return days until expiration. (if the TODO is more than 5 years in the
	 *         future returns - 999999999)
	 */
	private int getExpiredTime(String key, String name) {
		String date_string = (String) Config.properties.get(name + "_last_date");
		LocalDate date_after_days = LocalDate.parse(date_string)
				.plusDays(Integer.parseInt((String) Config.properties.get(key)));

		if (date_after_days.getYear() == LocalDate.now().getYear()) {
			return date_after_days.getDayOfYear() - LocalDate.now().getDayOfYear();
		} else if (date_after_days.getYear() == LocalDate.now().getYear() + 1) {
			return (date_after_days.getDayOfYear() + (365 - LocalDate.now().getDayOfYear()));
		} else if (date_after_days.getYear() == LocalDate.now().getYear() + 2) {
			return (365 * 1)
					+ (date_after_days.getDayOfYear() + (365 - LocalDate.now().getDayOfYear()));
		} else if (date_after_days.getYear() == LocalDate.now().getYear() + 3) {
			return (365 * 2)
					+ (date_after_days.getDayOfYear() + (365 - LocalDate.now().getDayOfYear()));
		} else if (date_after_days.getYear() == LocalDate.now().getYear() + 4) {
			return (365 * 3)
					+ (date_after_days.getDayOfYear() + (365 - LocalDate.now().getDayOfYear()));
		} else if (date_after_days.getYear() == LocalDate.now().getYear() + 5) {
			return (365 * 4)
					+ (date_after_days.getDayOfYear() + (365 - LocalDate.now().getDayOfYear()));
		}
		return -999999999;
	}

}
