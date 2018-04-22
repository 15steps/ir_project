package crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Links {

	private List<Link> linkList;
	private Set<String> setList;
	private int atual;
	
	public Links(List<Link> linkList, Set<String> setList, int atual) {
		this.linkList = linkList;
		this.setList = setList;
		this.atual = atual;
	}

	public Links() {
		this.linkList = new ArrayList<Link>();
		this.setList = new HashSet<String>();
		this.atual = 0;
	}

	public List<Link> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<Link> linkList) {
		this.linkList = linkList;
	}

	public Set<String> getSetList() {
		return setList;
	}

	public void setSetList(Set<String> setList) {
		this.setList = setList;
	}

	public int getAtual() {
		return atual;
	}

	public void setAtual(int atual) {
		this.atual = atual;
	}
	
	public void add(Link link, String base){
		
//		String baseAux = base.replace("htt(p|ps)://", "");
//		boolean contains = link.getLink().contains(baseAux);
//		
//		if(!this.setList.contains(link.getLink()) && contains){
//			link.setNum(this.linkList.size());
//			this.setList.add(link.getLink());
//			this.linkList.add(link);
//			System.out.println(link.getLink());
//		}
	}
	
	public Link get(int index){
		this.atual = index;
		return this.linkList.get(index);
	}
	
	public int size(){
		return this.linkList.size();
	}
	
}
