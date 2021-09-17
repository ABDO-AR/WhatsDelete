package com.ar.team.company.app.whatsdelete.ar.voices;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class ARVoicesAccess {

    // Fields:
    private final Context context;

    // Constructor:
    public ARVoicesAccess(Context context) {
        this.context = context;
    }

    // Method(Images):
    public List<File> getWhatsappVoicesDirectory() {
        // Initializing(Paths):
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String whatsappImagesPath = "/WhatsApp/Media/WhatsApp Voice Notes"; // (/202138).
        String finalPath = externalStorageDirectory + whatsappImagesPath;
        // Getting:
        File[] dirs = new File(finalPath).listFiles();
        if (dirs == null || dirs.length <= 0){
            // Initializing(Paths2):
            String whatsappImagesPath2 = "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Voice Notes";
            String finalPath2 = externalStorageDirectory + whatsappImagesPath2;
            // FieldsField:
            dirs = new File(finalPath2).listFiles(file -> isImage(file.getAbsolutePath()));
        }
        List<File> files = new ArrayList<>();
        // Checking(&Developing):
        for (File dir : Objects.requireNonNull(dirs)) {
            if (dir.isDirectory()) {
                // Initializing:
                File[] voices = dir.listFiles(file -> isImage(file.getAbsolutePath()));
                // Developing:
                files.addAll(Arrays.asList(Objects.requireNonNull(voices)));
            } else if (dir.getAbsolutePath().endsWith(".opus")) files.add(dir);
        }
        // Returning:
        return files;
    }

    // Methods(Checking):
    public boolean isImage(String filePath) {
        // Checking:
        return filePath.endsWith(".opus");
    }

    // Getters:
    public Context getContext() {
        return context;
    }
}
