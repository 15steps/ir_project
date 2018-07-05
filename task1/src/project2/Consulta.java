package project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.IntStream;

import project2.model.Posting;
import wordprocessing.Stopword;
import wordprocessing.WordProcessing;

public class Consulta {

	private Postings postings;
	private WordProcessing pro = WordProcessing.getInstance();

	public Consulta(Postings postings) {
		this.postings = postings;
	}

	public List<Posting> search(String query, boolean idf) {

		List<String> regex = new ArrayList<>();
		// regex.add("[0-9]*");
		// regex.add(".{1}");
		List<String> tokens = this.pro.tokens(query, Stopword.NONE, regex, true, true, false);

		List<Posting> list = new ArrayList<>();

		for (String s : tokens) {
			Posting p = this.postings.consult(s);
			if (p != null) {
				list.add(p);
			}
		}

		return list;
	}
	
	public List<Posting> searchQuartis(String query, boolean idf) {

		List<String> regex = new ArrayList<>();
		// regex.add("[0-9]*");
		// regex.add(".{1}");
		List<String> tokens = this.pro.tokens(query, Stopword.NONE, regex, true, true, false);
		tokens.clear();
		tokens.add(query);

		List<Posting> list = new ArrayList<>();

		for (String s : tokens) {
			Posting p = this.postings.consult(s);
			if (p != null) {
				list.add(p);
			}
		}
		
		return list;
	}

	public Map<Integer, Double> cosineScore(String query, boolean useIdf, int k) {
		List<String> tokens = this.pro.tokens(query, Stopword.NONE, Arrays.asList(""), true, true, false);
		final int N = this.postings.getQtd();
		double[] scores = new double[N];

		for (String t : tokens) {
			Posting p = postings.getMap().get(t);
			double idf = useIdf ? Math.log(N / p.getDf()) : p.getDf();

			for (int i = 0; i < p.getDocIDs().length; ++i) {
				int docID = p.getDocIDs()[i];
				int freq = p.getQtd()[i];
				scores[docID] = scores[docID] + (freq * idf);
			}
			
			for (int i = 0; i < p.getDocIDs().length; ++i) {
				int docID = p.getDocIDs()[i];
				scores[docID] = scores[docID] / p.getSize()[i];
			}
		}
		
		Map<Integer, Double> _scores = new HashMap<>();
		for (int i = 0; i < N; ++i) {
			_scores.put(i, scores[i]);
		}
		
		final boolean DESC = false;
		Map<Integer, Double> ordScores = sortByComparator(_scores, DESC);
		
		final Map<Integer, Double> topScores = new HashMap<>(); 
		List<Integer> keySet = new ArrayList<>();
		keySet.addAll(ordScores.keySet());
		List<Double> values = new ArrayList<>();
		values.addAll(ordScores.values());
		
		IntStream.range(0, k)
			.forEach(i -> {
				topScores.put(keySet.get(i), values.get(i));
			});
		
		return sortByComparator(topScores, DESC);
	}
	
	/**
	 * @param r1 Ranqueamento r1
	 * @param r2 Ranqueamento r2
	 * @return Correlação entre ranqueamentos
	 * @throws Exception Lança exceção se ranqueamentos têm tamanhos diferentes
	 */
	public double queryCorrelation(Map<Integer, Double> r1, Map<Integer, Double> r2) throws Exception {
		if (r1.size() != r2.size()) {
			throw new Exception("Tamanho dos ranqueamentos divergem!");
		}
		
		List<Integer> keys = new ArrayList<>();
		keys.addAll(r1.keySet());
//		Integer[] keys = (Integer[]) r1.keySet().toArray();
		final int k = r1.size();
		
		double sum = 0;
		for (Integer key : keys) {
			double s1 = r1.get(key) != null ? r1.get(key) : 0;
			double s2 = r2.get(key) != null ? r2.get(key) : 0;
			sum += Math.pow(s1 - s2, 2);
		}
		
		return 1 - (6 * sum) / (k * (Math.pow(k, 2) - 1));
	}

	private static Map<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap, final boolean order) {

		List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<Integer, Double>>() {
			public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
				if (order) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
		for (Entry<Integer, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
}
