package com.ar.team.company.app.socialdelete.ui.activity.home;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ar.team.company.app.socialdelete.ar.images.ARImagesAccess;
import com.ar.team.company.app.socialdelete.control.adapter.ImagesAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class HomeViewModel extends AndroidViewModel {

    // MainFields:
    private final MutableLiveData<List<Bitmap>> data = new MutableLiveData<>();
    private final LiveData<List<Bitmap>> bitmapsLiveData = data;
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
        data.postValue(bitmaps);
    }

    // Getters(&Setters):
    public MutableLiveData<List<Bitmap>> getData() {
        return data;
    }

    public LiveData<List<Bitmap>> getBitmapsLiveData() {
        return bitmapsLiveData;
    }

    public Thread getImagesThread() {
        return imagesThread;
    }
}
