package com.ar.team.company.app.whatsdelete.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.ar.voices.ARVoicesAccess;
import com.ar.team.company.app.whatsdelete.control.adapter.VoicesAdapter;
import com.ar.team.company.app.whatsdelete.databinding.FragmentVoiceBinding;
import com.ar.team.company.app.whatsdelete.ui.activity.home.HomeViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class VoiceFragment extends Fragment {

    // This for control the Fragment-Layout views:
    private FragmentVoiceBinding binding;
    private HomeViewModel model; // MainModel for our fragment.
    // Adapter:
    private ARVoicesAccess access;
    private List<File> files;
    private VoicesAdapter adapter;
    // TAGS:
    private static final String TAG = "VoiceFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout:
        binding = FragmentVoiceBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Get the fragment layout root.
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initializing:
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        // Initializing(Adapter):
        access = new ARVoicesAccess(requireContext());
        files = access.getWhatsappVoicesDirectory();
        adapter = new VoicesAdapter(requireContext(), files);
        // Developing:
        binding.voicesRecyclerView.setAdapter(adapter);
        binding.voicesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}