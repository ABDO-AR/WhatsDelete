package com.ar.team.company.app.socialdelete.ui.activity.home;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ar.team.company.app.socialdelete.ar.images.ARImagesAccess;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class HomeViewModel extends AndroidViewModel {

    // MainFields:
    private final MutableLiveData<List<Bitmap>> imagesMutableData = new MutableLiveData<>();
    private final LiveData<List<Bitmap>> imagesLiveData = imagesMutableData;
    // Thread:
    private Thread imagesThread;

    // Constructor:
    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    // Chats:
    // Status:
    // Images:
    public void startImageOperation(){
        // Working:
        imagesThread = new Thread(this::imagesThread);
        // StartWorkingThread:
        imagesThread.start();
    }
    // Videos:
    // Voices:
    // Documents:

    // Method(Thread):
    private void imagesThread() {
        // Initializing:
        // Adapter:
        List<Bitmap> bitmaps = ARImagesAccess.getImagesWithDirs(getApplication().getApplicationContext());
        // Developing:
        imagesMutableData.postValue(bitmaps);
    }

    // Getters(&Setters):
    public MutableLiveData<List<Bitmap>> getImagesMutableData() {
        return imagesMutableData;
    }

    public LiveData<List<Bitmap>> getImagesLiveData() {
        return imagesLiveData;
    }

    public Thread getImagesThread() {
        return imagesThread;
    }
}
