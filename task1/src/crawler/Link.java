package crawler;

import java.util.ArrayList;
import java.util.List;

import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Link {

	private String link;
	private String description;
	private String base;
	private String baseAux;
	private String complemento;
	private List<String> tokensComplemento;
	private List<String> tokensDescription;
	
	public Link(String link, String description){
		
		this.link = link;
		int index = link.indexOf(".com") + 4;
		this.base = link.substring(0, index);
		int index2 = base.indexOf("://") + 3;
		this.baseAux = base.substring(index2);
		this.complemento = link.substring(index);
		this.description = description;
		
		WordProcessing pro = new WordProcessing();
		Stopword stopword = Stopword.NONE;
		this.tokensComplemento = pro.tokens(this.complemento, stopword);
		this.tokensDescription = pro.tokens(this.description, stopword);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getBaseAux() {
		return baseAux;
	}

	public void setBaseAux(String baseAux) {
		this.baseAux = baseAux;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public List<String> getTokensComplemento() {
		return tokensComplemento;
	}

	public void setTokensComplemento(List<String> tokensComplemento) {
		this.tokensComplemento = tokensComplemento;
	}

	public List<String> getTokensDescription() {
		return tokensDescription;
	}

	public void setTokensDescription(List<String> tokensDescription) {
		this.tokensDescription = tokensDescription;
	}
	
	public List<String> getAllTokens(){
		List<String> tokens = new ArrayList<String>();
		tokens.addAll(tokensComplemento);
		tokens.addAll(tokensDescription);
		return tokens;
	}
	
	public List<String> getAllTokens2(){
		List<String> tokens = new ArrayList<String>();
		tokens.addAll(tokensComplemento);
		tokens.addAll(tokensDescription);
		tokens.addAll(tokensDescription);
		return tokens;
	}

	@Override
	public String toString() {
		return "Link [link=" + link + ", description=" + description + ", base=" + base + ", baseAux=" + baseAux
				+ ", complemento=" + complemento + ", tokens=" + tokensComplemento + "]";
	}

}
