package robots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Robot {

	private List<Agent> agent;
	private List<String> lines;
	private StringBuilder sb;
	private String link;
	private String path;
	private String name;

	public Robot(String link, String path) {
		super();
		this.link = link;
		this.path = path;
		this.sb = new StringBuilder();
		this.lines = new ArrayList<>();
		this.agent = new ArrayList<>();
		this.name = link.replaceAll("[^a-zZ-Z0-9]", "-").replaceAll("\\-+", "-").replace("-txt", ".txt");
//		this.name = link.replace('.', '_').replace("_txt", ".txt").replace('/', '|');
	}

	public void download(){
		
		List<String> agents = new ArrayList<>();
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
//			String name = link.replace('.', '_').replace("_txt", ".txt").replace('/', '|');
//			this.save(sb.toString(), path, name);
			
			for(String agent : agents){
				this.genereteRobot(agent);
			}
		}
	}
	
	private void genereteRobot(String agent){

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
