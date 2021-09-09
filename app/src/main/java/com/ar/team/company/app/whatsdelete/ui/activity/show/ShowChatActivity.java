package com.ar.team.company.app.whatsdelete.ui.activity.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.ar.team.company.app.whatsdelete.control.adapter.ShowChatAdapter;
import com.ar.team.company.app.whatsdelete.databinding.ActivityShowChatBinding;
import com.ar.team.company.app.whatsdelete.model.Chat;
import com.ar.team.company.app.whatsdelete.utils.ARUtils;

@SuppressWarnings("FieldCanBeLocal")
public class ShowChatActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    private ActivityShowChatBinding binding;
    // TAGS:
    private static final String TAG = "ShowChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowChatBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        Chat chat = ARUtils.fromJsonToChat(getIntent().getExtras().getString("Chat"));
        ShowChatAdapter adapter = new ShowChatAdapter(this, chat);
        // Developing:
        binding.senderNameTextView.setText(chat.getSender());
        binding.backButton.setOnClickListener(v -> finish());
        binding.showChatRecyclerView.setAdapter(adapter);
        binding.showChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}