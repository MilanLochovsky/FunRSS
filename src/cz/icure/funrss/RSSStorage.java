package cz.icure.funrss;

import java.util.ArrayList;
import java.util.List;

import cz.icure.funrss.RSSReaderUtils.FeedsTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class RSSStorage {
	
	private SQLiteDatabase _db;
	private RSSReaderDbHelper _dbHelper;
	
	public RSSStorage(Context context) {
		_dbHelper = new RSSReaderDbHelper(context);
		_db = null;
		//this.insertFeedRow("Nadpis", "Nejaky super popis feedu", "Link", "Image", "Copyright");
	}
	
	private void closeDb() {
    	_db.close();
	}
    
    private boolean openReadableDb() throws SQLiteException {
    	try {
    		_db = _dbHelper.getReadableDatabase();
    		return true;
    		
    	} catch (SQLiteException ex) {
    		return false;
    	}
	}
    
    private boolean openWritalbeDb() throws SQLiteException {
    	try {
    		_db = _dbHelper.getWritableDatabase();
    		return true;
    		
    	} catch (SQLiteException ex) {
    		return false;
    	}
	}
    
    public List<FeedItem> getAllFeeds() {
    	List<FeedItem> fi = new ArrayList<FeedItem>();
    	
    	this.openReadableDb();
    	
    	Cursor q = _db.query(FeedsTable.TABLE_NAME, new String[] {FeedsTable._ID, FeedsTable.COLUMN_NAME_TITLE, FeedsTable.COLUMN_NAME_DESCRIPTION, FeedsTable.COLUMN_NAME_IMAGE, FeedsTable.COLUMN_NAME_COPYRIGHT, FeedsTable.COLUMN_NAME_LINK}, null, null, null, null, FeedsTable.COLUMN_NAME_TITLE + " ASC");
    	
    	if (q.moveToFirst()) {
    		do {
	    		fi.add(new FeedItem(q.getInt(q.getColumnIndex(FeedsTable._ID)), q.getString(q.getColumnIndex(FeedsTable.COLUMN_NAME_TITLE)), q.getString(q.getColumnIndex(FeedsTable.COLUMN_NAME_LINK)), q.getString(q.getColumnIndex(FeedsTable.COLUMN_NAME_DESCRIPTION)), q.getString(q.getColumnIndex(FeedsTable.COLUMN_NAME_IMAGE)), q.getString(q.getColumnIndex(FeedsTable.COLUMN_NAME_COPYRIGHT))));
    		} while(q.moveToNext());
		}
    	
    	this.closeDb();
    	
		return fi;
    }
    
    public long insertFeedRow(String title, String description, String link, String image, String copyright) {
    	long status = -1;
    	
    	if(this.openWritalbeDb()) {
	    	
	    	ContentValues contentValues = new ContentValues();
	    	
	    	contentValues.put(FeedsTable.COLUMN_NAME_TITLE, title);
	    	contentValues.put(FeedsTable.COLUMN_NAME_DESCRIPTION, description);
	    	contentValues.put(FeedsTable.COLUMN_NAME_LINK, link);
	    	contentValues.put(FeedsTable.COLUMN_NAME_IMAGE, image);
	    	contentValues.put(FeedsTable.COLUMN_NAME_COPYRIGHT, copyright);
	
	    	status = _db.insert(FeedsTable.TABLE_NAME, null, contentValues);
	    	
	    	this.closeDb();
    	}
    	
    	return status;
    }
    
    public long deleteFeedRow(long id) {
    	long status = -1;
    	
    	if(this.openWritalbeDb()) {
	    	status = _db.delete(FeedsTable.TABLE_NAME, FeedsTable._ID + "=" + id, null);
	    	this.closeDb();
    	}
    	
    	return status;
    }
	
}
