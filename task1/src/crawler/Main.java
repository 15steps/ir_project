package crawler;

import java.util.List;
import java.util.Vector;

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
		
		String path = "files/";
		String [] heuristica1 = {"phone", "phones", "galaxy", "cell", "mobile", "device", "smartphone", "smartphones", "iphone", 
				"zenphone" ,"dual", "sim", "lte", "plus", "mi", "max", "mix", "android", "vivo", "mini", "moto", "play", "gen",
				"snapdragon", "qualcomm", "devices"};
		
		for(int i=0; i<list.size(); i++){
			int segundos = 10;
			int qtdPaginas = 1000;
			Pagina pagina = new Pagina(list.get(i), heuristica1, path + paths.get(i), segundos, qtdPaginas);
			pagina.start();
		}
		
	}

}
