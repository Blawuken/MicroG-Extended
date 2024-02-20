package android.annotation.support.v3.services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class privacy {
    private final Context mContext;
    private final AlertDialog mDialog;

    private privacy(Context context) {
        this.mContext = context;
        if (this.mContext.getSharedPreferences("MyPrefs", 0).getBoolean("password_correct", false)) {
            this.mDialog = null;
            return;
        }
        final EditText editText = new EditText(context);
        editText.setHint("extended");
        this.mDialog = new AlertDialog.Builder(context).setTitle("Enter Password").setView(editText).setPositiveButton("Submit", (DialogInterface.OnClickListener) null).setCancelable(false).create();
        this.mDialog.setOnShowListener(new DialogInterface.OnShowListener() { // from class: android.annotation.support.v3.services.privacy.1
            @Override // android.content.DialogInterface.OnShowListener
            public void onShow(DialogInterface dialogInterface) {
                Button button = privacy.this.mDialog.getButton(-1);
                final EditText editText2 = editText;
                button.setOnClickListener(new View.OnClickListener() { // from class: android.annotation.support.v3.services.privacy.1.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (!editText2.getText().toString().equals("extended")) {
                            editText2.setText("");
                            editText2.setError("Incorrect password. Try again.");
                            return;
                        }
                        SharedPreferences.Editor edit = privacy.this.mContext.getSharedPreferences("MyPrefs", 0).edit();
                        edit.putBoolean("password_correct", true);
                        edit.apply();
                        privacy.this.mDialog.dismiss();
                    }
                });
            }
        });
    }

    public static void show(Context context) {
        privacy privacyVar = new privacy(context);
        if (privacyVar.mDialog != null) {
            privacyVar.mDialog.show();
        }
    }
}