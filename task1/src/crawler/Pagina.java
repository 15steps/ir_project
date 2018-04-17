package crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import robots.Robot;

public class Pagina {

	private Robot robot;
	private String link;
	private String base;
	private String baseAux;
	private String path;
	private Links links;
	private Document document;
	
	public Pagina(String link, String path) {
		this.link = link;
		this.path = path;
		this.base = link.substring(0, link.indexOf(".com") + 4);
		this.baseAux = this.base.replaceAll("htt(ps|p)://", "");
		this.robot = new Robot(base + "/robots.txt", path);
		this.links = new Links();
	}
	
	public void download(){
		this.robot.download();
		this.save(this.robot.getSb().toString(), path, this.robot.getName(), "");
		this.document = downloadAux(link, 0);
	}
	
	public void downloadNivel(int nivel, int seconds){
		for(int i=0; i<this.links.size(); i++){
			Link link = this.links.get(i);
			if(link.getNivel() < nivel){
				downloadAux(link.getLink(), link.getNivel());
				System.out.println(link.toString());
				try {
					System.out.println("Esperando...");
					TimeUnit.SECONDS.sleep(seconds);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Document downloadAux(String link, int nivel){
		
		String format = ".html";
		Document document = null;
		
		try {
			document = Jsoup.connect(link).get();
			String tag = document.getElementsByTag("title").text().replaceAll("\\s+", "_");
			int size = 20;
			tag = tag.length() > size ? tag.substring(0, size) : tag;
			
			this.save(document.toString(), path, tag, format);
			
			Elements elements = document.select("a[href]");
			for (Element l : elements) {
				Link lin = new Link(l, nivel+1);
				this.links.add(lin, this.base);
	        }
			
		} catch (IOException e) {
			System.err.println("Erro ao conectar no endereco: "+link+"\n");
			e.printStackTrace();
		}
		
		return document;
	}
	
	private void save(String document, String path, String name, String format){
		try {
			
			int i = 1;
			File file = new File(path + name + format);
			while(file.exists()){
				file = new File(path + name + "_" + i + format);
				i++;
			}
			
			if (i > 1){
				name = name + "_" + i;
			}
			
			FileWriter fileWriter = new FileWriter(path + name + format);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(document);
			printWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			System.err.println("Erro ao salvar o arquivo: "+ path + name +"\n");
			e.printStackTrace();
		}
	}
	
	/*
	public void reader(String path, String format){
		File file = new File(path);
		if(file.isDirectory()){
			for(File f : file.listFiles()){
				if(f.exists() && f.getName().endsWith(format)){
					
					List<String> list = this.reader(f);
					
					Robot r = new Robot();
					r.genereteRobot(list, "*");
					
					System.out.println(f.getName());
					for(Agent a : r.getAgent()){
						System.out.println(a.toString());
						System.out.println();
					}
				}
			}
		}else{
			if(file.exists() && file.getName().endsWith(format)){
				
				List<String> list = this.reader(file);
				
				Robot r = new Robot();
				r.genereteRobot(list, "*");
				
				System.out.println(file.getName());
				for(Agent a : r.getAgent()){
					System.out.println(a.toString());
					System.out.println();
				}
			}
		}
	}
	
	public List<String> reader(File file){
		List<String> list = new Vector<>();
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);

			String line;
			while ((line = br.readLine()) != null){
				list.add(line);
			}
			
			reader.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	/**/
}
