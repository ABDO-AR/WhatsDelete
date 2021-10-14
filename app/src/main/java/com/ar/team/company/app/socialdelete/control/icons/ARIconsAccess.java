package com.ar.team.company.app.socialdelete.control.icons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.Log;

import com.ar.team.company.app.socialdelete.ar.access.ARAccess;
import com.ar.team.company.app.socialdelete.ar.images.ARImagesAccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("unused")
public class ARIconsAccess {

    // Fields:
    private final Context context;
    private final String sender;
    private final Icon icon;
    private final Bitmap bitmap;
    // Fields(Static):
    public static final String USER_ICON_DIR = "UserIcons";

    // Constructor:
    public ARIconsAccess(Context context, String sender, Icon icon) {
        // Initializing:
        this.context = context;
        this.sender = sender;
        this.icon = icon;
        // Initializing(Bitmap):
        this.bitmap = ((BitmapDrawable) icon.loadDrawable(context)).getBitmap();
    }

    // Methods:
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createUserIcon() {
        // Initializing:
        File userIconDir = new File(context.getExternalFilesDir(null), ARAccess.ROOT_DIR + "/" + USER_ICON_DIR);
        File userIconFile = new File(context.getExternalFilesDir(null), ARAccess.ROOT_DIR + "/" + USER_ICON_DIR + "/" + sender);
        File userIcon = new File(context.getExternalFilesDir(null), ARAccess.ROOT_DIR + "/" + USER_ICON_DIR + "/" + sender + "/" + sender + ".jpg");
        // Checking(&Creating):
        if (!userIconDir.exists()) {
            // Checking:
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Trying:
                try {
                    // Creating:
                    Files.createDirectory(Paths.get(userIconDir.getAbsolutePath()));
                    // Creating(UserIconFile):
                    creatingUserIconFile(userIconFile);
                } catch (IOException e) {
                    // Debugging:
                    Log.d(ARAccess.TAG, "createAccessDir: " + e.toString());
                }
            } else {
                // Creating:
                userIconDir.mkdir();
                // Creating(UserIconFile):
                creatingUserIconFile(userIconFile);
            }
        } else creatingUserIconFile(userIconFile);
        // Developing:
        createUserIcon(userIcon);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void creatingUserIconFile(File userIconFile) {
        // Checking:
        if (!userIconFile.exists()) {
            // Checking:
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Trying:
                try {
                    // Creating:
                    Files.createDirectory(Paths.get(userIconFile.getAbsolutePath()));
                } catch (IOException e) {
                    // Debugging:
                    Log.d(ARAccess.TAG, "createAccessDir: " + e.toString());
                }
            } else userIconFile.mkdir();
        }
    }

    private void createUserIcon(File userIcon) {
        // Developing:
        try {
            // Initializing:
            FileOutputStream out = new FileOutputStream(userIcon);
            // Compressing:
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            // Closing:
            out.flush();
            out.close();
        } catch (Exception e) {
            // Catching:
            e.printStackTrace();
        }
    }

    // Methods(Static):
    public static Bitmap getUserIcon(Context context, String sender) {
        // Initializing:
        File userIcon = new File(context.getExternalFilesDir(null), ARAccess.ROOT_DIR + "/" + USER_ICON_DIR + "/" + sender + "/" + sender + ".jpg");
        // Developing:
        return ARImagesAccess.ARBitmapHelper.decodeBitmapFromFile(userIcon.getAbsolutePath(), 50, 50);
    }

    // Getters:
    public Context getContext() {
        return context;
    }

    public String getSender() {
        return sender;
    }

    public Icon getIcon() {
        return icon;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
