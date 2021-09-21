package com.ar.team.company.app.socialdelete.ar.access;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class ARAccess {

    // MainFields:
    public static Map<String, File> MAIN_FILE_MAP;
    // Fields(Root):
    public static final String ROOT_DIR = "SocialDelete App";
    // Fields(SubRoot):
    public static final String IMAGES_DIR = ROOT_DIR + " Images";
    public static final String VIDEOS_DIR = ROOT_DIR + " Videos";
    public static final String VOICES_DIR = ROOT_DIR + " Voices";
    public static final String DOCUMENTS_DIR = ROOT_DIR + " Document";
    // Fields(Debug):
    private static final String TAG = "ARAccess";

    // Methods(Main):
    public static File getAppDir(Context context, String dir) {
        // Checking:
        if (MAIN_FILE_MAP == null) {
            // Initializing:
            createAccessDir(context, ROOT_DIR);
            // Developing:
            MAIN_FILE_MAP = createAccessDirs(context, IMAGES_DIR, VIDEOS_DIR, VOICES_DIR, DOCUMENTS_DIR);
        }
        // Returning:
        return MAIN_FILE_MAP.get(dir);
    }

    // Methods(Access):
    @SuppressWarnings({"ResultOfMethodCallIgnored", "UnusedReturnValue"})
    public static File createAccessDir(Context context, String dir) {
        // Initializing:
        File accessDir = new File(context.getExternalFilesDir(null), dir);
        // Checking:
        if (!accessDir.exists()) {
            // Checking:
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Trying:
                try {
                    // Creating:
                    Files.createDirectory(Paths.get(accessDir.getAbsolutePath()));
                } catch (IOException e) {
                    // Debugging:
                    Log.d(TAG, "createAccessDir: " + e.toString());
                }
            } else accessDir.mkdir();
        }
        // Retuning:
        return accessDir;
    }

    // Methods(AccessDirs):
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Map<String, File> createAccessDirs(Context context, String... dirs) {
        // Initializing:
        Map<String, File> fileMap = new HashMap<>();
        // Looping:
        for (String dir : dirs) {
            // Initializing:
            File accessDir = new File(context.getExternalFilesDir(null), ROOT_DIR + "/" + dir);
            // Checking:
            if (!accessDir.exists()) {
                // Checking:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Trying:
                    try {
                        // Creating:
                        Files.createDirectory(Paths.get(accessDir.getAbsolutePath()));
                    } catch (IOException e) {
                        // Debugging:
                        Log.d(TAG, "createAccessDir: " + e.toString());
                    }
                } else accessDir.mkdir();
                // Adding:
                fileMap.put(dir, accessDir);
            } else {
                // It's already exits:
                fileMap.put(dir, new File(context.getExternalFilesDir(null), ROOT_DIR + "/" + dir));
            }
        }
        // Retuning:
        return fileMap;
    }

    public static void copy(File src, File dst) {
        // Trying:
        try (InputStream in = new FileInputStream(src)) {
            // Trying:
            try (OutputStream out = new FileOutputStream(dst)) {
                // Initializing:
                byte[] buf = new byte[1024];
                int len;
                // Looping:
                while ((len = in.read(buf)) > 0) {
                    // Writing:
                    out.write(buf, 0, len);
                }
            } catch (IOException e) {
                // StackTrace:
                e.printStackTrace();
            }
        } catch (IOException e) {
            // StackTrace:
            e.printStackTrace();
        }
    }

}
