package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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

public class Pagina extends Thread{

	private Robot robot;
	private Link link;
	private List<Link> listLink;
	private Set<String> setLink;
	private String path;
	private NaiveBayes naiveBayes;
	private String regex;
	private boolean peso;
	private double limiar;
	private boolean train;
	private double countDownloaded;
	private int timeout;
	private int seconds;
	private int qtdPagina;
	private int num;
	private String name;
	private StringBuilder sb;
	private Files files;
	private boolean isNaiveBayes;
		
	/**
	 * Construtor para heuristica usando NaiveBayes
	 * @param link
	 * @param robot
	 * @param path
	 * @param seconds
	 * @param qtdPagina
	 * @param timeout
	 * @param naiveBayes
	 * @param peso
	 * @param train
	 */
	public Pagina(Link link, Robot robot, String path, int seconds, int qtdPagina, int timeout, NaiveBayes naiveBayes, boolean peso, boolean train){
		this.link = link;
		this.robot = robot;
		this.path = path;
		this.seconds = seconds;
		this.qtdPagina = qtdPagina;
		this.timeout = timeout;
		this.peso = peso;
		this.train = train;
		this.naiveBayes = naiveBayes;
		this.isNaiveBayes = true;
		this.inicio();
	}
	
	/**
	 * Construtor para busca em largura
	 * @param link
	 * @param robot
	 * @param path
	 * @param seconds
	 * @param qtdPagina
	 * @param timeout
	 */
	public Pagina(Link link, Robot robot, String path, int seconds, int qtdPagina, int timeout){
		this.link = link;
		this.robot = robot;
		this.path = path;
		this.seconds = seconds;
		this.qtdPagina = qtdPagina;
		this.timeout = timeout;
		this.isNaiveBayes = false;
		this.inicio();
	}
	
	
	private void inicio(){
		this.sb = new StringBuilder();
		this.files = new Files();
		this.regex = "htt(p|ps)://" + this.link.getBaseAux() + ".*";
		
		this.listLink = new ArrayList<Link>();
		this.listLink.add(this.link);
		
		this.setLink = new HashSet<String>();
		this.setLink.add(link.getLink());
		
		boolean www = this.link.getBaseAux().startsWith("www.");
		int beginIndex = www ? 4 : 0;
		int endIndex = this.link.getBaseAux().length() - 4;
		this.name = this.link.getBaseAux().substring(beginIndex, endIndex).replace(".", "_");
	}
	
	public void run(){
		if(this.isNaiveBayes){
			this.heuristicaNaiveBayes(this.link);
		}else{
			this.buscaLargura(this.link);
		}
	}
	
	/**
	 * Faz uma busca em largura usando o Naive Bayes para se guiar nos links positivos
	 * @param linkStart
	 */
	private void heuristicaNaiveBayes(Link linkStart){
			
		for(int i=0; i<this.listLink.size(); i++){
			
			if(this.countDownloaded >= this.qtdPagina){
				break;
			}
			
			Link link = this.listLink.get(i);
			System.out.println(link.getLink());
			
			//pega os links do documento que foi baixado
			List<Link> linksDocument = this.download(link.getLink());
			
			//classifica os links baixados
			linksDocument = this.classifyLinkNaive(linksDocument);
			
			//ordena os links e faz um corte com os melhores
			int limiar = (int) (linksDocument.size() * 0.5);
			Collections.sort(linksDocument);
			
			for(int j=0; j<limiar; j++){
				String linkReal = linksDocument.get(j).getLink().replaceAll("#.*", "");
				boolean mp4Pdf = linkReal.endsWith(".mp4") || linkReal.endsWith(".pdf");
				if(!this.setLink.contains(linkReal) && !mp4Pdf){
					this.listLink.add(linksDocument.get(j));
					this.setLink.add(linkReal);
				}
			}
			
			try {
				TimeUnit.SECONDS.sleep(this.seconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Faz uma busca em largura no link
	 * @param linkStart
	 */
	private void buscaLargura(Link linkStart){
			
		for(int i=0; i<this.listLink.size(); i++){
			
			if(this.countDownloaded >= this.qtdPagina){
				break;
			}
			
			Link link = this.listLink.get(i);
			System.out.println(link.getLink());
			List<Link> linksDocument = this.download(link.getLink());
			
			//evitar repeticao de link na lista
			for(Link l : linksDocument){
				String linkReal = l.getLink().replaceAll("#.*", "");
				if(!this.setLink.contains(linkReal)){
					this.listLink.add(l);
					this.setLink.add(linkReal);
				}
			}
			
			try {
				TimeUnit.SECONDS.sleep(this.seconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Faz o download da pagina
	 * @param link
	 * Link inicial
	 * @return
	 * Retorna os links dela
	 */
	private List<Link> download(String link){
		
		List<Link> listLinks = new ArrayList<Link>();
		
		try {
			Document document = Jsoup.connect(link).header("Content-Type", "text/html").timeout(this.timeout*1000).get();
			Elements elements = document.select("a[href]");
			
			String nameAux =  this.name+"_"+this.num;
			this.files.save(document.toString(), this.path, nameAux, ".html");
			this.num++;
			
			for (Element element : elements) {
				
				String href = element.attr("abs:href");
				boolean isEmpty = href.matches("\\s*");
				boolean matche = href.matches(this.regex);
				
				if(!isEmpty && matche){
					Link linkAux = new Link(href, element.text());
					
					//verifica se o link passa pelo robots.txt
					if(this.verifyRobot(linkAux)){
						listLinks.add(linkAux);
					}
				}
	        }
			
			this.countDownloaded++;
			
		} catch (IOException e) {
			System.err.println("Erro ao conectar no endereco: "+link+"\n");
			e.printStackTrace();
		}
		
		return listLinks;
	}
	
	/**
	 * Verifica se o link pertence ao dominio
	 * @param link
	 * @return
	 */
	private boolean verifyRobot(Link link){
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
	
	/**
	 * Classifica os links
	 * @param links
	 * @return
	 * Retorna uma lista dos links classificados positivamente
	 */
	private List<Link> classifyLinkNaive(List<Link> links){
		
		List<Link> listLinks = new ArrayList<Link>();
		Set<String> setLinkPositivo = new HashSet<String>();
		Set<String> setLinkNegativo = new HashSet<String>();
		
		for(int i=0; i<links.size(); i++){
			
			boolean download = this.classifyLinkNaive(links.get(i), this.peso, this.limiar);
			
			if(download){
				listLinks.add(links.get(i));
				setLinkPositivo.addAll(links.get(i).getAllTokens());
			}else{
				setLinkNegativo.addAll(links.get(i).getAllTokens());
			}
		}
		
		//treinar durante a execucao
		if(this.train){
			Iterator<String> positivoIterator = setLinkPositivo.iterator();
			while (positivoIterator.hasNext()){
				String s = positivoIterator.next();
				if(s.length() > 2 && s.length() <= 8){
					this.naiveBayes.train(s, Classe.POSITIVO);
				}
			}
			
			Iterator<String> negativoIterator = setLinkNegativo.iterator();
			while (negativoIterator.hasNext()){
				String s = negativoIterator.next();
				if(s.length() > 2 && s.length() <= 8){
					this.naiveBayes.train(s, Classe.NEGATIVO);
				}
			}
			setLinkNegativo.clear();
			setLinkPositivo.clear();
		}
		return listLinks;
	}
	
	/**
	 * Classifica um link
	 * @param link
	 * @param peso
	 * @param limiar
	 * @return
	 */
	private boolean classifyLinkNaive(Link link, boolean peso, double limiar){
		
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
