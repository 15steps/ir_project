package crawler;

import java.util.List;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("TO-DO WEBCRAWLER");
		
		List<String> list = new Vector<String>();

//		list.add("https://www.bestbuy.com/site/featured-offers/mobile-phone-cell-phone-sale/pcmcat125500050005.c?id=pcmcat125500050005");
//		list.add("https://www.apple.com/iphone/");
//		list.add("https://www.samsung.com/us/mobile/phones/");
//		list.add("https://store.asus.com/us/category/A26208");
//		list.add("https://www.lg.com/us/cell-phones");
//		list.add("https://www.mi.com/en/list/");
//		list.add("https://www.motorola.com/us/products/moto-smartphones");
//		list.add("https://www.sonymobile.com/us/products/phones/");
//		list.add("http://bluproducts.com/android-phones/");
//		list.add("http://www.htc.com/us/");
		

		String path = "files/";
		Pagina pagina = new Pagina();
		
		for(String site : list){
			String robots = site.substring(0, site.indexOf(".com") + 4) + "/robots.txt";
			pagina.downloadPage(site, path, ".html");
			pagina.downloadRobot(robots, path, ".txt");
		}
	}

}
