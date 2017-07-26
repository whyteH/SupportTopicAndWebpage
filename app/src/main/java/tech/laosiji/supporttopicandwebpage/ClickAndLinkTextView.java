package tech.laosiji.supporttopicandwebpage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 设置超链接和点击效果的文本
 * Created by Whyte on 2017/7/21.
 */
public class ClickAndLinkTextView extends android.support.v7.widget.AppCompatTextView {


    public ClickAndLinkTextView(Context context) {
        this(context, null);
    }

    public ClickAndLinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMovementMethod(LocalLinkMovementMethod.getInstance());
    }

    @Override
    public boolean hasFocusable() {
        return false;
    }


    private static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;

        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null) {
                sInstance = new LocalLinkMovementMethod();
            }
            return sInstance;
        }

        @Override
        public boolean onTouchEvent(@NonNull TextView widget, @NonNull Spannable buffer,
                                    @NonNull MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_CANCEL:
                    //移除按下的背景颜色
                    removeSpan(widget, buffer);
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_DOWN:
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = buffer.getSpans(off, off,
                            ClickableSpan.class);

                    if (link.length != 0) {
                        ClickableSpan cs = link[0];
                        int csStart = buffer.getSpanStart(cs);
                        int csEnd = buffer.getSpanEnd(cs);
                        if (off >= csStart && off < csEnd && off < layout.getLineEnd(line)) {
                            if (action == MotionEvent.ACTION_UP) {
                                //移除按下的背景颜色
                                removeSpan(widget, buffer);
                                link[0].onClick(widget);
                            } else {
                                //增加按下的背景颜色
                                BackgroundColorSpan span = new BackgroundColorSpan(
                                        widget.getResources().getColor(R.color.color_selected));
                                buffer.setSpan(span, csStart, csEnd,
                                        Spanned.SPAN_MARK_POINT);
                                widget.setText(buffer);
                                Selection.setSelection(buffer,
                                        buffer.getSpanStart(link[0]),
                                        buffer.getSpanEnd(link[0]));
                            }
                        }

                        return true;
                    }
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }

    private static void removeSpan(@NonNull TextView widget, @NonNull Spannable buffer) {
        BackgroundColorSpan[] spans = buffer.getSpans(0, buffer.length(),
                BackgroundColorSpan.class);
        for (BackgroundColorSpan span : spans) {
            buffer.removeSpan(span);
            widget.setText(buffer);
        }
    }
}
