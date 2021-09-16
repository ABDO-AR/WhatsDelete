package com.ar.team.company.app.whatsdelete.ui.activity.show.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;

import com.ar.team.company.app.whatsdelete.control.adapter.ShowChatAdapter;
import com.ar.team.company.app.whatsdelete.databinding.ActivityShowChatBinding;
import com.ar.team.company.app.whatsdelete.model.Chat;
import com.ar.team.company.app.whatsdelete.ar.utils.ARUtils;
import com.ar.team.company.app.whatsdelete.ui.interfaces.OnChatButtonClicked;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ShowChatActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    private ActivityShowChatBinding binding;
    private Chat chat;
    private Icon icon;
    private ShowChatAdapter adapter;
    // Interfaces:
    public static OnChatButtonClicked clicked;
    // TAGS:
    private static final String TAG = "ShowChatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowChatBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        chat = ARUtils.fromJsonToChat(getIntent().getExtras().getString("Chat"));
        icon = getIntent().getParcelableExtra("Icon");
        adapter = new ShowChatAdapter(this, chat);
        // Developing(Main-UI):
        binding.senderNameTextView.setText(chat.getSender());
        binding.backButton.setOnClickListener(v -> finish());
        // Developing(Icon):
        if (icon != null) binding.senderImageView.setImageDrawable(icon.loadDrawable(this));
        // Developing(RecyclerView):
        binding.showChatRecyclerView.setAdapter(adapter);
        binding.showChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.chatSendButton.setOnClickListener(v -> clicked.eventClick(chat.getSender(), binding.chatEditText.getText().toString()));
    }
}