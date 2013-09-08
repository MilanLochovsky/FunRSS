package cz.icure.funrss;

import java.util.List;

import cz.icure.funrss.RSSReaderUtils.FeedItem;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class RSSFeedsActivity extends Activity {
	
	ListView _feedsList;
	List<FeedItem> _data;
	LazyFeedsAdapter _feedAdapter;
	
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		_feedsList=(ListView)findViewById(R.id.feedslist);
        RSSStorage rss = new RSSStorage(this);
        _data = rss.getAllFeeds();
        _feedAdapter = new LazyFeedsAdapter(this, _data);
        _feedsList.setAdapter(_feedAdapter);
        registerForContextMenu(_feedsList);
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feeds, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
 
        case R.id.menu_feeds_settings:
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            break;
            
        case R.id.menu_feeds_add:
        	Intent intent = new Intent(this, RSSFeedSettingsActivity.class);
            startActivity(intent);
            break;
            
        }
 
        return true;
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      if (v.getId()==R.id.feedslist) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(_data.get(info.position).title);
        getMenuInflater().inflate(R.menu.menu_context_feeds, menu);
      }
    }

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		
		switch (item.getItemId()) {
		 
        case R.id.menu_context_edit:
    		Intent intent = new Intent(this, RSSFeedSettingsActivity.class);
    		intent.putExtra("ID", info.id);
            startActivity(intent);
            break;
            
        case R.id.menu_context_delete:
        	AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle(getResources().getString(R.string.dialog_delete_title));
            dialog.setMessage(getResources().getString(R.string.dialog_delete_message));
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.dialog_delete_yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int buttonId) {
                	RSSStorage rss = new RSSStorage(getApplicationContext());
                	if(rss.deleteFeedRow(info.id) > 0) { 
	                	_data.remove(info.position);
	                	_feedAdapter.notifyDataSetChanged(_data);
                	}
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.dialog_delete_no) , new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int buttonId) {
                }
            });
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            dialog.show();
            break;
        }
		
		//Toast.makeText(this, "Item: " + item.getItemId(), Toast.LENGTH_SHORT).show();
		
		return true;
	}
    
   
}
