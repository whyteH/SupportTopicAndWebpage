package tech.laosiji.supporttopicandwebpage;

import android.text.Spannable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Whyte on 2017/7/21.
 */

public class URLIconSpannableString extends BaseSpannableString {

    public URLIconSpannableString(CharSequence source, String... topic) {
        super(source);
        initUrl(source);

        initTopic(source, topic);
    }

    private void initUrl(CharSequence source) {
        Pattern defaultPattern = Pattern.compile(WebUtils.DYNAMIC_URL_REGEX);
        Matcher matcher = defaultPattern.matcher(source);
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
                setSpan(new Clickable(new URLClickListener(ff)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private void initTopic(CharSequence source, String[] topic) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < topic.length; i++) {
            builder.append(WebUtils.regexSpecial(topic[i]));
            if (i < topic.length - 1) {
                builder.append("|");
            }
        }
        String regex = "#(?:" + builder.toString() + ")#";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(source);
        while (matcher.find()) {
            final String find = matcher.group();
            if (!TextUtils.isEmpty(find)) {
                int start = matcher.start();
                int end = matcher.end();
                setSpan(new Clickable(new TopicClickListener(find)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private static class URLClickListener implements View.OnClickListener {

        private String url;

        URLClickListener(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View arg0) {
            Toast.makeText(BaseApplication.getInstance(), "click 网址：" + url, Toast.LENGTH_SHORT).show();
            WebUtils.openUrl(url);
        }
    }

    private class TopicClickListener implements View.OnClickListener {

        private String text;

        TopicClickListener(String text) {
            this.text = text;
        }

        @Override
        public void onClick(View widget) {
            String trimText = text.substring(1, text.length() - 1);
            Toast.makeText(BaseApplication.getInstance(), "click 话题：" + trimText, Toast.LENGTH_SHORT).show();
        }
    }
}
