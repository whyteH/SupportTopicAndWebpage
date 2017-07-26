package tech.laosiji.supporttopicandwebpage;

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

    public static class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;
        private int color = BaseApplication.getInstance().getResources().getColor(R.color.color_link);


        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        public Clickable(View.OnClickListener l, int color) {
            mListener = l;
            this.color = color;
        }


        @Override
        public void onClick(View widget) {
            if (mListener != null)
                mListener.onClick(widget);
        }


        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
            ds.setColor(color);
        }
    }
}
