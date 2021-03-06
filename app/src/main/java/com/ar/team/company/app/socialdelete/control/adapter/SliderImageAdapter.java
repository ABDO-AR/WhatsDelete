package com.ar.team.company.app.socialdelete.control.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.team.company.app.socialdelete.databinding.SlideImageViewItemBinding;
import com.ar.team.company.app.socialdelete.model.ARImage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SliderImageAdapter extends RecyclerView.Adapter<SliderImageAdapter.SliderImageViewHolder> {

    // Fields:
    private final Context context;
    private final List<ARImage> images;

    // Constructor:
    public SliderImageAdapter(Context context, List<ARImage> bitmaps) {
        this.context = context;
        this.images = bitmaps;
    }

    // Adapter:
    @NonNull
    @NotNull
    @Override
    public SliderImageViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Initializing:
        LayoutInflater inflater = LayoutInflater.from(context);
        SlideImageViewItemBinding binding = SlideImageViewItemBinding.inflate(inflater, parent, false);
        // Returning:
        return new SliderImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SliderImageAdapter.SliderImageViewHolder holder, int position) {
        // Initializing:
        Bitmap bitmap = images.get(position).getImageBitmap();
        // Developing:
        holder.binding.slidingImageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    // Holder:
    static class SliderImageViewHolder extends RecyclerView.ViewHolder {

        // Fields:
        private final SlideImageViewItemBinding binding;

        public SliderImageViewHolder(@NonNull @NotNull SlideImageViewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Getters:
        public SlideImageViewItemBinding getBinding() {
            return binding;
        }
    }

    // Getters(&Setters):
    public Context getContext() {
        return context;
    }

    public List<ARImage> getImages() {
        return images;
    }
}
