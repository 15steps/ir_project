package project2;

import java.util.ArrayList;
import java.util.List;

import project2.model.Posting;
import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Consulta {

	private Postings postings;
	private WordProcessing pro = WordProcessing.getInstance();

	public Consulta(Postings postings) {
		this.postings = postings;
	}
	
	public List<Posting> search(String query){

		List<String> regex = new ArrayList<>();
		//		regex.add("[0-9]*");
		//		regex.add(".{1}");
		List<String> tokens = this.pro.tokens(query, Stopword.NONE, regex, true, true, false);
		
		List<Posting> list = new ArrayList<>();
		
		for(String s : tokens){
			Posting p = this.postings.consult(s);
			if(p != null){
				list.add(p);
			}
		}
		
		return list;
	}
}
