package com.ar.team.company.app.socialdelete.ar.observer;

import android.content.Context;
import android.os.FileObserver;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ar.team.company.app.socialdelete.ar.access.ARAccess;
import com.ar.team.company.app.socialdelete.ui.activity.home.HomeViewModel;

@SuppressWarnings("unused")
public class ARFilesObserver extends FileObserver {

    // Fields:
    private final Context context;
    private final HomeViewModel model;
    private final String path;
    // TempData:
    private static int tempVoices = 0;
    // Fields(Debugging):
    private static final String TAG = "ARFilesObserver";

    // Constructor:
    public ARFilesObserver(Context context, String path, HomeViewModel model) {
        // Super:
        super(path);
        // Initializing:
        this.context = context;
        this.model = model;
        this.path = path;
    }

    // Method(Events):
    @Override
    public void onEvent(int event, @Nullable String s) {
        // Debugging:
        Log.d(TAG, "onEvent: " + s);
        // Checking:
        if (!path.equals(ARAccess.WHATSAPP_VOICES_PATH)) {
            // Checking:
            if (event == FileObserver.CREATE || event == FileObserver.ACCESS) {
                // Debugging:
                Log.d(TAG, "onEventCreate: " + s);
                Log.d(ARAccess.TAG, "A11-OP: ARFilesObserver :: " + s);
                // StartOperations:
                runOperations();
            }
        } else {
            // Checking:
            if (tempVoices == 0) {
                // Debugging:
                Log.d(TAG, "onEventCreate: " + s);
                // StartOperations:
                model.startVoiceOperation(context);
                // Increment:
                tempVoices++;
            }
        }
    }

    // Method(Run):
    private void runOperations() {
        // Checking(IF):
        if (path.equals(ARAccess.WHATSAPP_IMAGES_PATH)) model.startImageOperation(context);
        // Checking(ELSE-IF):
        else if (path.equals(ARAccess.WHATSAPP_VIDEOS_PATH)) model.startVideoOperation(context);
        else if (path.equals(ARAccess.WHATSAPP_VOICES_PATH)) model.startVoiceOperation(context);
        else if (path.equals(ARAccess.WHATSAPP_STATUS_PATH)) model.startStatusOperation(context);
        else if (path.equals(ARAccess.WHATSAPP_DOCUMENTS_PATH)) model.startDocumentOperation(context);
    }

    // Methods(Reset):
    public static void resetTempVoices() {
        // Resting:
        tempVoices = 0;
    }

    // Getters:
    public Context getContext() {
        return context;
    }

    public HomeViewModel getModel() {
        return model;
    }

    public String getPath() {
        return path;
    }
}
