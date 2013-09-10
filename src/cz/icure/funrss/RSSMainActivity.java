package cz.icure.funrss;

import java.util.ArrayList;
import java.util.List;

import cz.icure.funrss.RSSFeedSettingsActivity.DownloadFeedHeader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.sax.EndTextElementListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class RSSMainActivity extends Activity {

	//private static final int RESULT_SETTINGS = 1;
	
	ProgressDialog dialog;
	ListView _feedsList;
	List<RSSItem> _data;
	LazyItemsAdapter _feedAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        _feedsList=(ListView)findViewById(R.id.itemslist);
        _data = new ArrayList<RSSItem>();
        _feedAdapter = new LazyItemsAdapter(this, _data);
        _feedsList.setAdapter(_feedAdapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
 
        case R.id.menu_settings:
            Intent i = new Intent(this, SettingsActivity.class);
            //startActivityForResult(i, RESULT_SETTINGS);
            startActivity(i);
            break;
            
        case R.id.menu_reload:
        	// Reload feedu
        	dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
       	 	new ReloadFeeds().execute();
            break;
            
        case R.id.menu_feeds:
        	Intent ifd = new Intent(this, RSSFeedsActivity.class);
        	startActivity(ifd);
            break;
            
        }
 
        return true;
    }
    
    class ReloadFeeds extends AsyncTask<Void, List<RSSItem>, List<RSSItem>> {
    	
    	@Override
    	protected List<RSSItem> doInBackground(Void... params) {
    		List<RSSItem> outList = new ArrayList<RSSItem>();
    		try {
	    		RSSStorage rss = new  RSSStorage(getApplicationContext());
	    		List<FeedItem> lfi = rss.getAllFeeds();
	    		
	    		RSSParser rsp = new RSSParser();
	    		
	    		for(int i = 0; i < lfi.size(); i++) {
	    			try {
		    			rsp.setUrl(lfi.get(i).getUrl());
		    			outList.addAll(rsp.ParseFeedItems());
	    			}
	    			catch(Exception e) {
	    				e.printStackTrace();
	    			}
	    		}
    		}
    		catch(Exception e) {
    			e.printStackTrace();
    		}
        	return outList;
    	}
    	
    	@Override
        protected void onPostExecute(List<RSSItem> result) {
    		_data = result;
    		_feedAdapter.notifyDataSetChanged(_data);
        	 dialog.dismiss();
        }
     }
}
