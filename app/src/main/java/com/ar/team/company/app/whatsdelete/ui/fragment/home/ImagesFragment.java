package com.ar.team.company.app.whatsdelete.ui.fragment.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ar.team.company.app.whatsdelete.ar.images.ARImagesAccess;
import com.ar.team.company.app.whatsdelete.control.adapter.ImagesAdapter;
import com.ar.team.company.app.whatsdelete.databinding.FragmentImagesBinding;
import com.ar.team.company.app.whatsdelete.ui.activity.home.HomeViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ImagesFragment extends Fragment {

    // This for control the Fragment-Layout views:
    private FragmentImagesBinding binding;
    private HomeViewModel model; // MainModel for our fragment.
    // Adapter:
    private ARImagesAccess access;
    private List<Bitmap> bitmaps;
    private ImagesAdapter adapter;
    // Thread:
    private Thread workingThread;
    // TAGS:
    private static final String TAG = "ImagesFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout:
        binding = FragmentImagesBinding.inflate(inflater, container, false);
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
        // Initializing:
        access = new ARImagesAccess(requireContext());
        bitmaps = access.getWhatsappImagesBitmaps();
        adapter = new ImagesAdapter(requireContext(), bitmaps);
        // Developing:
        requireActivity().runOnUiThread(() -> {
            binding.recyclerImageView.setAdapter(adapter);
            binding.recyclerImageView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
            // Loading:
            isLoading(false);
        });
    }

    @SuppressWarnings("SameParameterValue")
    private void isLoading(boolean loading) {
        // Developing:
        binding.progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.recyclerImageView.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        workingThread.interrupt();
        super.onDestroy();
    }
}