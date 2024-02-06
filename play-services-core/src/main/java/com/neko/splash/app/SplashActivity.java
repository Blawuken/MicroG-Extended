package com.neko.splash.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.google.android.gms.R;
import org.microg.gms.ui.MainSettingsActivity;
import org.microg.tools.ui.BaseActivity;

public class SplashActivity extends BaseActivity {
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float f = i;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public void _Radius_ImageView(ImageView imageView, double d) {
        imageView.setImageBitmap(getRoundedCornerBitmap(((BitmapDrawable) imageView.getDrawable()).getBitmap(), (int) d));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.uwu_activity_splash);
        new Handler().postDelayed(new Runnable() { // from class: com.neko.splash.app.SplashActivity.100000000
            @Override // java.lang.Runnable
            public void run() {
                SplashActivity.this.startActivity(new Intent((Context) SplashActivity.this, (Class<?>) MainSettingsActivity.class));
                SplashActivity.this.finish();
                SplashActivity.this.overridePendingTransition(17432578, 17432579);
            }
        }, 2500);
        _Radius_ImageView((ImageView) findViewById(R.id.logo_view), 1000);
    }
}
