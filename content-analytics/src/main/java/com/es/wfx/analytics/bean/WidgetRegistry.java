/**
 * 
 */
package com.es.wfx.analytics.bean;

import org.springframework.data.annotation.Id;

/**
 * @author mansoora.tm
 *
 */
public class WidgetRegistry {

	@Id
	private String id;
	private String title;
	private String description;
	private String tags;
	private String url;
	private boolean insightStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the insightStatus
	 */
	public boolean isInsightStatus() {
		return insightStatus;
	}

	/**
	 * @param insightStatus the insightStatus to set
	 */
	public void setInsightStatus(boolean insightStatus) {
		this.insightStatus = insightStatus;
	}

}
