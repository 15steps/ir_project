package project2;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import project2.model.Page;
import project2.model.Posting;

public class Main {

	public static void main(String[] args){

		Util util = new Util();
		String pathSerial = "serial.txt";
		
		/*
		String path = "xml/";
		List<Page> pages = util.ler(path);
		Postings p = util.processarPages(pages);
		
		try {
			util.serialization(pathSerial, p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**/
				
		//*
		Postings postings = null;
		try {
			postings = (Postings) util.deSerialization(pathSerial);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		Consulta consulta = new Consulta(postings);
		String query = "";
		
		Scanner scanner = new Scanner(System.in);
		do{
			System.out.println("Escreva uma consulta ou digite (SAIR)");
			query = scanner.nextLine();
			
			List<Posting> list = consulta.search(query);
			for(Posting po : list){
				System.out.println(po.toString());
			}
			System.out.println("______________________");
		}while(!query.equals("SAIR"));
		scanner.close();
		/**/
	}

	
}
