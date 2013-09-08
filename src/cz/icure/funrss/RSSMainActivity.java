package cz.icure.funrss;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class RSSMainActivity extends Activity {

	//private static final int RESULT_SETTINGS = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
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
            break;
            
        case R.id.menu_feeds:
        	Intent ifd = new Intent(this, RSSFeedsActivity.class);
        	startActivity(ifd);
            break;
            
        }
 
        return true;
    }
}
