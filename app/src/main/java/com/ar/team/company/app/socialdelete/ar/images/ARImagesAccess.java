package com.ar.team.company.app.socialdelete.ar.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.widget.Toast;

import com.ar.team.company.app.socialdelete.ar.access.ARAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        File dir = ARAccess.createAccessDir(context, ARAccess.ROOT_DIR);
        List<File> files = Arrays.asList(getImagesFiles());
        try {
            ARAccess.copy(files.get(1),new File(dir + "/dds.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Developing:
        return null;
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
