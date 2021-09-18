package com.ar.team.company.app.whatsdelete.ui.activity.show.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.control.adapter.ShowChatAdapter;
import com.ar.team.company.app.whatsdelete.databinding.ActivityShowChatBinding;
import com.ar.team.company.app.whatsdelete.model.Chat;
import com.ar.team.company.app.whatsdelete.ar.utils.ARUtils;
import com.ar.team.company.app.whatsdelete.ui.interfaces.OnChatButtonClicked;

import es.dmoral.toasty.Toasty;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ShowChatActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    private ActivityShowChatBinding binding;
    // Adapter:
    private Chat chat;
    private Icon icon;
    private LinearLayoutManager layoutManager;
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
        layoutManager = new LinearLayoutManager(this);
        adapter = new ShowChatAdapter(this, chat);
        // Preparing:
        layoutManager.setStackFromEnd(true);
        // Developing(Main-UI):
        binding.senderNameTextView.setText(chat.getSender());
        binding.backButton.setOnClickListener(v -> finish());
        // Developing(Icon):
        if (icon != null) binding.senderImageView.setImageDrawable(icon.loadDrawable(this));
        else binding.senderImageView.setImageResource(R.drawable.ic_placeholder);
        // Developing(RecyclerView):
        binding.showChatRecyclerView.setAdapter(adapter);
        binding.showChatRecyclerView.setLayoutManager(layoutManager);
        binding.chatSendButton.setOnClickListener(this::sendMethod);
    }

    // SendMethod:
    private void sendMethod(View view) {
        // WarningMessage:
        String warningMes = "We need your friend send notification again if you need to send him messages";
        // WarningToast:
        if (icon == null) Toasty.warning(this, warningMes, Toasty.LENGTH_LONG).show();
        // Checking:
        if (!binding.chatEditText.getText().toString().isEmpty()) {
            // CatchingErrors:
            try {
                // SendingResponse:
                Chat refreshingChat = clicked.eventClick(chat.getSender(), binding.chatEditText.getText().toString());
                // Refreshing(Adapter):
                adapter = new ShowChatAdapter(this, refreshingChat);
                // Refreshing(RecyclerView):
                binding.showChatRecyclerView.setAdapter(adapter);
                binding.showChatRecyclerView.setLayoutManager(layoutManager);
            } catch (Exception e) {
                Toasty.error(this, "Unable to reach the person, please try again", Toast.LENGTH_LONG).show();
            }
            // EmptyChatInput:
            binding.chatEditText.setText("");
        }
    }
}