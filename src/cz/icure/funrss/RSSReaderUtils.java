package cz.icure.funrss;

import android.provider.BaseColumns;

public abstract class RSSReaderUtils {
	
	public static abstract class ItemsTable implements BaseColumns {
		public static final String TABLE_NAME = "items";
		public static final String COLUMN_NAME_IDFEED = "idFeed";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_LINK = "link";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_AUTOHOR = "author";
		public static final String COLUMN_NAME_GUID = "guid";
		public static final String COLUMN_NAME_PUBDATE = "pubdate";
    }
	
	public static abstract class FeedsTable implements BaseColumns {
		public static final String TABLE_NAME = "feeds";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_LINK = "link";
		public static final String COLUMN_NAME_DESCRIPTION = "description";
		public static final String COLUMN_NAME_IMAGE = "image";
		public static final String COLUMN_NAME_COPYRIGHT = "copyright";
    }
	
}
