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
import robots.Agent;
import robots.ComandAgent;
import robots.Robot;
import robots.TComandAgent;
import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Pagina extends Thread{

	private Robot robot;
	private Link link;
	private List<Link> listLink;
	private Set<String> setLink;
	private String path;
	private NaiveBayes naiveBayes;
	private int seconds;
	private int qtdPagina;
	private String regex;
	private String site;
	private boolean peso;
	private int timeout;
	private boolean train;
	
	public Pagina(Link link, NaiveBayes naiveBayes, Robot robot, String path, boolean peso, boolean train, int seconds, int qtdPagina, int timeout) {
		
		this.setLink = new HashSet<String>();
		this.link = link;
		this.peso = peso;
		this.train = train;
		this.timeout = timeout;
		this.regex = "htt(p|ps)://"+ this.link.getBaseAux() + ".*";
		this.listLink = new ArrayList<Link>();
		this.listLink.add(this.link);
		
		boolean www = this.link.getBaseAux().startsWith("www.");
		int beginIndex = www ? 4 : 0;
		int endIndex = this.link.getBaseAux().length() - 4;
		this.site = this.link.getBaseAux().substring(beginIndex, endIndex).replace(".", "_");
		
		this.path = path;
		this.naiveBayes = naiveBayes;
		this.qtdPagina = qtdPagina;
		this.seconds = seconds;
		this.robot = robot;
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
			
			//classificar o link para ver se faz ou nao o download
			boolean download = this.classifyLinkNaive(link, this.peso);
			
			//verifica se o link passa pelo robots.txt
			boolean robots = this.verifyRobot(link);
			
			//treinar durante a execucao
			if(this.train){
				List<String> tokens = this.peso ? link.getAllTokens2() : link.getAllTokens();
				Classe classe = download ? Classe.POSITIVO : Classe.NEGATIVO;
				this.naiveBayes.train(tokens, classe);
			}
			
			//se as condicoes forem verdadeiras, faz o download da pagina
			if(robots && download){
				System.out.println(link);
				Document document = this.downloadAux(link.getLink());
				if (document != null){
					int zeroOrOne = this.classifyLinkNaive(link, this.peso) ? 1 : 0;
					String linkPro = pro.wordProcessing(link.getComplemento(), stopword);
					String descPro = pro.wordProcessing(link.getDescription(), stopword);
					String title = document.getElementsByTag("title").text();
					int size = 30;
					title = title.length() > size ? title.substring(0, size) : title;
					title = pro.wordProcessing(title, stopword);
					String name = zeroOrOne + "-" + this.site + "-" + linkPro + "-" + descPro + "-" + title;
					f.save(document.toString(), this.path, name.replaceAll(" ", "_"), format);
					
					count++;
					
					try {
						TimeUnit.SECONDS.sleep(this.seconds);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public Document downloadAux(String link){

		Document document = null;
		try {
			document = Jsoup.connect(link).header("Content-Type", "text/html").timeout(timeout*1000).get();
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
	
	public boolean verifyRobot(Link link){
		
		boolean retorno = true;
		
		Agent agent = robot.getAgentByName("*");
		for(ComandAgent comandAgent : agent.getListComandAgent()){
			if(comandAgent.getComandAgent().equals(TComandAgent.ALLOW)){
				if(link.getLink().matches(comandAgent.getRejex())){
					retorno = true;
				}
			}else if(comandAgent.getComandAgent().equals(TComandAgent.DISALLOW)){
				if(link.getLink().matches(comandAgent.getRejex())){
					retorno = false;
				}
			}
		}
		return retorno;
	}
	
	public boolean classifyLinkNaive(Link link, boolean peso){
		
		List<String> tokens = peso ? link.getAllTokens2() : link.getAllTokens();
		NaiveBayesResult result = this.naiveBayes.classify(tokens);
		if((result.getPositivo() - result.getNegativo()) > 0){
			return true;
		}
		
		return false;
	}
	
}
