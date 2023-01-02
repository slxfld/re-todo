package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import lombok.NoArgsConstructor;
import window.frame.MainFrame;
import window.frame.MainFrameSingleton;
import window.panel.MainPanel;
import window.panel.MinimalPanel;

@NoArgsConstructor
public class Config {

	private static final String USER_HOME = "/home/" + System.getProperty("user.name");
	private static final String RETODO_CONF_FILE = System.getenv("APPDATA") + "/retodo.conf";
	private static final String LOCAL_SHARE_APPLICATIONS_RETODO_CONF = "/.local/share/applications/retodo.conf";

	public static Properties properties = new Properties();

	private static Timer refresh;

	static boolean fileExists = false;

	public static void init() {
		fileExists = (new File(RETODO_CONF_FILE).exists());
		if (!fileExists) {
			properties.put("initialized", "true");
			save();
		}

		try {
			refresh = new Timer();
			refresh.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					reloadPanel();
				}
			}, 100, (60000 * 60));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void reloadPanel() {
		switch (MainFrame.currentWindowType) {
		case MAIN:
			MainFrameSingleton.getInstance().loadPanel(new MainPanel());
			break;
		case MINIMAL:
			MainFrameSingleton.getInstance().loadPanel(new MinimalPanel());
			break;
		}
	}

	public static void save() {
		try {
			FileOutputStream stream = new FileOutputStream(getConfigPath());
			if (properties.size() == 0 && fileExists)
				throw new Exception("no elements");
			properties.store(stream, null);
			stream.flush();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void load() {
		try {
			FileInputStream stream = new FileInputStream(getConfigPath());
			properties.load(stream);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void put(String propertyName, String value) {
		properties.put(propertyName, value);
	}

	public static void remove(String propertName) {
		properties.remove(propertName);
	}

	public static boolean exists(String propertyName) {
		return (properties.getProperty(propertyName) != null);
	}

	public static String get(String propertyName) {
		if (exists(propertyName))
			return Config.properties.getProperty(propertyName);
		return "";
	}

	private static String getConfigPath() {
		String os = (System.getProperty("os.name").toLowerCase());
		if (os.contains("win")) {
			return RETODO_CONF_FILE;
		} else if (os.contains("nux") || os.contains("nix") || os.contains("aix")
				|| os.contains("mac")) {
			return USER_HOME + LOCAL_SHARE_APPLICATIONS_RETODO_CONF;
		} else {
			return "/retodo.conf";
		}
	}
}
