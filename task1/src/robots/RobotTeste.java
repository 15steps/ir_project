package robots;

import java.util.ArrayList;
import java.util.List;

import crawler.Link;

public class RobotTeste {

	public static void main(String[] args) {
		
		Robot robot = new Robot("https://www.mi.com", "files2/robots/mi.txt", false);
		
		
		List<String> links = new ArrayList<String>();
		links.add("https://www.mi.com/comment/myreview");
		links.add("https://www.mi.com/user/message/0");
		links.add("http://www.mi.com/en/mi-a1/");
		links.add("https://www.mi.com/en/mi-a1/");
		links.add("http://www.mi.com/en/mi-a1/?dssd");
		links.add("http://www.mi.com/?");
		links.add("http://www.mi.com/en/list/");
		
		
		for(String l : links){
			Link link1 = new Link(l, "");
			System.out.println(l);
			Agent agent = robot.getAgentByName("*");
			boolean retorno = true;
			for(ComandAgent comandAgent : agent.getListComandAgent()){
				if(comandAgent.getComandAgent().equals(TComandAgent.ALLOW)){
					if(link1.getLink().matches(comandAgent.getRejex())){
						retorno = true;
						System.out.println(retorno + " - " + comandAgent.getRejex() + " - " + link1.getLink());
					}
				}else if(comandAgent.getComandAgent().equals(TComandAgent.DISALLOW)){
					if(link1.getLink().matches(comandAgent.getRejex())){
						retorno = false;
						System.out.println(link1.getLink() + " - " + comandAgent.getRejex() + " - " + retorno);
					}
				}
			}
			
			System.out.println("-----------------------------------------");
		}
	}

}
