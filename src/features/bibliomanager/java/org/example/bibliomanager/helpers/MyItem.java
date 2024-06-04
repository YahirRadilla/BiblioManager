package org.example.bibliomanager.helpers;

public class MyItem {
    private Object[] properties;

    public MyItem(Object... properties) {
        this.properties = properties;
    }

    public Object getProperty(int index) {
        if (index >= 0 && index < properties.length) {
            return properties[index];
        }
        return null;
    }

    public int getPropertyCount() {
        return properties.length;
    }

    public void setProperty(int index, Object value) {
        if (index >= 0 && index < properties.length) {
            properties[index] = value;
        }
    }

    public Object[] getProperties() {
        return properties;
    }
}
