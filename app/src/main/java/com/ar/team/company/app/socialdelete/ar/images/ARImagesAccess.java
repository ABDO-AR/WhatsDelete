package com.ar.team.company.app.socialdelete.ar.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.ar.team.company.app.socialdelete.ar.access.ARAccess;
import com.ar.team.company.app.socialdelete.control.preferences.ARPreferencesManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class ARImagesAccess {

    // Fields:
    private final Context context;
    public static List<File> staticFiles;

    // Constructor:
    public ARImagesAccess(Context context) {
        this.context = context;
    }

    // Method(Static):
    public static List<Bitmap> getImagesWithDirs(Context context) {
        // Initializing:
        ARPreferencesManager manager = new ARPreferencesManager(context, ARPreferencesManager.MODE_FILES);
        File imagesDir = ARAccess.getAppDir(context, ARAccess.IMAGES_DIR);
        File[] whatsAppImagesFiles = getImagesFiles();
        List<Bitmap> bitmaps = new ArrayList<>();
        // Initializing(State):
        boolean state1 = Objects.requireNonNull(imagesDir.listFiles()).length != 0;
        // Looping:
        if (state1) {
            // Checking(Fields):
            String whatsapp = manager.getStringPreferences(ARPreferencesManager.IMAGE_COPIED_FILES);
            StringBuilder copied = new StringBuilder();
            // If it reached to here that's mean that there are already copied images.
            // Now we will start a simple for loop and checking each file by name:
            for (File copiedFile : Objects.requireNonNull(imagesDir.listFiles())) {
                // Getting all files name:
                copied.append(copiedFile.getName()).append(",");
                // Adding:
                bitmaps.add(ARBitmapHelper.decodeBitmapFromFile(copiedFile.getAbsolutePath(), 120, 120));
            }
            // We will start checking if file contains this new file or not:
            for (File file : whatsAppImagesFiles) {
                // Checking:
                if (!whatsapp.contains(file.getName()) && !copied.toString().contains(file.getName())) {
                    // NotifyManager:
                    manager.setStringPreferences(ARPreferencesManager.IMAGE_COPIED_FILES, whatsapp + file.getName() + ",");
                    // Here we will start copy operation because that was new file:
                    ARAccess.copy(file, new File(imagesDir.getAbsolutePath() + "/" + file.getName()));
                }
            }
        } else {
            // Looping:
            for (File file : whatsAppImagesFiles) {
                // NotifyManager:
                manager.setStringPreferences(ARPreferencesManager.IMAGE_COPIED_FILES, file.getName() + ",");
                // Start copy operation:
                ARAccess.copy(file, new File(imagesDir.getAbsolutePath() + "/" + file.getName()));
                // Adding:
                bitmaps.add(ARBitmapHelper.decodeBitmapFromFile(file.getAbsolutePath(), 120, 120));
            }
        }
        // AddStaticFiles:
        staticFiles = Arrays.asList(Objects.requireNonNull(imagesDir.listFiles()));
        // Retuning:
        return bitmaps;
    }

    private static File[] getImagesFiles() {
        // Initializing(Paths):
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String whatsappImagesPath = "/WhatsApp/Media/WhatsApp Images";
        String finalPath = externalStorageDirectory + whatsappImagesPath;
        // Initializing(Paths2):
        String whatsappImagesPath2 = "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images";
        String finalPath2 = externalStorageDirectory + whatsappImagesPath2;
        // FieldsField:
        File[] files = new File(finalPath).listFiles(file -> isImages(file.getAbsolutePath()));
        // Checking:
        try {
            // Initializing:
            String tryingPath = Objects.requireNonNull(files)[0].getAbsolutePath();
            // Returning:
            return files;
        } catch (NullPointerException e) {
            // Returning:
            return new File(finalPath2).listFiles(file -> isImages(file.getAbsolutePath()));
        }
    }

    // Method(Images):
    @Deprecated
    private File[] getWhatsappImagesDirectory() {
        // Initializing(Paths):
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String whatsappImagesPath = "/WhatsApp/Media/WhatsApp Images";
        String finalPath = externalStorageDirectory + whatsappImagesPath;
        // Initializing(Paths2):
        String whatsappImagesPath2 = "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images";
        String finalPath2 = externalStorageDirectory + whatsappImagesPath2;
        // FieldsField:
        File[] files = new File(finalPath).listFiles(file -> isImage(file.getAbsolutePath()));
        // Checking:
        try {
            // Initializing:
            String tryingPath = Objects.requireNonNull(files)[0].getAbsolutePath();
            // Returning:
            return files;
        } catch (NullPointerException e) {
            // Returning:
            return new File(finalPath2).listFiles(file -> isImage(file.getAbsolutePath()));
        }
    }

    @Deprecated
    public List<Bitmap> getWhatsappImagesBitmaps() {
        // Initializing:
        File[] files = getWhatsappImagesDirectory();
        List<Bitmap> bitmaps = new ArrayList<>();
        // AddingBitmaps:
        if (files != null) {
            for (File file : files) {
                // Adding:
                bitmaps.add(ARBitmapHelper.decodeBitmapFromFile(file.getAbsolutePath(), 120, 120));
            }
            // Adding:
            staticFiles = Arrays.asList(files);
            // Developing:
            return bitmaps;
        } else return null;
    }

    // InnerClasses:
    @SuppressWarnings("SuspiciousNameCombination")
    public static class ARBitmapHelper {

        // Methods(Decoding):
        public static Bitmap decodeBitmapFromFile(String imagePath, int reqWidth, int reqHeight) {
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imagePath, options);
            // Calculate inSampleSize
            options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);
            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(imagePath, options);
        }

        // Methods(Calculating):
        private static int calculateSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
            // Raw Height And Width Of Image:
            final int height = options.outHeight;
            final int width = options.outWidth;
            // SampleSize:
            int inSampleSize = 1;
            // Checking:
            if (height > reqHeight || width > reqWidth) {
                // Initializing:
                final int halfHeight = height / 2;
                final int halfWidth = width / 2;
                // Developing:
                // Calculate the largest inSampleSize value that is a power of 2 and
                // keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
            // Retuning:
            return inSampleSize;
        }
    }

    // Methods(Checking):
    @Deprecated
    public boolean isImage(String filePath) {
        // Checking:
        return filePath.endsWith(".jpg") || filePath.endsWith(".png");
    }

    public static boolean isImages(String filePath) {
        // Checking:
        return filePath.endsWith(".jpg") || filePath.endsWith(".png");
    }

    // Getters:
    public Context getContext() {
        return context;
    }
}
