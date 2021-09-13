package com.ar.team.company.app.whatsdelete.ar.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ARImagesAccess {

    // Fields:
    private final Context context;

    // Constructor:
    public ARImagesAccess(Context context) {
        this.context = context;
    }

    // Method(Images):
    public File[] getWhatsappImagesDirectory() {
        // Initializing(Paths):
        String externalStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
        String whatsappImagesPath = "/WhatsApp/Media/WhatsApp Images";
        String finalPath = externalStorageDirectory + whatsappImagesPath;
        // Returning:
        return new File(finalPath).listFiles(file -> isImage(file.getAbsolutePath()));
    }

    public List<Bitmap> getWhatsappImagesBitmaps() {
        // Initializing:
        File[] files = getWhatsappImagesDirectory();
        List<Bitmap> bitmaps = new ArrayList<>();
        // AddingBitmaps:
        for (File file : files) {
            // Adding:
            bitmaps.add(ARBitmapHelper.decodeBitmapFromFile(file.getAbsolutePath(), 120, 120));
        }
        // Developing:
        return bitmaps;
    }

    // InnerClasses:
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

    // Getters:
    public Context getContext() {
        return context;
    }
}