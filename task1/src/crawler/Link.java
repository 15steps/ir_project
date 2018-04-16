package crawler;

import org.jsoup.nodes.Element;

public class Link {

	private String link;
	private String description;
	private boolean visited;
	private Element element;
	
	public Link(String link, String description, boolean visited, Element element) {
		super();
		this.link = link;
		this.description = description;
		this.visited = visited;
		this.element = element;
	}

	public Link() {
		
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
		this.link = element.attr("abs:href");
		this.description = element.text();
	}
	
	@Override
	public String toString() {
		return "Link [link=" + link + ", description=" + description + ", visited=" + visited + "]";
	}
	
}
