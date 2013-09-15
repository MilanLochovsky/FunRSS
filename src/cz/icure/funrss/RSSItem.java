package cz.icure.funrss;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	
	public RSSItem() {
		
	}
	
	public RSSItem(Integer id, String title, String url, String description, Date dateTime, String guid) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.description = description;
		this.dateTime = dateTime;
		this.UID = guid;
	}
	
	public RSSItem(Integer id, String title, String url, String description, Integer dateTime, String guid) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.description = description;
		this.dateTime = new java.util.Date((long)dateTime*1000);
		this.UID = guid;
	}
	
	public String getUID() {
		if(UID == null || UID.trim() == "") {
			return url;
		}
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
	
	private static String md5String(String text) { 
		String digest = null;
		
		try { 
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(text.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2*hash.length);
			for(byte b : hash){ 
				sb.append(String.format("%02x", b&0xff));
			} 
			
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) { 
				ex.printStackTrace();
		} catch (NoSuchAlgorithmException ex) { 
			ex.printStackTrace();	
		} 
		
		return digest; 
	}	
	
}
