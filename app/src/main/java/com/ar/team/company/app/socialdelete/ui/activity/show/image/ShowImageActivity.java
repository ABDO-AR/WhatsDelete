package com.ar.team.company.app.socialdelete.ui.activity.show.image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.ar.team.company.app.socialdelete.R;
import com.ar.team.company.app.socialdelete.control.adapter.ImagesAdapter;
import com.ar.team.company.app.socialdelete.control.adapter.SliderImageAdapter;
import com.ar.team.company.app.socialdelete.control.adapter.StatusAdapter;
import com.ar.team.company.app.socialdelete.databinding.ActivityShowImageBinding;
import com.ar.team.company.app.socialdelete.model.ARImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ShowImageActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    private ActivityShowImageBinding binding;
    // Adapter:
    private SliderImageAdapter adapter;
    private List<ARImage> images;
    private int index;
    // TAGS:
    private static final String TAG = "ShowImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowImageBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        index = getIntent().getExtras().getInt("Index");
        if (getIntent().getExtras().getString("TAG").equals("Images")) images = ImagesAdapter.staticImages;
        else images = StatusAdapter.staticImages;
        adapter = new SliderImageAdapter(this, images);
        // PreparingTextView:
        binding.imagesSizeTextView.setText((index + 1) + "/" + images.size());
        // PreparingViewPager:
        binding.imagesViewPager.setAdapter(adapter);
        binding.imagesViewPager.setCurrentItem(index);
        // PreparingViewPager(Callbacks):
        binding.imagesViewPager.registerOnPageChangeCallback(new ARViewPagerCallbacks());
        // Developing:
        binding.backButton.setOnClickListener(v -> finish());
    }

    // ViewPagerCallbacks:
    class ARViewPagerCallbacks extends ViewPager2.OnPageChangeCallback {
        // Methods(OnPageSelected):
        @Override
        public void onPageSelected(int position) {
            binding.imagesSizeTextView.setText((position + 1) + "/" + images.size());
            binding.shareButton.setOnClickListener(view1 -> shareNewPalette(images.get(position).getImageBitmap(), images.get(position).getImageFile()));
            super.onPageSelected(position);
        }
    }

    // SharingBitmaps:
    private void sharePalette(Bitmap bitmap) {
        // Initializing:
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "palette", "share palette");
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Preparing:
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        // Developing:
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void shareNewPalette(Bitmap bitmap, File file) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 55);
            return;
        }
        Toast.makeText(this, "Wait", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.app_name) + " Sharing ...");
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            Uri imageUri = Uri.parse(file.getAbsolutePath());
            ArrayList<Uri> uris = new ArrayList<>();
            uris.add(imageUri);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            this.startActivity(Intent.createChooser(intent, "Share..."));
        } else {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_SUBJECT, this.getString(R.string.app_name) + " Sharing ...");
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("image/*");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            Uri imageUri = Uri.parse(file.getAbsolutePath());
            ArrayList<Uri> uris = new ArrayList<>();
            uris.add(imageUri);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            this.startActivity(Intent.createChooser(intent, "Share..."));
        }
    }


}