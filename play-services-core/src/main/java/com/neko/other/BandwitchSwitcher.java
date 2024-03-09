package com.neko.other;

import android.os.Bundle;
import android.content.Intent;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.net.Uri;
import org.microg.tools.ui.BaseActivity;

public class BandwitchSwitcher extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            Intent BandInfo = new Intent("android.intent.action.MAIN");
            BandInfo.setClassName("com.android.settings", "com.android.settings.BandMode");
            startActivity(BandInfo);
            finish();
        }
        catch (Exception e)
        {
            Builder ErrorMessage = new Builder(this);
            ErrorMessage.setMessage("Sorry, this feature is not available for your device.");
            ErrorMessage.setPositiveButton("I UNDERSTAND", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    dialog.dismiss();
                    Intent Uninstall = new Intent(Intent.ACTION_DELETE);
                    Uninstall.setData(Uri.parse("package:com.mgoogle.android.gms"));
                    startActivity(Uninstall);
                    finish();
                }
            });
            ErrorMessage.show();
        }
    }
}