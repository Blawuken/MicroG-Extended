package com.neko.oneclick;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

public class CardView extends androidx.cardview.widget.CardView implements View.OnClickListener {
    private String targetClass;
    private String targetPackage;

    public CardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.targetPackage = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "targetPackage");
        this.targetClass = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "targetClass");
        setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(this.targetPackage, this.targetClass));
        getContext().startActivity(intent);
    }
}
