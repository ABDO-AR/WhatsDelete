package com.ar.team.company.app.socialdelete.ui.activity.show.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ar.team.company.app.socialdelete.R;
import com.ar.team.company.app.socialdelete.control.adapter.ShowChatAdapter;
import com.ar.team.company.app.socialdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.socialdelete.databinding.ActivityShowChatBinding;
import com.ar.team.company.app.socialdelete.model.Chat;
import com.ar.team.company.app.socialdelete.ar.utils.ARUtils;
import com.ar.team.company.app.socialdelete.ui.interfaces.OnChatButtonClicked;

import java.util.List;

import es.dmoral.toasty.Toasty;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ShowChatActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    // This For Control The XML-Main Views:
    private ActivityShowChatBinding binding;
    // Adapter:
    private Chat chat;
    private Icon icon;
    private LinearLayoutManager layoutManager;
    private ShowChatAdapter adapter;
    private ARPreferencesManager manager;
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
        manager = new ARPreferencesManager(this);
        chat = ARUtils.fromJsonToChat(getIntent().getExtras().getString("Chat"));
        icon = getIntent().getParcelableExtra("Icon");
        layoutManager = new LinearLayoutManager(this);
        /////////////////
        List<Chat> chats = ARUtils.fromJsonToChats(manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
        for (int index = 0; index < chats.size(); index++) {
            if (chats.get(index).getSender().equals(chat.getSender())) {
                for (int subIndex = 0; subIndex < chats.get(index).getMessages().size(); subIndex++) {
                    chats.get(index).getMessages().get(subIndex).setSeenMes(true);
                }
                chat = chats.get(index);
            }
        }
        manager.setStringPreferences(ARPreferencesManager.WHATSAPP_CHATS, ARUtils.fromChatsToJson(chats));
        /////////////////
        // Preparing:
        layoutManager.setStackFromEnd(true);
        // Developing(Main-UI):
        binding.senderNameTextView.setText(chat.getSender());
        binding.backButton.setOnClickListener(v -> finish());
        // Developing(Icon):
        if (icon != null) binding.senderImageView.setImageDrawable(icon.loadDrawable(this));
        else binding.senderImageView.setImageResource(R.drawable.ic_placeholder);
        // Developing(RecyclerView):
        if (chat != null) {
            adapter = new ShowChatAdapter(this, chat);
            binding.showChatRecyclerView.setAdapter(adapter);
            binding.showChatRecyclerView.setLayoutManager(layoutManager);
        }
        if (icon == null) binding.chatMaterialCardView.setVisibility(View.GONE);
        else binding.chatMaterialCardView.setVisibility(View.VISIBLE);
        binding.chatSendButton.setOnClickListener(this::sendMethod);
        // Developing(Manager):
        manager.getPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    // OnChatReSend:
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Initializing:
        List<Chat> chats = ARUtils.fromJsonToChats(manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
        Chat refreshingChat = null;
        // Getting(RefreshingChat):
        for (Chat chat : chats) {
            // Checking:
            if (chat.getSender().equals(this.chat.getSender())) refreshingChat = chat;
        }
        // Trying:
        try {
            // Checking:
            if (refreshingChat != null && refreshingChat.getMessages() != null) {
                // Refreshing(Adapter):
                adapter = new ShowChatAdapter(this, refreshingChat);
                // Refreshing(RecyclerView):
                binding.showChatRecyclerView.setAdapter(adapter);
                binding.showChatRecyclerView.setLayoutManager(layoutManager);
            }
        } catch (Exception e) {
            // Debug:
            Log.d(TAG, "onSharedPreferenceChanged: " + e.toString());
        }
    }

    // SendMethod:
    private void sendMethod(View view) {
        // WarningMessage:
        String warningMes = "We need your friend send notification again if you need to send him messages";
        // WarningToast:
        if (icon == null) binding.chatMaterialCardView.setVisibility(View.GONE);
        else binding.chatMaterialCardView.setVisibility(View.VISIBLE);
        //if (icon == null) Toasty.warning(this, warningMes, Toasty.LENGTH_LONG).show();
        // Checking:
        if (!binding.chatEditText.getText().toString().isEmpty()) {
            // CatchingErrors:
            try {
                // SendingResponse:
                Chat refreshingChat = clicked.eventClick(chat.getSender(), binding.chatEditText.getText().toString());
                if (refreshingChat != null) {
                    // Refreshing(Adapter):
                    adapter = new ShowChatAdapter(this, refreshingChat);
                    // Refreshing(RecyclerView):
                    binding.showChatRecyclerView.setAdapter(adapter);
                    binding.showChatRecyclerView.setLayoutManager(layoutManager);
                    ARPreferencesManager.sender = chat.getSender();
                }

            } catch (Exception e) {
                //Toasty.error(this, "Unable to reach the person, please try again", Toast.LENGTH_LONG).show();
            }
            // EmptyChatInput:
            binding.chatEditText.setText("");
        }
    }

    // OnDestroy:
    @Override
    protected void onDestroy() {
        manager.getPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }
}