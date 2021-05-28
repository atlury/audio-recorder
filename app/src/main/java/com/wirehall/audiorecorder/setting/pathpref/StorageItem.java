package com.wirehall.audiorecorder.setting.pathpref;

import androidx.annotation.NonNull;

import java.io.File;

public class StorageItem {
  public static final String PARENT_DIR_NAME = "..";
  public static final String CHILD_DIR_PREFIX = "❐ ";
  private String name;
  private String path;

  public StorageItem(String name, String path) {
    this.name = name;
    this.path = path;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStylishName() {
    return getName().equals(PARENT_DIR_NAME) ? getName() : CHILD_DIR_PREFIX + getName();
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getParent() {
    return getPath() != null ? new File(getPath()).getParent() : null;
  }

  @NonNull
  @Override
  public String toString() {
    return "StorageItem{" + "name='" + name + '\'' + ", path='" + path + '\'' + '}';
  }
}
