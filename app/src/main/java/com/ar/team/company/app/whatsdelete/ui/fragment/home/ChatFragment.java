package com.ar.team.company.app.whatsdelete.ui.fragment.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.control.adapter.ChatAdapter;
import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.whatsdelete.databinding.FragmentChatBinding;
import com.ar.team.company.app.whatsdelete.model.Chat;
import com.ar.team.company.app.whatsdelete.ui.activity.home.HomeViewModel;
import com.ar.team.company.app.whatsdelete.ar.utils.ARUtils;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class ChatFragment extends Fragment {

    // This for control the Fragment-Layout views:
    private FragmentChatBinding binding;
    private HomeViewModel model; // MainModel for our fragment.
    // Preferences(&Adapter):
    private ChatAdapter adapter;
    private List<Chat> chats;
    private ARPreferencesManager manager;
    // TAGS:
    private static final String TAG = "ChatFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout:
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Get the fragment layout root.
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initializing:
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        // Initializing(ImportantFields):
        manager = new ARPreferencesManager(requireContext());
        chats = new ArrayList<>();
        // Initializing(UI):
        initUI(null, null);
        // Developing:
        manager.getPreferences().registerOnSharedPreferenceChangeListener(this::initUI);
    }

    // Initializing UserInterface:
    private void initUI(SharedPreferences sharedPreferences, String key) {
        // Initializing:
        boolean state = !manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS).equals("Empty,");
        // Developing:
        if (state) {
            // AddingAll:
            chats.clear();
            chats.addAll(ARUtils.fromJsonToChats(manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS)));
            // Checking(ChatsAreNotEmpty):
            if (!chats.isEmpty()) {
                // Initializing:
                adapter = new ChatAdapter(requireContext(), chats);
                // Setting:
                binding.chatRecyclerView.setAdapter(adapter);
                binding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                // Refreshing:
                adapter.notifyDataSetChanged();
            }
        }
    }
}