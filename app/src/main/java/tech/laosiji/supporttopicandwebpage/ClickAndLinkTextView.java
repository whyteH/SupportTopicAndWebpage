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
 * 设置超链接 和点击效果的文本
 * Created by Whyte on 2017/7/21.
 */
public class ClickAndLinkTextView extends android.support.v7.widget.AppCompatTextView {


    private boolean linkHit;

    private boolean isCustomTouch;

    public ClickAndLinkTextView(Context context) {
        this(context, null);
    }

    public ClickAndLinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isCustomTouch = true;
        setMovementMethod(ClickAndLinkTextView.LocalLinkMovementMethod.getInstance());
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (isCustomTouch) {
            linkHit = false;
            super.onTouchEvent(event);
            return linkHit;
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean hasFocusable() {
        return false;
    }


    public static class LocalLinkMovementMethod extends LinkMovementMethod {
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
            if (action == MotionEvent.ACTION_CANCEL) {
                //移除按下的背景颜色
                BackgroundColorSpan[] spans = buffer.getSpans(0, buffer.length(),
                        BackgroundColorSpan.class);
                for (BackgroundColorSpan span : spans) {
                    buffer.removeSpan(span);
                    widget.setText(buffer);
                }
                return true;
            }
            if (action == MotionEvent.ACTION_MOVE) {

                return true;
            }
            if (action == MotionEvent.ACTION_UP
                    || action == MotionEvent.ACTION_DOWN) {
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

                    // offset在Span的区间内才认为是点击到了Span上。默认的处理是，如果点击在行尾，即使点击在了
                    // Span外也认为点击了Span，判断点击offset小于line end的原因是点击内容区外面系统也认为点
                    // 击的位置是最后一个文字，影响就是如果链接在行尾，点最后一个字母没反应，不过影响不大
                    if (off >= csStart && off < csEnd && off < layout.getLineEnd(line)) {
                        if (action == MotionEvent.ACTION_UP) {
                            //移除按下的背景颜色
                            BackgroundColorSpan[] spans = buffer.getSpans(0, buffer.length(),
                                    BackgroundColorSpan.class);
                            for (BackgroundColorSpan span : spans) {
                                buffer.removeSpan(span);
                                widget.setText(buffer);
                            }
                            link[0].onClick(widget);
                        } else {
                            //增加按下的背景颜色
                            BackgroundColorSpan span = new BackgroundColorSpan(
                                    widget.getResources().getColor(R.color.color_list_selected));
                            buffer.setSpan(span, csStart, csEnd,
                                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                            widget.setText(buffer);
                            Selection.setSelection(buffer,
                                    buffer.getSpanStart(link[0]),
                                    buffer.getSpanEnd(link[0]));
                        }


                    }

                    if (widget instanceof ClickAndLinkTextView) {
                        ((ClickAndLinkTextView) widget).linkHit = true;
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                    Touch.onTouchEvent(widget, buffer, event);
                    return false;
                }
            }
            return Touch.onTouchEvent(widget, buffer, event);
        }
    }

}