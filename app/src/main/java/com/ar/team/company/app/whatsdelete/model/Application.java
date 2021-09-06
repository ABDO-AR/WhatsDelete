package com.ar.team.company.app.whatsdelete.model;

import android.graphics.drawable.Drawable;

@SuppressWarnings("unused")
public class Application {

    // Fields:
    private final String name;
    private final String packageName;
    private final Drawable icon;
    private final Drawable logo;

    // Constructor:
    public Application(String name, String packageName, Drawable icon, Drawable logo) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.logo = logo;
    }

    // Getters:
    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public Drawable getLogo() {
        return logo;
    }
}
