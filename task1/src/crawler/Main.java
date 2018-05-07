package crawler;

import java.util.List;
import java.util.Vector;

import naivebayes.Classe;
import naivebayes.NaiveBayes;

public class Main {

	public static void main(String[] args) {
		
		
		List<String> list = new Vector<String>();
//		list.add("https://www.bestbuy.com/site/featured-offers/mobile-phone-cell-phone-sale/pcmcat125500050005.c?id=pcmcat125500050005");
		list.add("https://www.apple.com/iphone/");
		list.add("https://www.samsung.com/us/mobile/phones/");
		list.add("https://store.asus.com/us/category/A26208");
		list.add("https://www.lg.com/us/cell-phones");
		list.add("https://www.mi.com/en/list/");
		list.add("https://www.motorola.com/us/products/moto-smartphones");
		list.add("https://www.sonymobile.com/us/products/phones/");
		list.add("http://bluproducts.com/android-phones/");
		list.add("http://www.htc.com/us/");
		list.add("https://www.banggood.com/Wholesale-Smartphones-c-1567.html");
		list.add("https://www.banggood.com/robots");
		
		List<String> paths = new Vector<String>();
//		paths.add("bestbuy/");
		paths.add("apple/");
		paths.add("samsung/");
		paths.add("asus/");
		paths.add("lg/");
		paths.add("mi/");
		paths.add("motorola/");
		paths.add("sony/");
		paths.add("blu/");
		paths.add("htc/");
		paths.add("bangood/");
		
		String path = "files2/";
		
		
		
		NaiveBayes naiveBayes = new NaiveBayes();
		
		String [] positivo = {"phone", "phones", "galaxy", "cell", "mobile", "device", "smartphone", "smartphones", "iphone", 
				"telephones", "electronics", "zenphone" ,"dual", "sim", "lte", "plus", "mi", "max", "mix", "android", "vivo", 
				"mini", "moto", "play", "gen", "snapdragon", "qualcomm", "devices"};
		
		String [] negativo = {"tv", "mac", "ipad", "watch", "music", "accessories", "homepod", "help", "privacy", "contact", 
				"search", "ipod", "tablets", "theater", "tvs", "video", "virtual", "computers", "games", "security" ,"support",
				"tablet", "hdmi", "cable", "computing", "windows", "desktops", "laptops", "audio", "reality", "wearables", 
				"televisions", "content", "policy", "accessibility", "sustainability", "about", "apps", "printers", "health",
				"care", "solutions", "safety", "tools", "software", "geek", "voice", "control", "sensors", "alarms", "scanners", 
				"office", "drones", "toys", "collectibles", "cameras"};
		
		naiveBayes.train(positivo, Classe.POSITIVO);
		naiveBayes.train(negativo, Classe.NEGATIVO);
		
		for(int i=0; i<list.size(); i++){
			int segundos = 10;
			int qtdPaginas = 0;
			Pagina pagina = new Pagina(list.get(i), naiveBayes, path + paths.get(i), segundos, qtdPaginas);
//			pagina.start();
		}
		
	}

}
