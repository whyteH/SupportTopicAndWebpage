package tech.laosiji.supporttopicandwebpage;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by Whyte on 2017/7/21.
 */

public class BaseSpannableString extends SpannableString {


    public BaseSpannableString(CharSequence source) {
        super(source);
    }

    public static class Clickable extends ClickableSpan implements View.OnClickListener {

        static final String COLOR_STRING = "#5C82BD";
        private final View.OnClickListener mListener;
        private String color = COLOR_STRING;


        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        public Clickable(View.OnClickListener l, String color) {
            mListener = l;
            this.color = color;
        }


        @Override
        public void onClick(View widget) {
            mListener.onClick(widget);
        }


        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(Color.parseColor(color));
        }
    }
}
