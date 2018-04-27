package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import files.Files;
import naivebayes.Classe;
import naivebayes.NaiveBayes;
import robots.Robot;

public class Pagina extends Thread{

	private Robot robot;
	private Link link;
	private List<Link> listLink;
	private Set<String> listHeuristica;
	private String path;
	private NaiveBayes naiveBayes;
	private int seconds;
	private int qtdPagina;
	
	public Pagina(String link, String[] heuristica, String path, int seconds, int qtdPagina) {
		
		this.link = new Link(link, "");
		this.listLink = new ArrayList<Link>();
		this.listLink.add(this.link);
		
		this.path = path;
		this.listHeuristica = new HashSet<String>();
		for(String s : heuristica){
			this.listHeuristica.add(s);
		}
		
		String linkRobot =  this.link.getBase() + "/robots.txt";
		this.robot = new Robot(linkRobot, path);
		this.robot.download();
		this.naiveBayes = new NaiveBayes();
		this.qtdPagina = qtdPagina;
		this.seconds = seconds;
	}
	
	public void run(){
		int size = 20;
		String format = ".html";
		Files f = new Files();
		int count = 0;
		
		for(int i=0; i<this.listLink.size(); i++){
			
			Link link = this.listLink.get(i);
//			System.out.println(link.toString());
			
			if(!this.classifyLinkHeuristica(link) && i>0){
				continue;
			}
			
			if(count > this.qtdPagina){
				continue;
			}
			
			count++;
			
			Document document = this.downloadAux(link.getLink());
			String name = document.getElementsByTag("title").text().replaceAll("\\s+", "_");
			name = name.length() > size ? name.substring(0, size) : name;
			f.save(document.toString(), this.path, name, format);
			
			try {
				System.out.println("Esperando...");
				TimeUnit.SECONDS.sleep(this.seconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Document downloadAux(String link){

		Document document = null;
		try {
			document = Jsoup.connect(link).header("Content-Type", "text/html").get();
			Elements elements = document.select("a[href]");
			for (Element l : elements) {
				String lin = l.attr("abs:href");
				if(!lin.matches("\\s*")){
					Link link1 = new Link(lin, l.text());
					this.listLink.add(link1);
				}
				
	        }
		} catch (IOException e) {
			System.err.println("Erro ao conectar no endereco: "+link+"\n");
			e.printStackTrace();
		}
		
		return document;
	}
	
	public boolean classifyLinkHeuristica(Link link){
		
		for(String s : link.getTokens()){
			if(this.listHeuristica.contains(s)){
				return true;
			}
		}
		return false;
	}
	
	public boolean classifyLinkNaive(Link link){
		
		Classe classe = this.naiveBayes.classify(link.getTokens());
		if(classe.equals(Classe.POSITIVO)){
			return true;
		}
		return false;
	}
	
}
