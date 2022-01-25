package fr.ensisa.rados.kfet;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Picture {

    private File photoFile;

    private String createFilename() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("photo");
        tmp.append("-");
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd_HHmmss");
        tmp.append(formater.format(Calendar.getInstance().getTime()));
        tmp.append(".");
        tmp.append("jpg");
        return tmp.toString();
    }

    private File createFile (Context context, String directory, String filename) {
        File folder = context.getExternalFilesDir(directory);
        if (folder == null) return null;
        File file = new File (folder, filename);
        return file;
    }

    public void createPhotoFile (Context context) {
        String filename = createFilename ();
        photoFile = createFile(context, Environment.DIRECTORY_PICTURES, filename);
    }

    private static final String FILE_PROVIDER = "fr.uha.hassenforder.teams.fileprovider";
    public Uri getPhotoURI (Context context) {
        return FileProvider.getUriForFile(context, FILE_PROVIDER, photoFile);
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public Uri addPictureToGallery(Context context) {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "picture");
        values.put(MediaStore.Images.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
        Uri base = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        Uri newUri = contentResolver.insert(base, values);
        return newUri;
    }

    static private InputStream getInputStream (Context context, String filename) {
        try {
            FileInputStream is = new FileInputStream(new File(filename));
            return is;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    static public Bitmap getBitmapFromUri(Context context, String filename, int targetW, int targetH) {
        try {
            if (filename == null) return null;
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            InputStream is = getInputStream(context, filename);
            BitmapFactory.decodeStream(is, null, bmOptions);
            is.close();

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            InputStream is2 = getInputStream(context, filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is2, null, bmOptions);
            is2.close();
            return bitmap;
        } catch (Exception e) {
        }
        return null;
    }

}

