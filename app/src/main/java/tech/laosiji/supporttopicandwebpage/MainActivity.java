package tech.laosiji.supporttopicandwebpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String data = "#话题1##话题2# 这是一个老司机的网站 http://laosiji.tech";
        String[] topics = new String[]{"话题1", "话题2"};

        TextView textView = (TextView) findViewById(R.id.text);
        URLIconSpannableString spannableString = new URLIconSpannableString(data, topics);

        textView.setText(new URLReplaceSpannableStringBuilder().append(spannableString));
    }
}
