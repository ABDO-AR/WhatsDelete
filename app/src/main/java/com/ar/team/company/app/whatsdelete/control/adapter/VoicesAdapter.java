package com.ar.team.company.app.whatsdelete.control.adapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.team.company.app.whatsdelete.databinding.VoiceItemViewBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class VoicesAdapter extends RecyclerView.Adapter<VoicesAdapter.VoicesViewHolder> {

    // Fields:
    private final Context context;
    private final List<File> files;

    // Constructor:
    public VoicesAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
        notifyDataSetChanged();
    }

    // Adapter:
    @NonNull
    @NotNull
    @Override
    public VoicesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Initializing:
        LayoutInflater inflater = LayoutInflater.from(context);
        VoiceItemViewBinding binding = VoiceItemViewBinding.inflate(inflater, parent, false);
        // Returning:
        return new VoicesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VoicesAdapter.VoicesViewHolder holder, int position) {
        // Initializing:
        File file = files.get(position);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa", Locale.US);
        // Initializing(MediaMetadataRetriever):
        retriever.setDataSource(context, Uri.parse(file.getAbsolutePath()));
        String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        // Developing:
        holder.binding.durationTextView.setText(formatsMilliSeconds(Long.parseLong(duration)));
        holder.binding.dateTextView.setText(format.format(file.lastModified()));
    }

    public static String formatsMilliSeconds(long milliseconds) {
        // Initializing:
        String finalTimerString = "";
        String secondsString;
        // Convert total duration into time:
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there:
        if (hours > 0) finalTimerString = hours + ":";
        // Prepending 0 to seconds if it is one digit:
        if (seconds < 10) secondsString = "0" + seconds;
        else secondsString = "" + seconds;
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        // return timer string:
        return finalTimerString;
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    // Holder:
    @SuppressWarnings("unused")
    static class VoicesViewHolder extends RecyclerView.ViewHolder {

        // Fields:
        private final VoiceItemViewBinding binding;

        public VoicesViewHolder(@NonNull @NotNull VoiceItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Getters:
        public VoiceItemViewBinding getBinding() {
            return binding;
        }
    }

    // Getters:
    public Context getContext() {
        return context;
    }

    public List<File> getFiles() {
        return files;
    }
}
