package com.ar.team.company.app.whatsdelete.ar.videos;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ARVideosAccess {

    // Fields:
    private final Context context;

    // Constructor:
    public ARVideosAccess(Context context) {
        this.context = context;
    }

    // Method(Images):
    public List<File> getWhatsappVideosDirectory() {
        // Initializing(Paths):
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String whatsappImagesPath = "/WhatsApp/Media/WhatsApp Video";
        String finalPath = externalStorageDirectory + whatsappImagesPath;
        // Returning:
        return Arrays.asList(Objects.requireNonNull(new File(finalPath).listFiles(file -> isImage(file.getAbsolutePath()))));
    }

    // Methods(Checking):
    public boolean isImage(String filePath) {
        // Checking:
        return filePath.endsWith(".mp4");
    }

    // Getters:
    public Context getContext() {
        return context;
    }
}
