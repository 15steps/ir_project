package robots;

public enum TComandAgent {

	ALLOW("Allow:"),
	DISALLOW("Disallow:"),
	USER_AGENT("User-agent:"),
	SITEMAP("Sitemap:");

	private String description;

	private TComandAgent(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
