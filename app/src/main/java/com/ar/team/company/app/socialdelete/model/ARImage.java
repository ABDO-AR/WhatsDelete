package com.ar.team.company.app.socialdelete.model;

import android.graphics.Bitmap;

import java.io.File;

@SuppressWarnings("unused")
public class ARImage {

    // Fields:
    private final Bitmap imageBitmap;
    private final File imageFile;

    // Constructor:
    public ARImage(Bitmap imageBitmap, File imageFile) {
        // Initializing
        this.imageBitmap = imageBitmap;
        this.imageFile = imageFile;
    }

    // Getters:
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public File getImageFile() {
        return imageFile;
    }
}
