package com.ar.team.company.app.socialdelete.control.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.team.company.app.socialdelete.databinding.ImageViewItemBinding;
import com.ar.team.company.app.socialdelete.model.ARImage;
import com.ar.team.company.app.socialdelete.ui.activity.show.image.ShowImageActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    // Fields:
    private final Context context;
    private final List<ARImage> images;
    public static List<ARImage> staticImages = new ArrayList<>();

    // Constructor:
    public ImagesAdapter(Context context, List<ARImage> images) {
        this.context = context;
        this.images = images;
        staticImages = images;
        notifyDataSetChanged();
    }

    // Adapter:
    @NonNull
    @NotNull
    @Override
    public ImagesAdapter.ImagesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ImageViewItemBinding binding = ImageViewItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ImagesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImagesAdapter.ImagesViewHolder holder, int position) {
        // Initializing:
        Bitmap bitmap = images.get(position).getImageBitmap();
        // Developing:
        holder.binding.imageViewItem.setImageBitmap(bitmap);
        holder.binding.imageViewItem.setOnClickListener(view -> slidingImage(position));

    }

    // RunningSlidingImage:
    private void slidingImage(int pos) {
        // Initializing:
        Intent intent = new Intent(context, ShowImageActivity.class);
        // PuttingExtras:
        intent.putExtra("Index", pos);
        intent.putExtra("TAG", "Images");
        // Developing:
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    // ViewHolder:
    static class ImagesViewHolder extends RecyclerView.ViewHolder {
        // Fields:
        private final ImageViewItemBinding binding;

        // Constructor:
        public ImagesViewHolder(@NonNull @NotNull ImageViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Getters:
        public ImageViewItemBinding getBinding() {
            return binding;
        }
    }

    // Getters:
    public Context getContext() {
        return context;
    }

    public List<ARImage> getBitmaps() {
        return images;
    }
}
