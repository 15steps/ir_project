package crawler;

import org.jsoup.nodes.Element;

public class Link {

	private String link;
	private String description;
	private boolean visited;
	private Element element;
	private int num;
	private int nivel;

	public Link(String link, String description, boolean visited, Element element, int num, int nivel) {
		super();
		this.link = link;
		this.description = description;
		this.visited = visited;
		this.element = element;
		this.num = num;
		this.nivel = nivel;
	}

	public Link(Element element, int nivel) {
		super();
		this.setElement(element);
		this.nivel = nivel;
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
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return "Link [link=" + link + ", description=" + description + ", visited=" + visited + ", num=" + num
				+ ", nivel=" + nivel + "]";
	}
	
}
