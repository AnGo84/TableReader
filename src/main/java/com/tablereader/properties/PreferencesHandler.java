package com.tablereader.properties;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * Created by AnGo on 19.01.2018.
 */
public class PreferencesHandler {

    public static final String LAST_FILE_PATH = "LAST_FILE_PATH";

    /**
     * Return last opened file.
     * If preference did not find return null.
     *
     * @return
     */
    public static File getPreferenceFilePath(Class aClass, String prefName) {
        Preferences prefs = Preferences.userNodeForPackage(aClass);

        String filePath = prefs.get(prefName, null);
        File file;
        if (filePath != null && (file = new File(filePath)).exists()) {
            return file;
        } else {
            return null;
        }
    }

    /**
     * Set path to the current opened file.
     *
     * @param file - file or null for clean
     */
    public static void setPreferenceFilePath(File file, Class aClass, String prefName) {
        Preferences prefs = Preferences.userNodeForPackage(aClass);
        if (file != null && file.exists()) {
            prefs.put(prefName, file.getPath());

        } else {
            prefs.remove(prefName);
        }
    }

    public static void setPreferenceString(String value, Class aClass, String prefName) {
        Preferences prefs = Preferences.userNodeForPackage(aClass);
        if (value != null) {
            prefs.put(prefName, value);

        } else {
            prefs.remove(prefName);
        }
    }

    public static String getPreferenceString(Class aClass, String prefName) {
        Preferences prefs = Preferences.userNodeForPackage(aClass);
        String value = prefs.get(prefName, null);
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }


//    public static GoogleAPIProject getGoogleAPIProject() {
//        GoogleAPIProject googleAPIProject = new GoogleAPIProject();
//        googleAPIProject.setUserName(getPreferenceString(MainApp.class, USER_NAME));
//        googleAPIProject.setProjectName(getPreferenceString(MainApp.class, PROJECT_NAME));
//        googleAPIProject.setPathToJson(getPreferenceFilePath(MainApp.class,JSON_FILE_PATH).getAbsolutePath());
//        return googleAPIProject;
//    }

}
