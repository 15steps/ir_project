package project2.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "tokens")
@XmlType(propOrder = { "token", "idDocument", "count", "position", "nameDocument", "size"})
public class Token implements Comparable<Token>{

	private String text;
	private int idDocument;
	private int count;
	private List<Integer> position = new ArrayList<>();
	private String nameDocument;
	private int size;
	
	public Token(String text, int idDocument, int count, List<Integer> position, String nameDocument) {
		this.text = text;
		this.idDocument = idDocument;
		this.count = count;
		this.position = position;
		this.nameDocument = nameDocument;
	}
	
	public Token() {
		
	}
	
	public String getToken() {
		return text;
	}
	
	@XmlElement(name="text")
	public void setToken(String token) {
		this.text = token;
	}
	public int getIdDocument() {
		return idDocument;
	}
	
	@XmlElement(name="id_document")
	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}
	public int getCount() {
		return count;
	}
	
	@XmlElement(name="count")
	public void setCount(int count) {
		this.count = count;
	}
	public List<Integer> getPosition() {
		return position;
	}
	
	@XmlElementWrapper(name="position_list")
	@XmlElement(name="position")
	public void setPosition(List<Integer> position) {
		this.position = position;
	}
	public String getNameDocument() {
		return nameDocument;
	}
	
	@XmlElement(name="name_document")
	public void setNameDocument(String nameDocument) {
		this.nameDocument = nameDocument;
	}

	public void addPosition(int position){
		this.position.add(position);
	}
	public void incrementCount(){
		this.count++;
	}
	
	private String printList(){
		StringBuilder sb = new StringBuilder();
		for(Integer i : this.position){
			sb.append(i);
			sb.append(", ");
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "Token [token=" + text + ", idDocument=" + idDocument + ", count=" + count + ", position=[" + printList() + "]" + "]";
	}
	
	@Override
	public int compareTo(Token o) {
		if (this.count > o.count){
			return -1;
		}
		if (this.count < o.count){
			return 1;
		}
		return 0;
	}

	public int getSize() {
		return size;
	}

	@Transient
	public void setSize(int size) {
		this.size = size;
	}
	
	
}
