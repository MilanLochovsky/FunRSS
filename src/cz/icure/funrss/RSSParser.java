package cz.icure.funrss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import android.sax.Element;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;

public class RSSParser {
	private URL feedUrl;
	private FeedItem channel;
	private List<RSSItem> parsedFeed;
	private String downloadedFeed;

	static final String PUB_DATE = "pubDate";
	static final String DESCRIPTION = "description";
	static final String CONTENT = "content";
	static final String LINK = "link";
	static final String TITLE = "title";
	static final String IMAGE = "image";
	static final String ITEM = "item";

	public RSSParser(String url) {

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
		Element chanDescription = chanElement.getChild("description");
		Element chanImage = chanElement.getChild("image");

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

		chanImage.setEndTextElementListener(new EndTextElementListener() {
			public void end(String body) {
				channel.setImage(body);
			}
		});

		try {
			Xml.parse(this.getFeedStream(), Xml.Encoding.UTF_8, root.getContentHandler());
			return channel;
		} catch (SAXException e) {
			// handle the exception
		} catch (IOException e) {
			// handle the exception
		}

		return channel;
	}

}