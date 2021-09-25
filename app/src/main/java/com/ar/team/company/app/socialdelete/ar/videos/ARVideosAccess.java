package com.ar.team.company.app.socialdelete.ar.videos;

import android.content.Context;
import android.os.Environment;

import com.ar.team.company.app.socialdelete.ar.access.ARAccess;
import com.ar.team.company.app.socialdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.socialdelete.ui.activity.home.HomeActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ARVideosAccess {

    // Fields:
    private final Context context;

    // Constructor:
    public ARVideosAccess(Context context) {
        this.context = context;
    }

    // Method(Static):
    public static List<File> getVideosWithDirs(Context context) {
        // Control:
        HomeActivity.setVideosObserver(false);
        // Initializing:
        ARPreferencesManager manager = new ARPreferencesManager(context, ARPreferencesManager.MODE_FILES);
        File videosDir = ARAccess.getAppDir(context, ARAccess.VIDEOS_DIR);
        File[] whatsAppVideosFiles = getVideosFiles();
        List<File> returningFiles = new ArrayList<>();
        // Initializing(State):
        boolean state1 = Objects.requireNonNull(videosDir.listFiles()).length != 0;
        // Looping:
        if (state1) {
            // Checking(Fields):
            String whatsapp = manager.getStringPreferences(ARPreferencesManager.VIDEO_COPIED_FILES);
            StringBuilder copied = new StringBuilder();
            // If it reached to here that's mean that there are already copied images.
            // Now we will start a simple for loop and checking each file by name:
            for (File copiedFile : Objects.requireNonNull(videosDir.listFiles())) {
                // Checking:
                if (!copiedFile.isDirectory()) {
                    // Getting all files name:
                    copied.append(copiedFile.getName()).append(",");
                    // Adding:
                    returningFiles.add(copiedFile);
                }
            }
            // Checking:
            if (whatsAppVideosFiles != null) {
                // We will start checking if file contains this new file or not:
                for (File file : whatsAppVideosFiles) {
                    // Checking:
                    if (!whatsapp.contains(file.getName()) && !copied.toString().contains(file.getName()) && !file.isDirectory()) {
                        // NotifyManager:
                        manager.setStringPreferences(ARPreferencesManager.VIDEO_COPIED_FILES, whatsapp + file.getName() + ",");
                        // Here we will start copy operation because that was new file:
                        ARAccess.copy(file, new File(videosDir.getAbsolutePath() + "/" + file.getName()));
                    }
                }
            }
        } else {
            // Initializing:
            int tempIndex = 0;
            // Checking:
            if (whatsAppVideosFiles != null) {
                // Looping:
                for (File file : whatsAppVideosFiles) {
                    // NotifyManager:
                    manager.setStringPreferences(ARPreferencesManager.VIDEO_COPIED_FILES, manager.getStringPreferences(ARPreferencesManager.VIDEO_COPIED_FILES) + file.getName() + ",");
                    // Getting first 3 images:
                    if (tempIndex <= 1) {
                        // Start creating temp dir:
                        ARAccess.createTempDirAt(context, ARAccess.VIDEOS_DIR);
                    }
                    // Increment:
                    tempIndex++;
                }
            } else ARAccess.createTempDirAt(context, ARAccess.VIDEOS_DIR);
        }
        // ReRunObserver:
        HomeActivity.setVideosObserver(true);
        // Retuning:
        return returningFiles;
    }

    public static File[] getVideosFiles() {
        // Initializing(Paths):
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String whatsappImagesPath = "/WhatsApp/Media/WhatsApp Video";
        String finalPath = externalStorageDirectory + whatsappImagesPath;
        // Initializing(Paths2):
        String whatsappImagesPath2 = "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video";
        String finalPath2 = externalStorageDirectory + whatsappImagesPath2;
        // FieldsField:
        File[] backupFiles = new File(finalPath2).listFiles(file -> isVideos(file.getAbsolutePath()));
        File[] files = new File(finalPath).listFiles(file -> isVideos(file.getAbsolutePath()));
        // Checking:
        if (files == null || files.length <= 0) files = backupFiles;
        // Returning:
        return files;
    }

    // Methods(Checking):
    public static boolean isVideos(String filePath) {
        // Checking:
        return filePath.endsWith(".mp4");
    }

    // Getters:
    public Context getContext() {
        return context;
    }
}
