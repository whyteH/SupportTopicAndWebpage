package tech.laosiji.supporttopicandwebpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String data = "http://115rc.com/home/topic/1231492.html# #30003话题#";
        String topic = "30003话题";

        TextView textView = (TextView) findViewById(R.id.text);
        URLIconSpannableString spannableString = new URLIconSpannableString(data,topic);

        textView.setText(new URLReplaceSpannableStringBuilder().append(spannableString));
    }
}
