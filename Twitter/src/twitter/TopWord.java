package twitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import twitter4j.Status;

public class TopWord {
	Map<String, TermInfo> words;
	List<TermInfo> sortedWords;
	TermInfoComparator termInfoComparator;
	TweetMap tweetMap;
	Set<String> stopWords;
	
	public static void main(String args[]){
		TopWord mytopword = new TopWord();
		mytopword.readStopWords("Stopwords.tbl");
	}
	
	private TopWord(){
		words = new HashMap<String, TermInfo>();
		sortedWords = new ArrayList<TermInfo>();
		termInfoComparator = new TermInfoComparator();
		stopWords = new HashSet<String>();
	}
	
	TopWord(TweetMap tweetMap){
		stopWords = new HashSet<String>();
		this.tweetMap = tweetMap;
		words = new HashMap<String, TermInfo>();
		sortedWords = new ArrayList<TermInfo>();
		termInfoComparator = new TermInfoComparator();
		readStopWords("Stopwords.tbl");
		readStopWords("stop-words-spanish.txt");
		readStopWords("stop-words-portugese.txt");
		readStopWords("punctiation-stopwords.txt");
	}
	
	void sortWords(){
		Collections.sort(sortedWords, termInfoComparator);
	}
	
	void addWord(String word){
		word = word.toLowerCase();
		if (!stopWords.contains(word) && word.length() > 3){
			if (words.containsKey(word)){
				words.get(word).increment();
				sortWords();
			}
			else{
				words.put(word, new TermInfo(1,word));
				sortedWords.add(words.get(word));
			}
		}
	}

	public void addStatus(Status s){
		Set<String> duplicates = new HashSet<String>();
		String tweet = s.getText();
		String words[] = tweet.split(" ");
		for(String i:words){
			if(!duplicates.contains(i)){
				addWord(i);
				duplicates.add(i);
			}
		}
	}
	
	public void printTopWords(int number){
			System.out.println("-----------------------------");
			if(number <= sortedWords.size()){
				for (int n = 0; n < number; n++){
					TermInfo i = sortedWords.get(n);
					System.out.println(i.term + ": " + i.frequency);
				}
			}
			else{
				for (int n = 0; n < sortedWords.size(); n++){
					TermInfo i = sortedWords.get(n);
					System.out.println(i.term + ": " + i.frequency);
				}
			}
			System.out.println("-----------------------------");
	}
	
	public String stringTopWords(int number){
		String rv = new String();
		if(number <= sortedWords.size()){
			for (int n = 0; n < number; n++){
				TermInfo i = sortedWords.get(n);
				rv += (i.term + ": " + i.frequency +"\n");
			}
		}
		else{
			for (int n = 0; n < sortedWords.size(); n++){
				TermInfo i = sortedWords.get(n);
				rv += (i.term + ": " + i.frequency +"\n");
			}
		}	
		return rv;
	}
	
	public void readStopWords(String filename){
		BufferedReader br;
		String line;
		try {
			br = new BufferedReader(new FileReader(filename));
			while ((line = br.readLine())!=null){
				stopWords.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to open stopword file. Continuing without stopwords");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to read word");
		}
		for (String s: stopWords){
			System.out.println(s);
		}
	}


}

