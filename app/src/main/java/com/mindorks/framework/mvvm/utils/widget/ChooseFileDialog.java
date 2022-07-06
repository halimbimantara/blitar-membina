package com.mindorks.framework.mvvm.utils.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindorks.framework.mvvm.R;


public final class ChooseFileDialog {

    Dialog dialog;

    public ChooseFileDialog(Context context) {
        preparingDialog(context);
    }

    private void preparingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(wlp);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dialog.setContentView(R.layout.dialog_choose_file);

            TextView camera = dialog.findViewById(R.id.camera);
            TextView gallery = dialog.findViewById(R.id.gallery);
            TextView uploadFile = dialog.findViewById(R.id.uploadFile);
            TextView delete = dialog.findViewById(R.id.delete);
            delete.setVisibility(View.GONE);

            gallery.setOnClickListener(view -> listener.onItemClick(view));
            camera.setOnClickListener(view -> listener.onItemClick(view));
            uploadFile.setOnClickListener(view -> listener.onItemClick(view));
            delete.setOnClickListener(view -> listener.onItemClick(view));
        }
    }

    DialogItemListener listener;

    public void showDialog() {
        dialog.show();
    }

    public void hideDialog() {
        dialog.cancel();
    }

    public void setListener(DialogItemListener listener) {
        this.listener = listener;
    }

    public interface DialogItemListener {
        void onItemClick(View view);
    }

}