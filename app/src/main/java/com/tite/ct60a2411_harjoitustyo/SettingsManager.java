package com.tite.ct60a2411_harjoitustyo;

import java.io.Serializable;

public class SettingsManager implements Serializable {
    private static SettingsManager instance;
    private int languageIndex;
    private int fontSize;
    private int updateArchiveLength;

    private SettingsManager() {
        this.languageIndex = 0;
        this.fontSize = 16;
        this.updateArchiveLength = 14;
    }

    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = (SettingsManager) ObjectSaveUtils.readObject("SettingsManager.data");
            if (instance == null)
                instance = new SettingsManager();
        }
        return instance;
    }

    public void saveArchive() {
        ObjectSaveUtils.saveObject(instance, "SettingsManager.data");
    }

    public int getLanguageIndex() {
        return languageIndex;
    }

    public void setLanguageIndex(int languageIndex) {
        this.languageIndex = languageIndex;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getUpdateArchiveLength() {
        return updateArchiveLength;
    }

    public void setUpdateArchiveLength(int updateArchiveLength) {
        this.updateArchiveLength = updateArchiveLength;
    }
}
