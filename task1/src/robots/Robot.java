package robots;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Robot {

	private File file;
	private List<Agent> agent;
	
	public Robot() {
		super();
		this.agent = new Vector<Agent>();
	}

	public Robot(File file, List<Agent> agent, List<String> sitemap) {
		super();
		this.file = file;
		this.agent = agent;
	}
	
	public void genereteRobot(List<String> lines, String agent){

		List<String> allowList = new ArrayList<String>();
		List<String> disallowList = new ArrayList<String>();
		List<String> sitemapList = new ArrayList<String>();
		
		String allow = "Allow:";
		String disallow = "Disallow:";
		String userAgent = "User-agent:";
		String sitemap = "Sitemap:";
		
		boolean ast = false;
		
		for(String line : lines){
			if(line.startsWith("#")){
				continue;
			}else if(line.startsWith(userAgent)){
				String agentAtual = line.substring(userAgent.length()).trim();
				ast = agentAtual.equals(agent);
			}else if(ast){
				if(line.startsWith(allow)){
					allowList.add(line.substring(allow.length()).trim());
				}else if(line.startsWith(disallow)){
					disallowList.add(line.substring(disallow.length()).trim());
				}else if(line.startsWith(sitemap)){
					sitemapList.add(line.substring(sitemap.length()).trim());
				}
			}else{
				continue;
			}
		}
		
		Agent agents = new Agent(agent, disallowList, allowList, sitemapList);
		this.agent.add(agents);
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public List<Agent> getAgent() {
		return agent;
	}
	public void setAgent(List<Agent> agent) {
		this.agent = agent;
	}
	
}
