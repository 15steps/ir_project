package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
	private String regex;
	private String site;
	private boolean peso;
	private boolean heuristica;
	private boolean train;
	private double count;
	private double countDownloaded;
	private int mod = 10;
	private int timeout;
	private int seconds;
	private int qtdPagina;
	private int num;
	private String name;
	private StringBuilder sb;
	
	
	public Pagina(Link link, NaiveBayes naiveBayes, Robot robot, String path, boolean peso, boolean train, boolean heuristica, int seconds, int qtdPagina, int timeout) {
		this.sb = new StringBuilder();
		this.link = link;
		this.peso = peso;
		this.train = train;
		this.heuristica = heuristica;
		this.timeout = timeout;
		this.regex = "htt(p|ps)://"+ this.link.getBaseAux() + ".*";
		this.listLink = new ArrayList<Link>();
		this.listLink.add(this.link);
		this.setLink = new HashSet<String>();
		this.setLink.add(link.getLink());
		this.name = link.getBaseAux().replaceAll("\\.", "_");
		
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
		
		List<String> list = f.reader(this.path + this.name + ".txt");
		for(String s : list){
			if(!s.isEmpty()){
				String [] array = s.split("\\s+");
				
				if(!this.setLink.contains(array[1])){
					this.setLink.add(array[1]);
					this.sb.append(s + "\n"); 
				}
			}
		}
		this.countDownloaded = list.size() + 1;
		
		WordProcessing pro = new WordProcessing();
		
		Stopword stopword = Stopword.NONE;
		
		for(int i=0; i<this.listLink.size(); i++){
			
			Link link = this.listLink.get(i);
			System.out.println(link.getLink());
			Document document = this.downloadAux(link.getLink());
			if (document != null){
				
				String nameAux =  this.name+"_"+this.num;
				f.save(document.toString(), this.path, nameAux, format);
				
				this.num++;
//				if(this.setLink.contains(link.getLink())){
					this.sb.append(nameAux + ".html" + " " + link.getLink() + "\n"); 
//				}

				this.countDownloaded++;
				if(this.countDownloaded > this.qtdPagina){
					break;
				}

				try {
					TimeUnit.SECONDS.sleep(this.seconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(this.countDownloaded % this.mod == 0){
				System.out.println(link.getBaseAux() + " - " + this.countDownloaded + "/" + this.count + " - " + (this.countDownloaded/this.count) + "%");
			}
		}
		
		//salva no final o estado
		f.save(this.sb.toString(), this.path, this.name, ".txt");
	}
	
	public Document downloadAux(String link){

		Document document = null;
		try {
			document = Jsoup.connect(link).header("Content-Type", "text/html").timeout(timeout*1000).get();
			Elements elements = document.select("a[href]");
			
			List<Link> listAux = new ArrayList<Link>();
			
			for (Element l : elements) {
				String lin = l.attr("abs:href");
				if(!lin.matches("\\s*") && lin.matches(this.regex) && !setLink.contains(lin)){	
					
					Link link1 = new Link(lin, l.text());
					//verifica se o link passa pelo robots.txt
					boolean robots = this.verifyRobot(link1);
					
					if(robots){
						if(this.heuristica){
							//classificar o link para ver se faz ou nao o download
							double limiar = 1;
							boolean download = this.classifyLinkNaive(link1, this.peso, limiar);
							
							//treinar durante a execucao
							if(this.train){
								List<String> tokens = this.peso ? link1.getAllTokens2() : link1.getAllTokens();
								Classe classe = download ? Classe.POSITIVO : Classe.NEGATIVO;
								this.naiveBayes.train(tokens, classe);
							}
							
							//se as condicoes forem verdadeiras, faz o download da pagina
							if(download && (this.count < this.qtdPagina)){
								listAux.add(link1);
							}
						}else{
							listAux.add(link1);
						}
						this.count += listAux.size();
					}
				}
	        }
			
			if(this.heuristica){
				int limiar = (int) (listAux.size() * 0.5);
				Collections.sort(listAux);
				int size = listAux.size() < limiar ? listAux.size() : limiar;
				
				for(int i=0; i< size; i++){
					if(!this.setLink.contains(listAux.get(i).getLink())){
						this.listLink.add(listAux.get(i));
						this.setLink.add(listAux.get(i).getLink());
					}
				}
			}else{
				for(int i=0; i<listAux.size(); i++){
					if(!this.setLink.contains(listAux.get(i).getLink())){
						this.listLink.add(listAux.get(i));
						this.setLink.add(listAux.get(i).getLink());
					}
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
//		System.out.println(link.getLink());
		Agent agent = robot.getAgentByName("*");
		for(ComandAgent comandAgent : agent.getListComandAgent()){
			if(comandAgent.getComandAgent().equals(TComandAgent.ALLOW)){
				if(link.getLink().matches(comandAgent.getRejex())){
					retorno = true;
//					System.out.println(link.getLink() + " - " + comandAgent.getRejex());
				}
			}else if(comandAgent.getComandAgent().equals(TComandAgent.DISALLOW)){
				if(link.getLink().matches(comandAgent.getRejex())){
					retorno = false;
//					System.out.println(link.getLink() + " - " + comandAgent.getRejex());
				}
			}
		}
//		System.out.println("----------------");
		return retorno;
	}
	
	public boolean classifyLinkNaive(Link link, boolean peso, double limiar){
		
		List<String> tokens = peso ? link.getAllTokens2() : link.getAllTokens();
		NaiveBayesResult result = this.naiveBayes.classify(tokens);
		double prob = (result.getPositivo() - result.getNegativo());
		link.setProb(prob);
		if(prob > limiar){
			return true;
		}
		return false;
	}
	
}
