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

@SuppressWarnings("unused")
public class ARAccess {

    // Fields(Root):
    public static final String ROOT_DIR = "SocialDelete App";
    // Fields(SubRoot):
    public static final String IMAGES_DIR = ROOT_DIR + " Images";
    public static final String VIDEOS_DIR = ROOT_DIR + " Videos";
    public static final String VOICES_DIR = ROOT_DIR + " Voices";
    public static final String DOCUMENTS_DIR = ROOT_DIR + " Document";
    // Fields(Debug):
    private static final String TAG = "ARAccess";

    // Methods(Access):
    @SuppressWarnings("ResultOfMethodCallIgnored")
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

    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }

}
