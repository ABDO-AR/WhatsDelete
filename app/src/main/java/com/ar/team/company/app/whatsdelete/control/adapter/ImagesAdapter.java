package com.ar.team.company.app.whatsdelete.control.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.team.company.app.whatsdelete.databinding.ImageViewItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    // Fields:
    private final Context context;
    private final List<Bitmap> bitmaps;

    // Constructor:
    public ImagesAdapter(Context context, List<Bitmap> bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
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
        Bitmap bitmap = bitmaps.get(position);
        // Developing:
        holder.binding.imageViewItem.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
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

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }
}
