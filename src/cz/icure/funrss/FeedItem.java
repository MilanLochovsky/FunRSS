package cz.icure.funrss;

import java.io.Serializable;

public class FeedItem implements Serializable {

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	private Integer id;
	private String title;
	private String url;
	private String description;
	private String image;
	private String copyright;
	
	public FeedItem(Integer id, String title, String url, String description, String image, String copyright) {
		this.id = id;
		this.title = title;
		this.url = url;
		this.description = description;
		this.image = image;
		this.copyright = copyright;
	}
	
	public FeedItem() {
		this.id = null;
		this.title = null;
		this.url = null;
		this.description = null;
		this.image = null;
		this.copyright = null;
	}
}
