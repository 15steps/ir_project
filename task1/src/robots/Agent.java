package robots;

import java.util.List;

public class Agent {
	
	private String agent;
	private List<String> disallow;
	private List<String> allow;
	private List<String> sitemap;
	private List<ComandAgent> listComandAgent;
	
	public Agent(String agent, List<String> disallow, List<String> allow, List<String> sitemap, List<ComandAgent> listComandAgent) {
		this.agent = agent;
		this.disallow = disallow;
		this.allow = allow;
		this.sitemap = sitemap;
		this.listComandAgent = listComandAgent;
	}
	
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public List<String> getDisallow() {
		return disallow;
	}
	public void setDisallow(List<String> disallow) {
		this.disallow = disallow;
	}
	public List<String> getAllow() {
		return allow;
	}
	public void setAllow(List<String> allow) {
		this.allow = allow;
	}
	public List<String> getSitemap() {
		return sitemap;
	}
	public void setSitemap(List<String> sitemap) {
		this.sitemap = sitemap;
	}
	public List<ComandAgent> getListComandAgent() {
		return listComandAgent;
	}
	public void setListComandAgent(List<ComandAgent> listComandAgent) {
		this.listComandAgent = listComandAgent;
	}

	@Override
	public String toString() {
		return "Agent [agent=" + agent + ", disallow=" + disallow + ", allow=" + allow + ", sitemap=" + sitemap
				+ ", listComandAgent=" + listComandAgent + "]";
	}
	
}
