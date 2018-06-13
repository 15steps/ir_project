package project2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project2.model.Posting;
import project2.model.Token;

public class Postings implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private StringBuilder sb = new StringBuilder();
	private List<Posting> postings = new ArrayList<Posting>();
	private Map<String, Posting> map;
	private boolean grap;

	public Postings(boolean grap, Map<String, List<Token>> map){
		this.grap = grap;
		
		for (String key : map.keySet()){

			List<Token> value = map.get(key);
			int[] docIDs = new int[value.size()];
			int[] graps = new int[value.size()];
			int[] qtd = new int[value.size()];
			String[] docName = new String[value.size()];

			for(int i=0; i<value.size(); i++){
				Token t = value.get(i);
				docIDs[i] = t.getIdDocument();

				if(i==0){
					graps[i] = docIDs[i];
				}else{
					graps[i] = docIDs[i] - docIDs[i-1];
				}
				qtd[i] = t.getCount();
				docName[i] = t.getNameDocument();
			}

			Posting p = new Posting(key, docIDs, graps, qtd, this.grap, docName);
			this.postings.add(p);
		}
	}
	
	public Posting consult(String term){
		if (this.map == null){
			this.map = new HashMap<String, Posting>();
			for (Posting p : this.postings){
				this.map.put(p.getTerm(), p);
			}
		}
		return this.map.containsKey(term) ? this.map.get(term) : null;
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}

}


