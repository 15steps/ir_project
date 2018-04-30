package crawler;

import java.util.List;

import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Link {

	private String link;
	private String description;
	private String base;
	private String baseAux;
	private String complemento;
	private List<String> tokens;
	private String tokensStr;
	
	public Link(String link, String description){
		this.description = description;
		this.start(link);
	}
	
	public void start(String link){
		this.link = link;
		int index = link.indexOf(".com") + 4;
		this.base = link.substring(0, index);
		int index2 = base.indexOf("://") + 3;
		this.baseAux = base.substring(index2);
		this.complemento = link.substring(index);
		
		WordProcessing pro = new WordProcessing();
		Stopword stopword = Stopword.NONE;
		String texto = this.complemento + " " + this.description;
		this.tokens = pro.tokens(texto, stopword, true, true, true);
		this.tokensStr = pro.wordProcessing(texto, stopword, true, true, true);
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

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public String getTokensStr() {
		return tokensStr;
	}

	public void setTokensStr(String tokensStr) {
		this.tokensStr = tokensStr;
	}

	@Override
	public String toString() {
		return "Link [link=" + link + ", description=" + description + ", base=" + base + ", baseAux=" + baseAux
				+ ", complemento=" + complemento + ", tokens=" + tokens + "]";
	}

}
