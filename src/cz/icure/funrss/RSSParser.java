package cz.icure.funrss;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Log;
import android.util.Xml;

public class RSSParser {
	private URL feedUrl;
	private List<RSSItem> parsedFeed;
	private String downloadedFeed;
	
	static final  String PUB_DATE = "pubDate";
    static final  String DESCRIPTION = "description";
    static final  String CONTENT = "content";
    static final  String LINK = "link";
    static final  String TITLE = "title";
    static final  String ITEM = "item";
	
	RSSParser(String url) {
		
		try {
			this.feedUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		this.parsedFeed = new ArrayList<RSSItem>();
	}
	
	private InputStream getFeedStream() {
		
		try {
	        URLConnection connection = this.feedUrl.openConnection();
	        connection.connect();
	        connection.setReadTimeout(1000);
	        
	        return connection.getInputStream();
		}
		catch(SocketTimeoutException e) {
			Log.e("TIMEOUT", e.toString());
			e.printStackTrace();
			
			return null;
		}
		catch(Exception e) {
			Log.e("", e.toString());
			e.printStackTrace();
			
			return null;
		}
	}
	
	public List<RSSItem> ParseFeed() {
		final List<RSSItem> messages = new ArrayList<RSSItem>();
		final RSSItem currentMessage = new RSSItem();
		
		RootElement root = new RootElement("rss");
		Element channel = root.getChild("channel");
		Element item = channel.getChild(ITEM);
		final SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		
		item.setEndElementListener(new EndElementListener() {
			public void end() {
				messages.add(currentMessage.copy());
			}
		});
		
		item.getChild(TITLE).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setTitle(body);
					}
				});
		
		item.getChild(LINK).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setUrl(body);
					}
				});
		
		item.getChild(DESCRIPTION).setEndTextElementListener(
				new EndTextElementListener() {
					public void end(String body) {
						currentMessage.setDescription(body);
					}
				});
		
		item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                try {
					currentMessage.setDateTime(formatter.parse(body));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
		try {
			Xml.parse(this.getFeedStream(), Xml.Encoding.UTF_8,
					root.getContentHandler());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return messages;
	}
	
}
