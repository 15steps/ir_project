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

import project2.model.Attributes;
import project2.model.Page;
import project2.model.Posting;
import project2.model.Quartis;
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
	
	public Postings processarPages(List<Page> pages, boolean grap){

		Map<String, List<Token>> map = new HashMap<String, List<Token>>();
		for (Page p : pages){
			Map<String, Token> m = processar(p);

			for (String key : m.keySet()){
				List<Token> value = map.containsKey(key) ? map.get(key) : new ArrayList<>();
				value.add(m.get(key));
				map.put(key, value);
			}
		}
		Postings postings = new Postings(grap, map);
		return postings;
	}
	
	public Postings processarPagesAtributos(List<Page> pages, boolean grap){
		
		List<Attributes> attributesList = new ArrayList<Attributes>();
		
		double[] max = new double[5];
		
		for(Page p : pages){
			Attributes attri = processarAtributos(p);
			attributesList.add(attri);
			
			double[] attributes = attri.getAttributesQuartis();
			
			for(int j=0; j<attributes.length; j++){
				if(attributes[j] > max[j]){
					max[j] = attributes[j];
				}
			}
		}
		Map<String, Posting> map = new HashMap<String, Posting>();
		Quartis[][] quartisList = new Quartis[5][4];
		String[] nameList = attributesList.get(0).getNamesQuartis();
		String[] nameNormalList = attributesList.get(0).getNamesNormal();

		for(int j=0; j<max.length; j++){
			double a = max[j] / 4;
			double b = a * 2;
			double c = a * 3;
			double d = max[j];
			
			quartisList[j][0] = new Quartis(0, a);
			quartisList[j][1] = new Quartis(a, b);
			quartisList[j][2] = new Quartis(b, c);
			quartisList[j][3] = new Quartis(c, d);
		}		
			
		for(int j=0; j<quartisList.length; j++){
			for(int k=0; k<quartisList[0].length; k++){

				Quartis quartis = quartisList[j][k];
				String key = quartis.getTag() + " / " + nameList[j];
				
				List<Integer> docIDsList = new ArrayList<>();
				List<String> docNameList = new ArrayList<>();
				
				for(int i=0; i<pages.size(); i++){
					Page page = pages.get(i);
					
					Attributes attri = processarAtributos(page);
					
					double a = 0;
					if (j == 0){
						a = attri.getScreenSize();
					}else if(j == 1){
						a = attri.getCameraRes();
					}else if(j == 2){
						a = attri.getBatteryCapacity();
					}else if(j == 3){
						a = attri.getProcessorSpeed();
					}else if(j == 4){
						a = attri.getWeight();
					}
					
					if (quartis.contem(a)){
						docIDsList.add(page.getId());
						docNameList.add(page.getName());
					}
				}
				
				Posting pos = new Posting();
				pos.setDocIDs(docIDsList.stream().mapToInt(i->i).toArray());
				pos.setDocName(docNameList.stream().toArray(String[]::new));
				pos.setGrap(false);
				pos.setTerm(key);
				
				map.put(key, pos);
			}
		}
		
		
		Map<String, List<Page>> mapAux = new HashMap<String, List<Page>>();

		for(int j=0; j<pages.size(); j++){
			Page page = pages.get(j);
			Attributes attri = processarAtributos(page);

			for(int i=0; i<nameNormalList.length; i++){
				String a = "";
				if(i==0){
					a = (int) attri.getScreenResolutionWidth() + "x" + (int) attri.getScreenResolutionHeight();
				}else if(i==1){
					a = (int) attri.getRam() + "";
				}else if(i==2){
					a = (int) attri.getInternalMemory() + "";
				}
					
				String key = a + " / " + nameNormalList[i];
				if(mapAux.containsKey(key)){
					mapAux.get(key).add(page);
				}else{
					List<Page> list = new ArrayList<>();
					list.add(page);
					mapAux.put(key, list);
				}
			}
		}
		
		for(String key : mapAux.keySet()){
			Posting pos = new Posting();
			pos.setDocIDs(mapAux.get(key).stream().mapToInt(p -> p.getId()).toArray());
			pos.setDocName(mapAux.get(key).stream().map(p -> p.getName()).toArray(String[]::new));
			pos.setGrap(false);
			pos.setTerm(key);
			
			map.put(key, pos);
		}
		
		Postings postings = new Postings();
		postings.setMap(map);
		postings.setGrap(grap);

		return postings;
	}

	public Map<String, Token> processar(Page page){
		
		Map<String, Token> m = new HashMap<String, Token>();

		List<String> regex = new ArrayList<>();
		//		regex.add("[0-9]*");
		//		regex.add(".{1}");

		List<String> tokens = this.pro.tokens(page.getBody(), Stopword.NONE, regex, true, true, false);
		page.setCountToken(tokens.size());
		
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
				value.setSize(page.getCountToken());
			}
			m.put(key, value);
		}
		
		
		return m;
	}
	
	public Attributes processarAtributos(Page page){
		
		Attributes atri = new Attributes();
		atri.setId(page.getId());
		atri.setName(page.getName());
		
		String regex = "[^0-9\\.]";
		
		String screenResolution = page.getScreenResolution().replaceAll("\\s", "").replaceAll("[x\\*\\-]", " ");
		if(!screenResolution.isEmpty()){
			String[] array = screenResolution.split(" ");
			
			double first = Double.parseDouble(array[0]);
			double second = Double.parseDouble(array[1]);
			
			if(first > second){
				atri.setScreenResolutionWidth(first);
				atri.setScreenResolutionHeight(second);
			}else{
				atri.setScreenResolutionWidth(second);
				atri.setScreenResolutionHeight(first);
			}
		}
		
		String screenSize = page.getScreenSize().replaceAll(regex, "");
		if(!screenSize.isEmpty()){
			double inch = Double.parseDouble(screenSize);
			atri.setScreenSize(inch);
		}
		
		String cameraRes = page.getCameraRes().replaceAll(regex, "");
		if(!cameraRes.isEmpty()){
			double mp = Double.parseDouble(cameraRes);
			atri.setCameraRes(mp);
		}
		
		String batteryCapacity = page.getBatteryCapacity().replaceAll(regex, "");
		if(!batteryCapacity.isEmpty()){
			double mAh = Double.parseDouble(batteryCapacity);
			atri.setBatteryCapacity(mAh);
		}
		
		String ram = page.getRam().replaceAll(regex, "");
		if(!ram.isEmpty()){
			double gbRam = Double.parseDouble(ram);
			atri.setRam(gbRam);
		}
		
		String internalMemory = page.getInternalMemory().replaceAll(regex, "");
		if(!internalMemory.isEmpty()){
			double gbRom = Double.parseDouble(internalMemory);
			atri.setInternalMemory(gbRom);
		}
		
		String processorSpeed = page.getProcessorSpeed().replaceAll(regex, "");
		if(!processorSpeed.isEmpty()){
			double ghz = Double.parseDouble(processorSpeed);
			atri.setProcessorSpeed(ghz);
		}

		String weight = page.getWeight().replaceAll(regex, "");
		if(!weight.isEmpty()){
			double g = Double.parseDouble(weight);
			double oz = 28.3495;
			if(page.getWeight().contains("oz")){
				g = g * oz;
			}
			atri.setWeight(g);
		}
		
		return atri;
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
