package com.neko.marquee.text;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import java.util.Calendar;

public class Grettings extends TextView {

    @Override // android.view.View
    public boolean isFocused() {
        return true;
    }

    public Grettings(Context context) {
        super(context);
        greeting();
    }

    public Grettings(Context context, AttributeSet attrs) {
        super(context, attrs);
        greeting();
    }

    public Grettings(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        greeting();
    }
    
    private void greeting() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        StringBuilder sb = new StringBuilder();
        if (timeOfDay >= 0 && timeOfDay < 5) {
            sb.append("Good Morning...");
        } else if (timeOfDay >= 5 && timeOfDay < 9) {
            sb.append("Good Morning...");
        } else if (timeOfDay >= 9 && timeOfDay < 15) {
            sb.append("Good Afternoon...");
        } else if (timeOfDay >= 15 && timeOfDay < 18) {
            sb.append("Good Afternoon...");
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            sb.append("Good Night...");
        }
        setText(sb);
    }
}
