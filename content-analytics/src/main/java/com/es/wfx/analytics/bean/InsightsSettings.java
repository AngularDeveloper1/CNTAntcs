package com.es.wfx.analytics.bean;

import org.springframework.data.annotation.Id;

public class InsightsSettings {
	@Id
	private String _id;
	private String title;
	private String className;
	private boolean isSupressed;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the isSupressed
	 */
	public boolean isSupressed() {
		return isSupressed;
	}

	/**
	 * @param isSupressed
	 *            the isSupressed to set
	 */
	public void setSupressed(boolean isSupressed) {
		this.isSupressed = isSupressed;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the _id
	 */
	public String get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(String _id) {
		this._id = _id;
	}

}
