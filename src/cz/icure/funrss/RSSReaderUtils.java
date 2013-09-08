package cz.icure.funrss;

import java.util.Date;

import android.provider.BaseColumns;

public class RSSReaderUtils {
	
	public class RSSItem {
		public String title;
		public String url;
		public String description;
		public Date dateTime;
	}
	
	public class FeedItem {
		public Integer id;
		public String title;
		public String url;
		public String description;
		public String image;
		public String copyright;
		
		public FeedItem(Integer id, String title, String url, String description, String image, String copyright) {
			this.id = id;
			this.title = title;
			this.url = url;
			this.description = description;
			this.image = image;
			this.copyright = copyright;
		}
	}
	
	public RSSReaderUtils() {
		
	}
	
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
