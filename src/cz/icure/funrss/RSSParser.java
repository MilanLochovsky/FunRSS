package cz.icure.funrss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

public class RSSParser extends DefaultHandler {
	private URL feedUrl;
	private FeedItem channel;
	List<RSSItem> items;
    RSSItem item;

    public RSSParser() {

	}
    
    public void setUrl(String url) {
    	try {
			this.feedUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
    }
    
	public RSSParser(String url) {
		this.setUrl(url);
	}

	private InputStream getFeedStream() {

		try {
			URLConnection connection = this.feedUrl.openConnection();
			connection.connect();
			connection.setReadTimeout(1000);

			return connection.getInputStream();
		} catch (SocketTimeoutException e) {
			Log.e("TIMEOUT", e.toString());
			e.printStackTrace();

			return null;
		} catch (Exception e) {
			Log.e("", e.toString());
			e.printStackTrace();

			return null;
		}
	}
	
	public FeedItem ParseFeedHeader() {
		RootElement root = new RootElement("rss");
		Element chanElement = root.getChild("channel");
		Element chanTitle = chanElement.getChild("title");
		Element chanCopyright = chanElement.getChild("copyright");
		Element chanDescription = chanElement.getChild("description");
		Element chanImage = chanElement.getChild("image");
		Element chanImageUrl = chanImage.getChild("url");
		
		chanElement.setStartElementListener(new StartElementListener() {
			public void start(Attributes attributes) {
				channel = new FeedItem();
			}
		});

		chanTitle.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				channel.setTitle(body);
			}
		});

		chanDescription.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				channel.setDescription(body);
			}
		});

		chanImageUrl.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				channel.setImage(body);
			}
		});
		
		chanCopyright.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				channel.setCopyright(body);
			}
		});

		try {
			Xml.parse(this.getFeedStream(), Xml.Encoding.UTF_8, root.getContentHandler());
			return channel;
		} catch (SAXException e) {
			e.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		}

		return null;
	}

	public List<RSSItem> ParseFeedItems() {
		RootElement root = new RootElement("rss");
		Element chanElement = root.getChild("channel");
		Element chanItem = chanElement.getChild("item");
        Element itemTitle = chanItem.getChild("title");
        Element itemDescription = chanItem.getChild("description");
        Element itemLink = chanItem.getChild("link");
        Element itemDate = chanItem.getChild("pubDate");
        
        final SimpleDateFormat formatter = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

        items = new ArrayList<RSSItem>();
        
        chanItem.setStartElementListener(new StartElementListener() {
			public void start(Attributes attributes) {
				item = new RSSItem();
			}
		});
        
        chanItem.setEndElementListener(new EndElementListener() {
			@Override
			public void end() {
				items.add(item);
			}
		});

		itemTitle.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				item.setTitle(body);
			}
		});

		itemDescription.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				item.setDescription(body);
			}
		});

		itemLink.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				item.setUrl(body);
			}
		});
		
		itemDate.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				item.setDateTime(body, formatter);
			}
		});

		try {
			Xml.parse(this.getFeedStream(), Xml.Encoding.UTF_8, root.getContentHandler());
			return items;
		} catch (SAXException e) {
			e.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		}

		return null;
	}

}