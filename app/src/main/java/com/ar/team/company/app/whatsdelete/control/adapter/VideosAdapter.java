package com.ar.team.company.app.whatsdelete.control.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.team.company.app.whatsdelete.databinding.VideoItemViewBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    // Fields:
    private final Context context;
    private final List<File> files;

    // Constructor:
    public VideosAdapter(Context context, List<File> files) {
        this.context = context;
        this.files = files;
        notifyDataSetChanged();
    }

    // Adapter:
    @NonNull
    @NotNull
    @Override
    public VideosViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Initializing:
        LayoutInflater inflater = LayoutInflater.from(context);
        VideoItemViewBinding binding = VideoItemViewBinding.inflate(inflater, parent, false);
        // Retuning:
        return new VideosViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull VideosAdapter.VideosViewHolder holder, int position) {
        // Initializing:
        File file = files.get(position);
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Images.Thumbnails.MINI_KIND);
        // Developing:
        holder.binding.videoThumbnail.setImageBitmap(thumb);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    // Holder:
    static class VideosViewHolder extends RecyclerView.ViewHolder {

        // Fields:
        private final VideoItemViewBinding binding;

        // Constructor:
        public VideosViewHolder(@NonNull @NotNull VideoItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Getters:
        public VideoItemViewBinding getBinding() {
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
