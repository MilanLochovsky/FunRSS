package cz.icure.funrss;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.text.method.DateTimeKeyListener;

public class RSSItem implements Serializable {
	private Integer id;
	private String title;
	private String url;
	private String description;
	private Date dateTime;
	private String UID;
	
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateTime() {
		return dateTime;
	}
	
	public Long getUnixTime() {
		return (dateTime.getTime()/1000);
	}

	public void setDateTime(String dateTime)  {
		final SimpleDateFormat formatter = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
		try {
			this.dateTime = formatter.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setDateTime(String dateTime, SimpleDateFormat formatter)  {
		try {
			this.dateTime = formatter.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
