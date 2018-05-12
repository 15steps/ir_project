package crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import files.Files;
import naivebayes.Classe;
import naivebayes.NaiveBayes;
import robots.Robot;
import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Main {

	public static void main(String[] args) {
		
		
		List<String> listLinks = new ArrayList<String>();
		listLinks.add("https://www.apple.com/iphone/");
		listLinks.add("https://www.samsung.com/us/mobile/phones/");
		listLinks.add("https://www.asus.com/us/Phone/");
		listLinks.add("https://www.lg.com/us/cell-phones");
		listLinks.add("https://www.mi.com/en/list/");
		listLinks.add("https://www.motorola.com/us/products/moto-smartphones");
		listLinks.add("https://www.sonymobile.com/us/products/phones/");
		listLinks.add("http://bluproducts.com/android-phones/");
		listLinks.add("http://www.htc.com/us/");
		listLinks.add("https://www.banggood.com/Wholesale-Smartphones-c-1567.html");
		
		List<String> listRobots = new Vector<String>();
		listRobots.add("apple.txt");
		listRobots.add("samsung.txt");
		listRobots.add("asus.txt");
		listRobots.add("lg.txt");
		listRobots.add("mi.txt");
		listRobots.add("motorola.txt");
		listRobots.add("sony.txt");
		listRobots.add("blu.txt");
		listRobots.add("htc.txt");
		listRobots.add("banggood.txt");
		
		String path = "files_teste/";
		
		List<String> listPaths = new ArrayList<String>();
		listPaths.add(path + "apple/");
		listPaths.add(path + "samsung/");
		listPaths.add(path + "asus/");
		listPaths.add(path + "lg/");
		listPaths.add(path + "mi/");
		listPaths.add(path + "motorola/");
		listPaths.add(path + "sony/");
		listPaths.add(path + "blu/");
		listPaths.add(path + "htc/");
		listPaths.add(path + "banggood/");
		
		NaiveBayes naiveBayes = new NaiveBayes();
		
		String [] positivo = {"phone", "phones", "galaxy", "cell", "mobile", "device", "smartphone", "smartphones", "iphone", 
				"telephones", "electronics", "zenphone" ,"dual", "sim", "lte", "plus", "mi", "max", "mix", "redmi", "android", "vivo", 
				"mini", "moto", "play", "gen", "snapdragon", "qualcomm", "devices", "note", "3g", "4g", "touchscreen", "touch"};
		
		String [] negativo = {"tv", "mac", "ipad", "ipod", "watch", "music", "accessories", "homepod", "help", "privacy", "contact", 
				"search", "ipod", "tablet", "tablets", "theater", "tvs", "video", "virtual", "computers", "games", "security" ,"support",
				"tablet", "hdmi", "cable", "computing", "windows", "desktops", "laptop", "laptops", "audio", "reality", "wearables", 
				"televisions", "content", "policy", "accessibility", "sustainability", "about", "apps", "printers", "health",
				"care", "solutions", "safety", "tools", "software", "geek", "voice", "control", "sensors", "alarms", "scanners", 
				"office", "drones", "toys", "collectibles", "cameras", "user", "myaccount", "message", "account", "faq", "gaming", 
				"notebooks", "notebook", "book", "books", "keyboard"};
		
		naiveBayes.train(positivo, Classe.POSITIVO);
		naiveBayes.train(negativo, Classe.NEGATIVO);
		
		Files file = new Files();
		Classe classe = null;
		
		WordProcessing pro = new WordProcessing();
		Stopword stopword = Stopword.NONE;
		
		List<String> lines = file.reader("features.txt");
		
		for(String line : lines){
			if(line.startsWith("#GOOD")){
				classe = Classe.POSITIVO;
			}else if(line.startsWith("#BAD")){
				classe = Classe.NEGATIVO;
			}else if(line.trim().isEmpty() || line.startsWith("//")){
				continue;
			}else{
				Link link = new Link(line, "");
				List<String> list = pro.tokens(link.getComplemento(), stopword);
				naiveBayes.train(list, classe);
			}
		}
		
		String pathRobot = "robots/";
		
		//parametros
		int segundos = 6;
		int qtdPaginas = 100;
		int timeout = 25; //em segundos
		boolean peso = false; //true, maior peso para a informacao da ancora
		boolean train = false; //treinar para todo link verificado
		boolean heuristica = true; //se vai ou nao usar a heuristica, ou só busca em largura
		
		for(int i=0; i<listLinks.size(); i++){
			Link link = new Link(listLinks.get(i), "SMARTPHONE");
			Robot robot = new Robot(link.getBase(), (pathRobot + listRobots.get(i)), false);
			
			Pagina pagina = null;
			if(heuristica){
				pagina = new Pagina(link, robot, listPaths.get(i), segundos, qtdPaginas, timeout, naiveBayes, peso, train);
			}else{
				pagina = new Pagina(link, robot, listPaths.get(i), segundos, qtdPaginas, timeout);
			}
			pagina.start();
		}
		
	}

}
