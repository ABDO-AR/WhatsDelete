package com.ar.team.company.app.socialdelete.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ar.team.company.app.socialdelete.control.adapter.VideosAdapter;
import com.ar.team.company.app.socialdelete.databinding.FragmentVideosBinding;
import com.ar.team.company.app.socialdelete.ui.activity.home.HomeViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class VideosFragment extends Fragment {

    // This for control the Fragment-Layout views:
    private FragmentVideosBinding binding;
    private HomeViewModel model; // MainModel for our fragment.
    // Adapters:
    private VideosAdapter adapter;
    // TAGS:
    private static final String TAG = "VideosFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment layout:
        binding = FragmentVideosBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Get the fragment layout root.
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initializing:
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        // StartOperations:
        model.startVideoOperation();
        // Observing:
        model.getVideosLiveData().observe(getViewLifecycleOwner(), this::onVideosChanged);
    }

    // OnVideosChange:
    private void onVideosChanged(List<File> videos) {
        // Initializing:
        adapter = new VideosAdapter(requireContext(), videos);
        // Preparing(RecyclerView):
        binding.videosRecyclerView.setAdapter(adapter);
        binding.videosRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        // Loading:
        isLoading(false);
    }

    @SuppressWarnings("SameParameterValue")
    private void isLoading(boolean loading) {
        // Developing:
        binding.progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.videosRecyclerView.setVisibility(loading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        model.getVideosThread().interrupt();
        super.onDestroy();
    }
}