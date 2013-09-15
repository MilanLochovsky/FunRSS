package cz.icure.funrss;

import cz.icure.funrss.RSSReaderUtils.FeedsTable;
import cz.icure.funrss.RSSReaderUtils.ItemsTable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RSSReaderDbHelper extends SQLiteOpenHelper {

	    public static final int DATABASE_VERSION = 3;
	    public static final String DATABASE_NAME = "FunRSS.db";

	    private static final String SQL_CREATE_ENTRIES_FEEDS =
	        "CREATE TABLE " + FeedsTable.TABLE_NAME + " ("
	        + FeedsTable._ID + " INTEGER PRIMARY KEY, "
	        + FeedsTable.COLUMN_NAME_TITLE + " STRING, "
	        + FeedsTable.COLUMN_NAME_LINK + " STRING, "
	        + FeedsTable.COLUMN_NAME_DESCRIPTION + " STRING, "
	        + FeedsTable.COLUMN_NAME_COPYRIGHT + " STRING, "
	        + FeedsTable.COLUMN_NAME_IMAGE + " STRING);";
	    
	        private static final String SQL_CREATE_ENTRIES_ITEMS =
	        "CREATE TABLE " + ItemsTable.TABLE_NAME + " ("
	        + ItemsTable._ID + " INTEGER PRIMARY KEY, "
	        + ItemsTable.COLUMN_NAME_IDFEED + " INTEGER, "
	        + ItemsTable.COLUMN_NAME_TITLE + " STRING, "
	        + ItemsTable.COLUMN_NAME_LINK + " STRING, "
	        + ItemsTable.COLUMN_NAME_DESCRIPTION + " STRING, "
	        + ItemsTable.COLUMN_NAME_PUBDATE + " INTEGER, "
	        + ItemsTable.COLUMN_NAME_AUTOHOR + " STRING, "
	        + ItemsTable.COLUMN_NAME_GUID + " STRING);";

	    private static final String SQL_DELETE_ENTRIES =
	        "DROP TABLE IF EXISTS " + FeedsTable.TABLE_NAME + "; DROP TABLE IF EXISTS " + ItemsTable.TABLE_NAME;
	    
	    public RSSReaderDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    
	    public void onCreate(SQLiteDatabase db) {
	    	Log.i("", SQL_CREATE_ENTRIES_FEEDS);
	    	Log.i("", SQL_CREATE_ENTRIES_ITEMS);
	        db.execSQL(SQL_CREATE_ENTRIES_FEEDS);
	        db.execSQL(SQL_CREATE_ENTRIES_ITEMS);
	    }
	    
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	        db.execSQL(SQL_DELETE_ENTRIES);
	        onCreate(db);
	    }
	    
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
}
