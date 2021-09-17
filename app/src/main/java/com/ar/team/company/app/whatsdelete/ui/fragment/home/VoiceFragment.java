package com.ar.team.company.app.whatsdelete.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ar.team.company.app.whatsdelete.ar.voices.ARVoicesAccess;
import com.ar.team.company.app.whatsdelete.control.adapter.VoicesAdapter;
import com.ar.team.company.app.whatsdelete.databinding.FragmentVoiceBinding;
import com.ar.team.company.app.whatsdelete.ui.activity.home.HomeViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class VoiceFragment extends Fragment {

    // This for control the Fragment-Layout views:
    private FragmentVoiceBinding binding;
    private HomeViewModel model; // MainModel for our fragment.
    // Adapter:
    private ARVoicesAccess access;
    private List<File> files = new ArrayList<>();
    private VoicesAdapter adapter;
    // Threading:
    private Thread workingThread;
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
        // Working:
        workingThread = new Thread(this::workingMethod);
        // StartWorkingThread:
        workingThread.start();
    }

    // WorkingThread:
    private void workingMethod() {
        // Initializing(Adapter):
        access = new ARVoicesAccess(requireContext());

        if (access.getWhatsappVoicesDirectory()!=null)
        files = access.getWhatsappVoicesDirectory();
        adapter = new VoicesAdapter(requireContext(), files);
        // Developing:
        requireActivity().runOnUiThread(() -> {
            // Developing:
            binding.voicesRecyclerView.setAdapter(adapter);
            binding.voicesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            // Loading:
            isLoading(false);
        });
    }

    @SuppressWarnings("SameParameterValue")
    private void isLoading(boolean loading) {
        // Developing:
        binding.progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.voicesRecyclerView.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        workingThread.interrupt();
        super.onDestroy();
    }
}