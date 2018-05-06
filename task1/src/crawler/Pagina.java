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
import naivebayes.NaiveBayesResult;
import robots.Robot;
import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Pagina extends Thread{

	private Robot robot;
	private Link link;
	private List<Link> listLink;
	private Set<String> setLink;
	private Set<String> listHeuristica;
	private String path;
	private NaiveBayes naiveBayes;
	private int seconds;
	private int qtdPagina;
	private String regex;
	private String site;
	
	public Pagina(String link, String[] heuristica, String path, int seconds, int qtdPagina) {
		
		this.setLink = new HashSet<String>();
		this.link = new Link(link, "");
		this.regex = "htt(p|ps)://"+ this.link.getBaseAux() + ".*";
		this.listLink = new ArrayList<Link>();
		this.listLink.add(this.link);
		
		boolean www = this.link.getBaseAux().startsWith("www.");
		int beginIndex = www ? 4 : 0;
		int endIndex = this.link.getBaseAux().length() - 4;
		this.site = this.link.getBaseAux().substring(beginIndex, endIndex).replace(".", "_");
		
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
		String format = ".html";
		Files f = new Files();
		int count = 0;
		WordProcessing pro = new WordProcessing();
		
		Stopword stopword = Stopword.NONE;
		
		for(int i=0; i<this.listLink.size(); i++){
			
			Link link = this.listLink.get(i);
			
			if(count > this.qtdPagina){
				break;
			}
			
			count++;
			
			Document document = this.downloadAux(link.getLink());
			if (document != null){
				int zeroOrOne = this.classifyLinkHeuristica(link) ? 1 : 0;
				String linkPro = pro.wordProcessing(link.getComplemento(), stopword);
				String descPro = pro.wordProcessing(link.getDescription(), stopword);
				String title = document.getElementsByTag("title").text();
				int size = 30;
				title = title.length() > size ? title.substring(0, size) : title;
				title = pro.wordProcessing(title, stopword);
				String name = zeroOrOne + "-" + this.site + "-" + linkPro + "-" + descPro + "-" + title;
				f.save(document.toString(), this.path, name.replaceAll(" ", "_"), format);
				try {
//					System.out.println("Esperando...");
					TimeUnit.SECONDS.sleep(this.seconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Document downloadAux(String link){

		Document document = null;
		try {
			document = Jsoup.connect(link).header("Content-Type", "text/html").timeout(5).get();
			Elements elements = document.select("a[href]");
			for (Element l : elements) {
				String lin = l.attr("abs:href");
				if(!lin.matches("\\s*") && lin.matches(this.regex) && !setLink.contains(lin)){	
					Link link1 = new Link(lin, l.text());
					this.listLink.add(link1);
					this.setLink.add(lin);
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
		NaiveBayesResult result = this.naiveBayes.classify(link.getTokens());
		if(result.getClasse().equals(Classe.POSITIVO)){
			return true;
		}
		return false;
	}
	
}
