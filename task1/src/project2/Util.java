package project2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import project2.model.Page;
import project2.model.Token;
import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Util {
	
	WordProcessing pro = WordProcessing.getInstance();
	
	public List<Page> ler(String path){
		File file = new File(path);
		File[] files = Arrays.stream(file.listFiles()).filter(s -> s.getName().endsWith("xml")).toArray(File[]::new);
		List<Page> pages = new ArrayList<>();

		for(int i=0; i<files.length; i++){
			Page pageXML = new Page();
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Page.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				pageXML = (Page) jaxbUnmarshaller.unmarshal(files[i]);
			} catch (JAXBException e) {
				//e.printStackTrace();
			}
			pageXML.setName(files[i].getName());
			pageXML.setId(i+1);
			pages.add(pageXML);
		}
		return pages;
	}
	
	public Postings processarPages(List<Page> pages){

		Map<String, List<Token>> map = new HashMap<String, List<Token>>();
		for (Page p : pages){
			Map<String, Token> m = processar(p);

			for (String key : m.keySet()){
				List<Token> value = map.containsKey(key) ? map.get(key) : new ArrayList<>();
				value.add(m.get(key));
				map.put(key, value);
			}
		}

		return new Postings(false, map);
	}

	public Map<String, Token> processar(Page page){
		
		Map<String, Token> m = new HashMap<String, Token>();

		List<String> regex = new ArrayList<>();
		//		regex.add("[0-9]*");
		//		regex.add(".{1}");

		List<String> tokens = this.pro.tokens(page.getBody(), Stopword.NONE, regex, true, true, false);

		for (int j=0; j<tokens.size(); j++){
			String key = tokens.get(j);

			Token value = null;
			if(m.containsKey(key)){
				value = m.get(key);
				value.addPosition(j+1);
				value.incrementCount();
			}else{
				value = new Token();
				value.setNameDocument(page.getName());
				value.setToken(key);
				value.setIdDocument(page.getId());
				value.addPosition(j+1);
				value.incrementCount();
			}
			m.put(key, value);
		}
		return m;
	}

	public void salvar(Page page, String path){
		try {
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Page.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(page, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public List<Page> exempleTeste(){

		String d1 = "To do is to be. To be is to do";
		String d2 = "To be or not to be. I am what I am.";
		String d3 = "I think therefore I am. Do be do be do.";
		String d4 = "Do do do, da da da. Let it be, let it be.";

		Page p1 = new Page();
		p1.setId(1);
		p1.setBody(d1);
		
		Page p2 = new Page();
		p2.setId(2);
		p2.setBody(d2);
		
		Page p3 = new Page();
		p3.setId(3);
		p3.setBody(d3);
		
		Page p4 = new Page();
		p4.setId(4);
		p4.setBody(d4);

		List<Page> pages = new ArrayList<>();
		pages.add(p1);
		pages.add(p2);
		pages.add(p3);
		pages.add(p4);
		return pages;
	}
	
	public Object deSerialization(String file) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream(file);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		bufferedInputStream.close();
		fileInputStream.close();
		return object;
	}

	public void serialization(String file, Object object) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
		objectOutputStream.writeObject(object);
		objectOutputStream.close();
		bufferedOutputStream.close();
		fileOutputStream.close();
	}
}