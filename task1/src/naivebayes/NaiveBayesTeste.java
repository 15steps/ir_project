package naivebayes;

import crawler.Link;
import files.Files;

public class NaiveBayesTeste {

	public static void main(String[] args) {
		
		NaiveBayes naiveBayes = new NaiveBayes();		
		
		/*
		naiveBayes.train("You need to buy some Viagra".split("\\s+"), Classe.NEGATIVO);
		naiveBayes.train("This is not spam, just a letter to Bob.".split("\\s+"), Classe.POSITIVO);
		naiveBayes.train("Hey Oasic, Do you offer consulting?".split("\\s+"), Classe.POSITIVO);
		naiveBayes.train("You should buy this stock".split("\\s+"), Classe.NEGATIVO);
		
		String[] tokens = "Now is the time to buy Viagra cheaply and discreetly".split("\\s+");
		
		System.out.println(naiveBayes.classify(tokens));
		/**/
		
		String [] positivo = {"phone", "phones", "galaxy", "cell", "mobile", "device", "smartphone", "smartphones", "iphone", 
				"telephones", "electronics"};
		
		String [] negativo = {"tv", "mac", "ipad", "watch", "music", "accessories", "homepod", "help", "privacy", "contact", 
				"search", "ipod", "tablets", "theater", "tvs", "video", "virtual", "computers", "games", "security" ,"support",
				"tablet", "hdmi", "cable", "computing", "windows", "desktops", "laptops", "audio", "reality", "wearables", 
				"televisions", "content", "policy", "accessibility", "sustainability", "about", "apps", "printers", "health",
				"care", "solutions", "safety", "tools", "software", "geek", "voice", "control", "sensors", "alarms", "scanners", 
				"office", "drones", "toys", "collectibles", "cameras"};
		
		naiveBayes.train(positivo, Classe.POSITIVO);
		naiveBayes.train(negativo, Classe.NEGATIVO);
		
		//*
		Files f = new Files();
		String path2 = "test/links_apple.txt";
		for(String url : f.reader(path2)){
			Link link = new Link(url, "");
			
			System.out.println(url);
			System.out.println(naiveBayes.classify(link.getTokens()));
			System.out.println(link.getTokens());
			System.out.println();

		}
		/**/
	}

}
