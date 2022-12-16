package launcher;

import javax.swing.UIManager;

import config.Config;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import mdlaf.themes.MaterialLiteTheme;
import mdlaf.themes.MaterialOceanicTheme;

public class Launch {
	private static final String MARS = "mars";
	private static final String OCEAN = "ocean";
	private static final String LITE = "lite";

	public static void main(String args[]) {
		setSkin(args);
		Config.init();
	}

	private static void setSkin(String[] args) {
		try {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase(LITE)) {
					UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
				} else if (args[0].equalsIgnoreCase(OCEAN)) {
					UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialOceanicTheme()));
				} else if (args[0].equalsIgnoreCase(MARS)) {
					UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
				}
			} else {
				UIManager.setLookAndFeel(new MaterialLookAndFeel(new MaterialLiteTheme()));
			}
		} catch (Exception e) {
			System.out.println("setting look and feel failed: " + e.getMessage());
		}
	}
}
