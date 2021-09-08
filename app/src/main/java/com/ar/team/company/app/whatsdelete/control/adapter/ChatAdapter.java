package com.ar.team.company.app.whatsdelete.control.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.ar.team.company.app.whatsdelete.databinding.SingleChatItemBinding;
import com.ar.team.company.app.whatsdelete.model.Chat;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    // Fields:
    private final Context context;
    private final List<Chat> chats;

    // Constructor:
    public ChatAdapter(Context context, List<Chat> chats) {
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
    public ChatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Initializing:
        LayoutInflater inflater = LayoutInflater.from(context);
        SingleChatItemBinding binding = SingleChatItemBinding.inflate(inflater, parent, false);
        // Returning:
        return new ChatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatAdapter.ChatViewHolder holder, int position) {
        // Initializing:
        Chat chat = chats.get(position);
        // Developing:
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    // ViewHolder:
    static class ChatViewHolder extends RecyclerView.ViewHolder {

        // Fields:
        private final SingleChatItemBinding binding;

        // Constructor:
        public ChatViewHolder(@NonNull @NotNull SingleChatItemBinding binding) {
            super(binding.getRoot());
            // Initializing:
            this.binding = binding;
        }

        // Getters:
        public SingleChatItemBinding getBinding() {
            return binding;
        }
    }

    // Getters:
    public Context getContext() {
        return context;
    }

    public List<Chat> getChats() {
        return chats;
    }
}
