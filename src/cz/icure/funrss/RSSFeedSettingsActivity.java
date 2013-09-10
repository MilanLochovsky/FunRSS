
package cz.icure.funrss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class RSSFeedSettingsActivity extends PreferenceActivity {
	EditTextPreference mLabel;
	EditTextPreference mUrl;
	FeedItem header;
	ProgressDialog dialog;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.feed_preference);
        mLabel = (EditTextPreference) findPreference("label");
        mLabel.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        mUrl = (EditTextPreference) findPreference("url");
        mUrl.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                    	 dialog = ProgressDialog.show(p.getContext(), "", "Loading. Please wait...", true);
                    	 new DownloadFeedHeader().execute((String) newValue);
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        EditTextPreference mUsername = (EditTextPreference) findPreference("loginUsername");
        mUsername.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        EditTextPreference mPassword = (EditTextPreference) findPreference("loginPassword");
        mPassword.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                        // Set the summary based on the new label.
                        p.setSummary((String) newValue);
                        return true;
                    }
                });
        
        CheckBoxPreference mVibratePref = (CheckBoxPreference) findPreference("login");
        mVibratePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    public boolean onPreferenceChange(Preference p,
                            Object newValue) {
                    	if(((Boolean)newValue) == true) {
                    		((EditTextPreference) findPreference("loginUsername")).setEnabled(true);
                    		((EditTextPreference) findPreference("loginPassword")).setEnabled(true);
                    	}
                    	else {
                    		((EditTextPreference) findPreference("loginUsername")).setEnabled(false);
                    		((EditTextPreference) findPreference("loginPassword")).setEnabled(false);
                    	}
                    	
                        return true;
                    }
                });
        
        getListView().setItemsCanFocus(true);

        // Grab the content view so we can modify it.
        FrameLayout content = (FrameLayout) getWindow().getDecorView()
                .findViewById(android.R.id.content);

        // Get the main ListView and remove it from the content view.
        ListView lv = getListView();
        content.removeView(lv);

        // Create the new LinearLayout that will become the content view and
        // make it vertical.
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        // Have the ListView expand to fill the screen minus the save/cancel
        // buttons.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.weight = 1;
        ll.addView(lv, lp);

        // Inflate the buttons onto the LinearLayout.
        View v = LayoutInflater.from(this).inflate(
                R.layout.save_cancel_alarm, ll);

        // Attach actions to each button.
        Button b = (Button) v.findViewById(R.id.alarm_save);
        b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	RSSStorage rss = new RSSStorage(getApplicationContext());
                	Log.i("", "L: " + mLabel.getSummary());
                	Log.i("", "H: " + header.getDescription());
                	rss.insertFeedRow(mLabel.getSummary().toString(), header.getDescription(), mUrl.getText(), header.getImage(), header.getCopyright());
                    finish();
                }
        });
        b = (Button) v.findViewById(R.id.alarm_cancel);
        b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
        });

        // Replace the old content view with our new one.
        setContentView(ll);
    }
	
	class DownloadFeedHeader extends AsyncTask<String, FeedItem, FeedItem> {
		
		@Override
		protected FeedItem doInBackground(String... params) {
			RSSParser rsp = new RSSParser(params[0]);
	    	return rsp.ParseFeedHeader();
		}
		
		@Override
	    protected void onPostExecute(FeedItem result) {
	    	 if(result != null && result.getTitle() != null) {
         		((EditTextPreference) findPreference("label")).setSummary(result.getTitle());;
     		 }
	    	 Log.i("", "Ok dokonceno");
	    	 header = result;
	    	 dialog.dismiss();
        }
	 }
}