package cz.icure.funrss;

import java.util.ArrayList;
import java.util.List;

import cz.icure.funrss.RSSReaderUtils.FeedsTable;
import cz.icure.funrss.RSSReaderUtils.ItemsTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class RSSStorage {
	
	private SQLiteDatabase _db;
	private RSSReaderDbHelper _dbHelper;
	
	public RSSStorage(Context context) {
		_dbHelper = new RSSReaderDbHelper(context);
		_db = null;
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
    
    public long insertItemRow(Integer idFeed, String title, String description, String link, String guid, Long pubdate) {
    	long status = -1;
    	
    	if(this.openWritalbeDb()) {
	    	
	    	ContentValues contentValues = new ContentValues();
	    	
	    	contentValues.put(ItemsTable.COLUMN_NAME_IDFEED, idFeed);
	    	contentValues.put(ItemsTable.COLUMN_NAME_TITLE, title);
	    	contentValues.put(ItemsTable.COLUMN_NAME_DESCRIPTION, description);
	    	contentValues.put(ItemsTable.COLUMN_NAME_LINK, link);
	    	contentValues.put(ItemsTable.COLUMN_NAME_PUBDATE, pubdate.intValue());
	    	contentValues.put(ItemsTable.COLUMN_NAME_GUID, guid);
	
	    	status = _db.insert(ItemsTable.TABLE_NAME, null, contentValues);
	    	
	    	this.closeDb();
    	}
    	
    	return status;
    }
    
    public Boolean isGUIDExists(int feedId, String guid) {
    	this.openReadableDb();
    	
    	Cursor q = _db.query(ItemsTable.TABLE_NAME, new String[] {ItemsTable._ID}, ItemsTable.COLUMN_NAME_IDFEED + "=" + feedId + " AND " + ItemsTable.COLUMN_NAME_GUID + "=\"" + guid + "\"", null, null, null, null);
    	if(q.getCount()>0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public Boolean insertItems(int feedId, List<RSSItem> items) {
    	
    	for(int i = 0; i < items.size(); i++) {
    		RSSItem rsi = items.get(i);
    		if(!this.isGUIDExists(feedId, rsi.getUID())) {
    			this.insertItemRow(feedId, rsi.getTitle(), rsi.getDescription(), rsi.getUrl(), rsi.getUID(), rsi.getUnixTime());
    		}
    	}
    	
    	return false;
    }
    
    public Integer getCointFeedItems(int idFeed) {
    	this.openReadableDb();
    	Cursor mCount= _db.rawQuery("select count(*) from " + ItemsTable.TABLE_NAME + " where " + ItemsTable.COLUMN_NAME_IDFEED + "=" + idFeed, null);
    	mCount.moveToFirst();
    	int count= mCount.getInt(0);
    	this.closeDb();
    	return count;
    }
    
    public List<RSSItem> getFirstXItems(int num) {
    	List<RSSItem> itm = new ArrayList<RSSItem>();
    	
    	this.openReadableDb();
    	Cursor q = _db.query(ItemsTable.TABLE_NAME, new String[] {ItemsTable._ID, ItemsTable.COLUMN_NAME_TITLE, ItemsTable.COLUMN_NAME_LINK, ItemsTable.COLUMN_NAME_DESCRIPTION, ItemsTable.COLUMN_NAME_PUBDATE}, null, null, null, null, ItemsTable.COLUMN_NAME_PUBDATE + " DESC", String.valueOf(num));
    	
    	if (q.moveToFirst()) {
    		do {
	    		itm.add(new RSSItem(q.getInt(q.getColumnIndex(ItemsTable._ID)), q.getString(q.getColumnIndex(ItemsTable.COLUMN_NAME_TITLE)), q.getString(q.getColumnIndex(ItemsTable.COLUMN_NAME_LINK)), q.getString(q.getColumnIndex(ItemsTable.COLUMN_NAME_DESCRIPTION)), q.getInt(q.getColumnIndex(ItemsTable.COLUMN_NAME_PUBDATE)), null));
    		} while(q.moveToNext());
		}
    	
    	this.closeDb();
    	
		return itm;
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
