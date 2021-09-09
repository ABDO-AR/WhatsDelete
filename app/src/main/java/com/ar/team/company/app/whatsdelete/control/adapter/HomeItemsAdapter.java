package com.ar.team.company.app.whatsdelete.control.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.databinding.HomeItemBinding;
import com.ar.team.company.app.whatsdelete.databinding.SingleChatItemBinding;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.ChatFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.DocumentFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.ImagesFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.StatusFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.VideosFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.VoiceFragment;
import com.ar.team.company.app.whatsdelete.ui.interfaces.HomeItemClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeItemsAdapter  extends RecyclerView.Adapter<HomeItemsAdapter.HomeViewHolder>{
    // init Interface
    private HomeItemClickListener itemClickListener;
    private Context context;
    // Fields:
    public final int[] iconsItems = {R.drawable.ic_chat,R.drawable.ic_status,R.drawable.ic_images , R.drawable.ic_videos,R.drawable.ic_voice, R.drawable.ic_documents};
    public final String[] namesItems ={"Chat","Status","Images","Videos","Voice","Document"};

    // Constructor:


    public HomeItemsAdapter(Context context)
    {
        this.context = context;

        this.itemClickListener = (HomeItemClickListener) context;
    }

    public HomeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Initializing:
        LayoutInflater inflater = LayoutInflater.from(context);
        HomeItemBinding binding = HomeItemBinding.inflate(inflater, parent, false);
        // Returning:
        return new HomeItemsAdapter.HomeViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull HomeItemsAdapter.HomeViewHolder holder, int position)
    {
    holder.binding.imgItemHome.setImageResource(iconsItems[position]);
    holder.binding.tvItemHome.setText(namesItems[position]);

    //open Fragment when click
    holder.binding.layoutItem.setOnClickListener(view -> itemClickListener.openFragment(position));


    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return iconsItems.length;
    }


    // ViewHolder:
static class HomeViewHolder extends RecyclerView.ViewHolder {

    // Fields:
    private final HomeItemBinding binding;

    // Constructor:
    public HomeViewHolder(@NonNull @NotNull HomeItemBinding binding) {
        super(binding.getRoot());
        // Initializing:
        this.binding = binding;
    }

    // Getters:
    public HomeItemBinding getBinding() {
        return binding;
    }
}


}
