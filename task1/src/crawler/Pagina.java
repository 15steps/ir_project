package crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import robots.Agent;
import robots.Robot;

public class Pagina {

	public Pagina() {
		
	}
	
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
	
	public void downloadPage(Links links, String link, String path, String format){
		
		String base = link.substring(0, link.indexOf(".com") + 4);
		String robots = base + "/robots.txt";
		this.downloadRobot(robots, path, ".txt");
		
		try {
			Document document = Jsoup.connect(link).get();
			String tag = document.getElementsByTag("title").text().replaceAll("\\s+", "_");
			int size = 20;
			tag = tag.length() > size ? tag.substring(0, size) : tag;
			this.save(document.toString(), path, tag + format);
			
			Elements elements = document.select("a[href]");
			for (Element l : elements) {
				Link lin = new Link();
				lin.setElement(l);
				links.add(lin, base);
	        }
			
		} catch (IOException e) {
			System.err.println("Erro ao conectar no endereco: "+link+"\n");
			e.printStackTrace();
		}
	}
	
	public List<String> downloadRobot(String link, String path, String format){
		
		List<String> list = new Vector<String>();
		StringBuilder sb = new StringBuilder();
		
		try{
			URL url = new URL(link);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/txt");
			conn.connect();

			InputStreamReader inputStream = new InputStreamReader(conn.getInputStream());
			BufferedReader inStream = new BufferedReader(inputStream);
			String line;
			
			while ((line = inStream.readLine()) != null){
				sb.append(line);
				sb.append("\n");
				list.add(line);
			}
			
			inStream.close();
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			String name = link.replace('.', '_').replace("_txt", ".txt").replace('/', '|');
			this.save(sb.toString(), path, name);
		}
		
		return list;
	}

	public void save(String document, String path, String name){
		try {
			FileWriter fileWriter = new FileWriter(path + name);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(document);
			printWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			System.err.println("Erro ao salvar o arquivo: "+ path + name +"\n");
			e.printStackTrace();
		}
	}
}
