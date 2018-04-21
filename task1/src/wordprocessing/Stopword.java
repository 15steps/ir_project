package wordprocessing;

import java.util.HashSet;
import java.util.Set;

public enum Stopword {
	
	NONE(0, "NONE", new String[]{}),

	PORTUGUESE(1, "PORTUGUESE" , new String[]{"de", "o", "que", "do", "da", "em", "um", "para", "e", "com", "nao", 
			"uma", "os", "no", "se", "na", "por", "mais", "as", "dos", "como", "mas", "foi", "ao", "ele", "das", "tem",
			"a", "seu", "sua", "ou", "ser", "quando", "ha", "nos", "ja", "esta", "eu", "tambem", "so", "pelo", "pela", 
			"ate", "isso", "ela", "entre", "era", "depois", "sem", "mesmo", "aos", "ter", "seus", "quem", "nas", "me", 
			"esse", "eles", "estao", "voce", "tinha", "foram", "essa", "num", "nem", "suas", "meu", "as", "minha", 
			"tem", "numa", "pelos", "elas", "havia", "seja", "qual", "sera", "nos", "tenho", "lhe", "deles", "essas", 
			"esses", "pelas", "este", "fosse", "dele", "tu", "te", "voces", "vos", "lhes", "meus", "minhas", "teu", 
			"tua", "teus", "tuas", "nosso", "nossa", "nossos", "nossas", "dela", "delas", "esta", "estes", "estas", 
			"aquele", "aquela", "aqueles", "aquelas", "isto", "aquilo", "estou", "esta", "estamos", "estao", "estive", 
			"esteve", "estivemos", "estiveram", "estava", "estavamos", "estavam", "estivera", "estiveramos", "esteja", 
			"estejamos", "estejam", "estivesse", "estivessemos", "estivessem", "estiver", "estivermos", "estiverem", 
			"hei", "ha", "havemos", "hao", "houve", "houvemos", "houveram", "houvera", "houveramos", "haja", "hajamos", 
			"hajam", "houvesse", "houvessemos", "houvessem", "houver", "houvermos", "houverem", "houverei", "houvera", 
			"houveremos", "houverao", "houveria", "houveriamos", "houveriam", "sou", "somos", "sao", "era", "eramos", 
			"eram", "fui", "foi", "fomos", "foram", "fora", "foramos", "seja", "sejamos", "sejam", "fosse", "fossemos", 
			"fossem", "for", "formos", "forem", "serei", "sera", "seremos", "serao", "seria", "seriamos", "seriam", 
			"tenho", "tem", "temos", "tem", "tinha", "tinhamos", "tinham", "tive", "teve", "tivemos", "tiveram", "tivera", 
			"tiveramos", "tenha", "tenhamos", "tenham", "tivesse", "tivessemos", "tivessem", "tiver", "tivermos",
			"tiverem", "terei", "tera", "teremos", "terao", "teria", "teriamos", "teriam", "faz", "vai", "vem"}),
	
	ENGLISH(2, "ENGLISH", new String[]{"a", "about", "above", "above", "across", "after", "afterwards", "again", "against",
			"all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", 
			"amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", 
			"as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", 
			"behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", 
			"call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", 
			"done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", 
			"enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", 
			"fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", 
			"front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", 
			"hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", 
			"hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last",
			"latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine",
			"more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", 
			"never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", 
			"nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", 
			"our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", 
			"same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", 
			"since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", 
			"somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", 
			"then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", 
			"thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", 
			"together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", 
			"us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", 
			"whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither",
			"who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", 
			"your", "yours", "yourself", "yourselves", "the"});
	
	private int code;
	private String description;
	private String[] stopword;
	private Set<String> stopwordSet;
	
	private Stopword(int code, String description, String[] stopword) {
		this.code = code;
		this.description = description;
		this.stopword = stopword;
		this.stopwordSet = new HashSet<String>();
		
		for(String s : stopword){
			this.stopwordSet.add(s);
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getStopword() {
		return stopword;
	}

	public void setStopword(String[] stopword) {
		this.stopword = stopword;
		this.stopwordSet.clear();
		
		for(String s : stopword){
			this.stopwordSet.add(s);
		}
	}

	public Set<String> getStopwordSet() {
		return stopwordSet;
	}
	
}
