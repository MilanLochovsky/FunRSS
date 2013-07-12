package cz.icure.funrss;

import java.util.Date;

public class RSSItem {
	private String title;
	private String url;
	private String description;
	private Date dateTime;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public RSSItem copy() {
		RSSItem copy = createForCopy();
        copyTo(copy);
        return copy;
	}

	public RSSItem createForCopy() {
		return new RSSItem();
	}

	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public void copyTo(RSSItem dest) {
		dest.setTitle(title);
		dest.setDescription(description);
		dest.setUrl(url);
		dest.setDateTime(dateTime);
	}
	
}
