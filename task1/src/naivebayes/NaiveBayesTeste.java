package naivebayes;

public class NaiveBayesTeste {

	public static void main(String[] args) {
		
		NaiveBayes naiveBayes = new NaiveBayes();		
		
		naiveBayes.train("You need to buy some Viagra".split("\\s+"), Classe.NEGATIVO);
		naiveBayes.train("This is not spam, just a letter to Bob.".split("\\s+"), Classe.POSITIVO);
		naiveBayes.train("Hey Oasic, Do you offer consulting?".split("\\s+"), Classe.POSITIVO);
		naiveBayes.train("You should buy this stock".split("\\s+"), Classe.NEGATIVO);
		
		String[] tokens = "Now is the time to buy Viagra cheaply and discreetly".split("\\s+");
		
		System.out.println(naiveBayes.classify(tokens));
		
	}

}
