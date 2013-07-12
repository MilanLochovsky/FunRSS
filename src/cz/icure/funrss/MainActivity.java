package cz.icure.funrss;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.renderscript.Font;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.URLSpan;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class MainActivity extends Activity {

	TextView mTextView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.mTextView = (TextView) findViewById(R.id.textView1);
        
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DownloadFeed().execute("http://www.root.cz/rss/clanky/");
            }
        });
        		
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private class DownloadFeed extends AsyncTask {

    	@Override
        protected void onPostExecute(Object result) {
        	SpannableStringBuilder outputText = new SpannableStringBuilder();
        	
        	for(RSSItem s : (List<RSSItem>)result) {
        		SpannableString text = new SpannableString(s.getTitle() + "\n" + s.getDateTime().toString() + "\n" + s.getDescription() + "\n\n");
        		text.setSpan(new URLSpan(s.getUrl()), 0, s.getTitle().length(), 0);
        		text.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.getTitle().length(), 0);
        		text.setSpan(new ForegroundColorSpan(Color.GRAY),s.getTitle().length() + 1,s.getTitle().length() + 1 + s.getDateTime().toString().length(), 0);
        		text.setSpan(new RelativeSizeSpan(0.75f),s.getTitle().length() + 1,s.getTitle().length() + 1 + s.getDateTime().toString().length(), 0);
        		outputText.append(text);
        	}

        	mTextView.setMovementMethod(LinkMovementMethod.getInstance());
            mTextView.setText(outputText, BufferType.SPANNABLE);
        }

    
		@Override
		protected List<RSSItem> doInBackground(Object... params) {
			RSSParser rpsr = new RSSParser((String)params[0]);
			return rpsr.ParseFeed();
		}
    }
    
}
