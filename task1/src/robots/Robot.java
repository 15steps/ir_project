package robots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import files.Files;

public class Robot {

	private List<Agent> agent;
	private List<String> lines;
	private StringBuilder sb;
	private String link;
	private String name;
	private String path;

	public Robot(String link, String path) {
		this.link = link;
		this.sb = new StringBuilder();
		this.lines = new ArrayList<String>();
		this.agent = new ArrayList<Agent>();
		this.path = path;
		this.name = link.replaceAll("[^a-zZ-Z0-9]", "_").replaceAll("\\_+", "_").replace("_txt", "");
	}
	
	public void start(){
		List<String> agents = new ArrayList<String>();
		String userAgent = "User-agent:";
		
		try{
			URL url = new URL(link);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/txt");
			conn.connect();

			InputStreamReader inputStream = new InputStreamReader(conn.getInputStream());
			BufferedReader inStream = new BufferedReader(inputStream);
			String line;
			
			while ((line = inStream.readLine()) != null){
				this.sb.append(line);
				this.sb.append("\n");
				this.lines.add(line);
				
				if(line.startsWith(userAgent)){
					String agentAtual = line.substring(userAgent.length()).trim();
					agents.add(agentAtual);
				}
			}
			
			inStream.close();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			for(String agent : agents){
				this.genereteRobot(agent);
			}
			Files f = new Files();
			f.save(this.sb.toString(), this.path, this.name, ".txt");
		}
	}
	
	private void genereteRobot(String agent){

		List<String> allowList = new ArrayList<String>();
		List<String> disallowList = new ArrayList<String>();
		List<String> sitemapList = new ArrayList<String>();
		List<ComandAgent> listComandAgent = new ArrayList<ComandAgent>();
		
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
					String trim = line.substring(allow.length()).trim();
					allowList.add(trim);
					listComandAgent.add(new ComandAgent(TComandAgent.ALLOW, this.link.replaceAll("/robots.txt", ""), trim));
				}else if(line.startsWith(disallow)){
					String trim = line.substring(disallow.length()).trim();
					disallowList.add(trim);
					listComandAgent.add(new ComandAgent(TComandAgent.DISALLOW, this.link.replaceAll("/robots.txt", ""), trim));
				}else if(line.startsWith(sitemap)){
					String trim = line.substring(sitemap.length()).trim();
					sitemapList.add(trim);
					listComandAgent.add(new ComandAgent(TComandAgent.SITEMAP, this.link.replaceAll("/robots.txt", ""), trim));
				}
			}else{
				continue;
			}
		}
		
		Agent agents = new Agent(agent, disallowList, allowList, sitemapList, listComandAgent);
		this.agent.add(agents);
	}
	
	public Agent getAgentByName(String name){
		for(Agent a : this.agent){
			if(a.getAgent().equals(name)){
				return a;
			}
		}
		return null;
	}

	public List<Agent> getAgent() {
		return agent;
	}

	public void setAgent(List<Agent> agent) {
		this.agent = agent;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public StringBuilder getSb() {
		return sb;
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
