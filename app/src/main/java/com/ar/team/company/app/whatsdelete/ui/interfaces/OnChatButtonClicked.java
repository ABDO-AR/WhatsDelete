package com.ar.team.company.app.whatsdelete.ui.interfaces;

import com.ar.team.company.app.whatsdelete.control.adapter.ShowChatAdapter;
import com.ar.team.company.app.whatsdelete.model.Chat;

public interface OnChatButtonClicked {
    Chat eventClick(String sender, String mes);
}
