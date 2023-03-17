package com.example.pjk.mapd_721_final_project.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;

import androidx.annotation.Nullable;

import com.example.pjk.mapd_721_final_project.R;

import java.util.List;

public class ViewCheckin extends Dialog {

    Activity a;
    public ViewCheckin(Context context, Activity a) {
        super(a);

        // Set the layout for the popup window
        setContentView(R.layout.dialog_view_checkin);

    }


    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
    }

}

