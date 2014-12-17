package de.unipotsdam.elis.workspacegrid.model;

import java.io.Serializable;
import java.net.URL;

import com.vaadin.shared.ui.colorpicker.Color;

public class WorkspaceSlide implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private URL webLink;
	private String name;

	private int numberOfNewActivities;
	public WorkspaceSlide(String name, String filterName,
			Color markupColor, URL webLink, int numberOfNewActivities) {
		super();
		this.filterName = filterName;		
		this.markupColor = markupColor;
		this.webLink = webLink;
		this.setName(name);
		this.setNumberOfNewActivities(numberOfNewActivities);
	}
	
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
	public Color getMarkupColor() {
		return markupColor;
	}
	public void setMarkupColor(Color markupColor) {
		this.markupColor = markupColor;
	}
	
	public URL getWebLink() {
		return webLink;
	}

	public void setWebLink(URL webLink) {
		this.webLink = webLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfNewActivities() {
		return numberOfNewActivities;
	}

	public void setNumberOfNewActivities(int numberOfNewActivities) {
		this.numberOfNewActivities = numberOfNewActivities;
	}

	private Color markupColor;
	private String filterName;	
}
