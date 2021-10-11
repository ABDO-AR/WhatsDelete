package com.ar.team.company.app.socialdelete.ui.activity.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ar.team.company.app.socialdelete.ar.documents.ARDocumentsAccess;
import com.ar.team.company.app.socialdelete.ar.images.ARImagesAccess;
import com.ar.team.company.app.socialdelete.ar.status.ARStatusAccess;
import com.ar.team.company.app.socialdelete.ar.videos.ARVideosAccess;
import com.ar.team.company.app.socialdelete.ar.voices.ARVoicesAccess;
import com.ar.team.company.app.socialdelete.model.ARImage;
import com.ar.team.company.app.socialdelete.model.Document;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

@SuppressWarnings("unused")
public class HomeViewModel extends AndroidViewModel {

    // MainFields(Images):
    private final MutableLiveData<List<ARImage>> imagesMutableData = new MutableLiveData<>();
    private final LiveData<List<ARImage>> imagesLiveData = imagesMutableData;
    // MainFields(Videos):
    private final MutableLiveData<List<File>> videosMutableData = new MutableLiveData<>();
    private final LiveData<List<File>> videosLiveData = videosMutableData;
    // MainFields(Voices):
    private final MutableLiveData<List<File>> voicesMutableData = new MutableLiveData<>();
    private final LiveData<List<File>> voicesLiveData = voicesMutableData;
    // MainFields(Status):
    private final MutableLiveData<List<File>> statusMutableData = new MutableLiveData<>();
    private final LiveData<List<File>> statusLiveData = statusMutableData;
    // MainFields(Documents):
    private final MutableLiveData<List<Document>> documentsMutableData = new MutableLiveData<>();
    private final LiveData<List<Document>> documentsLiveData = documentsMutableData;
    // Threads:
    private Thread imagesThread;
    private Thread videosThread;
    private Thread voicesThread;
    private Thread statusThread;
    private Thread documentsThread;

    // Constructor:
    public HomeViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    // Images:
    public void startImageOperation(Context context) {
        // Working:
        imagesThread = new Thread(() -> imagesThread(context));
        // StartWorkingThread:
        imagesThread.start();
    }

    // Videos:
    public void startVideoOperation(Context context) {
        // Working:
        videosThread = new Thread(() -> videosThread(context));
        // StartWorkingThread:
        videosThread.start();
    }

    // Voices:
    public void startVoiceOperation(Context context) {
        // Working:
        voicesThread = new Thread(() -> voicesThread(context));
        // StartWorkingThread:
        voicesThread.start();
    }

    // Status:
    public void startStatusOperation(Context context) {
        // Working:
        statusThread = new Thread(() -> statusThread(context));
        // StartWorkingThread:
        statusThread.start();
    }

    // Documents:
    public void startDocumentOperation(Context context) {
        // Working:
        documentsThread = new Thread(() -> documentsThread(context));
        // StartWorkingThread:
        documentsThread.start();
    }

    // Method(Thread):
    private void imagesThread(Context context) {
        // Initializing:
        List<ARImage> images = ARImagesAccess.getImagesWithDirs(context);
        // Developing:
        imagesMutableData.postValue(images);
    }

    private void videosThread(Context context) {
        // Initializing:
        List<File> videos = ARVideosAccess.getVideosWithDirs(context);
        // Developing:
        videosMutableData.postValue(videos);
    }

    private void voicesThread(Context context) {
        // Initializing:
        List<File> voices = ARVoicesAccess.getVoicesWithDirs(context);
        // Developing:
        voicesMutableData.postValue(voices);
    }

    private void statusThread(Context context) {
        // Initializing:
        List<File> statuses = ARStatusAccess.getStatusWithDirs(context);
        // Developing:
        statusMutableData.postValue(statuses);
    }

    private void documentsThread(Context context) {
        // Initializing:
        List<Document> documents = ARDocumentsAccess.getDocumentsWithDirs(context);
        // Developing:
        documentsMutableData.postValue(documents);
    }

    // Getters(&Setters):
    public MutableLiveData<List<File>> getVideosMutableData() {
        return videosMutableData;
    }

    public LiveData<List<File>> getVideosLiveData() {
        return videosLiveData;
    }

    public MutableLiveData<List<ARImage>> getImagesMutableData() {
        return imagesMutableData;
    }

    public LiveData<List<ARImage>> getImagesLiveData() {
        return imagesLiveData;
    }

    public MutableLiveData<List<File>> getVoicesMutableData() {
        return voicesMutableData;
    }

    public LiveData<List<File>> getVoicesLiveData() {
        return voicesLiveData;
    }

    public MutableLiveData<List<Document>> getDocumentsMutableData() {
        return documentsMutableData;
    }

    public LiveData<List<Document>> getDocumentsLiveData() {
        return documentsLiveData;
    }

    public MutableLiveData<List<File>> getStatusMutableData() {
        return statusMutableData;
    }

    public LiveData<List<File>> getStatusLiveData() {
        return statusLiveData;
    }

    public Thread getStatusThread() {
        return statusThread;
    }

    public Thread getDocumentsThread() {
        return documentsThread;
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
