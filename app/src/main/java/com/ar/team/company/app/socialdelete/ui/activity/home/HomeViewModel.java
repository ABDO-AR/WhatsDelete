package com.ar.team.company.app.socialdelete.ui.activity.home;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ar.team.company.app.socialdelete.ar.images.ARImagesAccess;
import com.ar.team.company.app.socialdelete.ar.videos.ARVideosAccess;
import com.ar.team.company.app.socialdelete.ar.voices.ARVoicesAccess;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public class HomeViewModel extends AndroidViewModel {

    // MainFields(Images):
    private final MutableLiveData<List<Bitmap>> imagesMutableData = new MutableLiveData<>();
    private final LiveData<List<Bitmap>> imagesLiveData = imagesMutableData;
    // MainFields(Videos):
    private final MutableLiveData<List<File>> videosMutableData = new MutableLiveData<>();
    private final LiveData<List<File>> videosLiveData = videosMutableData;
    // MainFields(Voices):
    private final MutableLiveData<List<File>> voicesMutableData = new MutableLiveData<>();
    private final LiveData<List<File>> voicesLiveData = voicesMutableData;
    // Threads:
    private Thread imagesThread;
    private Thread videosThread;
    private Thread voicesThread;
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
    public void startVideoOperation(){
        // Working:
        videosThread = new Thread(this::videosThread);
        // StartWorkingThread:
        videosThread.start();
    }
    // Voices:
    public void startVoiceOperation(){
        // Working:
        voicesThread = new Thread(this::voicesThread);
        // StartWorkingThread:
        voicesThread.start();
    }
    // Documents:

    // Method(Thread):
    private void imagesThread() {
        // Initializing:
        List<Bitmap> bitmaps = ARImagesAccess.getImagesWithDirs(getApplication().getApplicationContext());
        // Developing:
        imagesMutableData.postValue(bitmaps);
    }

    private void videosThread() {
        // Initializing:
        List<File> videos = ARVideosAccess.getVideosWithDirs(getApplication().getApplicationContext());
        // Developing:
        videosMutableData.postValue(videos);
    }

    private void voicesThread() {
        // Initializing:
        List<File> voices = ARVoicesAccess.getVoicesWithDirs(getApplication().getApplicationContext());
        // Developing:
        voicesMutableData.postValue(voices);
    }

    // Getters(&Setters):
    public MutableLiveData<List<File>> getVideosMutableData() {
        return videosMutableData;
    }

    public LiveData<List<File>> getVideosLiveData() {
        return videosLiveData;
    }

    public MutableLiveData<List<Bitmap>> getImagesMutableData() {
        return imagesMutableData;
    }

    public LiveData<List<Bitmap>> getImagesLiveData() {
        return imagesLiveData;
    }

    public MutableLiveData<List<File>> getVoicesMutableData() {
        return voicesMutableData;
    }

    public LiveData<List<File>> getVoicesLiveData() {
        return voicesLiveData;
    }

    public Thread getVoicesThread() {
        return voicesThread;
    }

    public Thread getVideosThread() {
        return videosThread;
    }

    public Thread getImagesThread() {
        return imagesThread;
    }
}
