package org.prasanna.messenger.model;


//@XmlRootElement is not added because is a sub-resource of MessageResouce
public class Link {
	private String link;
	private String rel;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
