package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Files {
	
	/**
	 * Salva um documento
	 * @param document
	 * @param path
	 * @param name
	 * @param format
	 */
	public void save(String document, String path, String name, String format){
		try {
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
	
	/**
	 * Cria os diretorios passados como entrada
	 * @param paths
	 */
	public void mkdir(List<String> paths){
		for(String path : paths){
			try{
				File dir = new File(path);
				if(!dir.exists()){
					dir.mkdirs();
				}
			} catch (Exception e) {
				System.err.println("Erro ao criar o diretorio: "+ path +"\n");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Ler um arquivo e retorna uma lista das linhas
	 * @param path
	 * @return
	 */
	public List<String> reader(String path){
		File file = new File(path);
		List<String> list = new ArrayList<String>();
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
	
}
