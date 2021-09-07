package com.ar.team.company.app.whatsdelete.control.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.databinding.SingleAppItemBinding;
import com.ar.team.company.app.whatsdelete.model.Application;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationsViewHolder> {

    // Fields:
    private final Context context;
    private final List<Application> applications;
    // TAGS:
    private static final String TAG = "ApplicationsAdapter";

    // Constructor:
    public ApplicationsAdapter(Context context, List<Application> applications) {
        this.context = context;
        this.applications = applications;
        notifyDataSetChanged();
    }

    // Adapter:
    @NonNull
    @NotNull
    @Override
    public ApplicationsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Initializing:
        LayoutInflater inflater = LayoutInflater.from(context);
        SingleAppItemBinding binding = SingleAppItemBinding.inflate(inflater, parent, false);
        // Debugging:
        Log.d(TAG, "onCreateViewHolder: View Holder Created Successfully");
        // Returning:
        return new ApplicationsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ApplicationsAdapter.ApplicationsViewHolder holder, int position) {
        // Initializing:
        Application application = applications.get(position);
        Drawable appIcon = application.getIcon();
        // Developing:
        holder.binding.appIconImageView.setImageDrawable(appIcon);
        holder.binding.appNameTextView.setText(application.getName());
        // Debugging:
        Log.d(TAG, "onBindViewHolder: View Holder Bind Successfully");
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    // Holders:
    static class ApplicationsViewHolder extends RecyclerView.ViewHolder {

        // Fields:
        private final SingleAppItemBinding binding;

        // Constructor:
        public ApplicationsViewHolder(@NonNull @NotNull SingleAppItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // Getters:
        public SingleAppItemBinding getBinding() {
            return binding;
        }
    }

    // Getters:
    public Context getContext() {
        return context;
    }

    public List<Application> getApplications() {
        return applications;
    }
}
