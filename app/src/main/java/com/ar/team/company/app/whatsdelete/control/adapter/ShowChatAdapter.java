package com.ar.team.company.app.whatsdelete.control.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.team.company.app.whatsdelete.databinding.ShowChatItemViewBinding;
import com.ar.team.company.app.whatsdelete.databinding.SingleChatItemBinding;
import com.ar.team.company.app.whatsdelete.model.Chat;
import com.ar.team.company.app.whatsdelete.ui.activity.show.ShowChatActivity;
import com.ar.team.company.app.whatsdelete.utils.ARUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShowChatAdapter extends RecyclerView.Adapter<ShowChatAdapter.ShowChatViewHolder> {

    // Fields:
    private final Context context;
    private final Chat chats;

    // Constructor:
    public ShowChatAdapter(Context context, Chat chats) {
        // Initializing:
        this.context = context;
        this.chats = chats;
        // Notify:
        notifyDataSetChanged();
    }

    // Adapter:
    @NonNull
    @NotNull
    @Override
    public ShowChatAdapter.ShowChatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Initializing:
        LayoutInflater inflater = LayoutInflater.from(context);
        ShowChatItemViewBinding binding = ShowChatItemViewBinding.inflate(inflater, parent, false);
        // Returning:
        return new ShowChatAdapter.ShowChatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ShowChatViewHolder holder, int position) {
        // Initializing:
        Chat.Messages messages = chats.getMessages().get(position);
        // Developing:
        holder.binding.chatMes.setText(messages.getMessage());
        holder.binding.dateTextView.setText(chats.getMessageDate());
    }

    @Override
    public int getItemCount() {
        return chats.getMessages().size();
    }

    // ViewHolder:
    static class ShowChatViewHolder extends RecyclerView.ViewHolder {

        // Fields:
        private final ShowChatItemViewBinding binding;

        // Constructor:
        public ShowChatViewHolder(@NonNull @NotNull ShowChatItemViewBinding binding) {
            super(binding.getRoot());
            // Initializing:
            this.binding = binding;
        }

        // Getters:
        public ShowChatItemViewBinding getBinding() {
            return binding;
        }
    }

    // Getters:
    public Context getContext() {
        return context;
    }

    public Chat getChats() {
        return chats;
    }

}
