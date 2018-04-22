package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

public class Files {
	
	public void save(String document, String path, String name, String format){
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
	
	public void reader(String path, String format){
		File file = new File(path);
		if(file.isDirectory()){
//			for(File f : file.listFiles()){
//				
//			}
		}else{
			if(file.exists() && file.getName().endsWith(format)){
				
			}
		}
	}
	
	public List<String> reader(String path){
		File file = new File(path);
		List<String> list = new Vector<String>();
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
