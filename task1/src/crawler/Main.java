package crawler;

import java.util.List;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		
		List<String> list = new Vector<String>();

//		list.add("https://www.bestbuy.com/site/featured-offers/mobile-phone-cell-phone-sale/pcmcat125500050005.c?id=pcmcat125500050005");
		list.add("https://www.apple.com/iphone/");
//		list.add("https://www.samsung.com/us/mobile/phones/");
//		list.add("https://store.asus.com/us/category/A26208");
//		list.add("https://www.lg.com/us/cell-phones");
//		list.add("https://www.mi.com/en/list/");
//		list.add("https://www.motorola.com/us/products/moto-smartphones");
//		list.add("https://www.sonymobile.com/us/products/phones/");
//		list.add("http://bluproducts.com/android-phones/");
//		list.add("http://www.htc.com/us/");
		
		String path = "test3/";
		String [] heuristica1 = {"phone", "phones", "galaxy", "cell", "mobile", "device", "smartphone", "smartphones", "iphone"};
		
		for(String link : list){
			Pagina pagina = new Pagina(link, heuristica1, path);
			int segundos = 10;
			int qtdPaginas = 10;
			pagina.download(segundos, qtdPaginas);
		}
		
	}

}
