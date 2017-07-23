package tech.laosiji.supporttopicandwebpage;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Whyte on 2017/7/21.
 */

public class URLReplaceSpannableStringBuilder extends SpannableStringBuilder {


    public URLReplaceSpannableStringBuilder() {
    }

    @Override
    public SpannableStringBuilder append(CharSequence text) {
        SpannableStringBuilder builder = super.append(text);

        final String REPLACE_STRING = "\t" + BaseApplication.getInstance().getString(R.string.url_replace_string);

        Pattern p = Pattern.compile(WebUtils.DYNAMIC_URL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(text);
        int offset = 0;
        while (matcher.find()) {
            final String find = matcher.group();
            if (!WebUtils.isEmail(find)) {
                int start = matcher.start();
                int end = matcher.end();
                int length = find.length();
                int index = -1;
                for (int i = 0; i < length; i++) {
                    if (find.charAt(i) >= 127) {
                        index = i;
                        break;
                    }
                }
                final String ff;
                if (index > 0) {
                    ff = find.substring(0, index);
                    end = start + ff.length();
                } else {
                    ff = find;
                }

                int replaceStart = start - offset;
                int replaceEnd = end - offset;
                builder.replace(replaceStart, replaceEnd, REPLACE_STRING);
                Drawable drawable = BaseApplication.getInstance().getResources().getDrawable(R.mipmap.ic_dynamic_link_bound);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                setSpan(new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE)
                        , replaceStart, replaceStart + ("\t".length()), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                offset += ff.length() - REPLACE_STRING.length();
            }
        }
        return builder;
    }
}
