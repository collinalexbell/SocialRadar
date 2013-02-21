package twitter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Status;

public class TopWord {
	Map<String, TermInfo> words;
	List<TermInfo> sortedWords;
	TermInfoComparator termInfoComparator;
	TweetMap tweetMap;
	
	TopWord(TweetMap tweetMap){
		this.tweetMap = tweetMap;
		words = new HashMap<String, TermInfo>();
		sortedWords = new ArrayList<TermInfo>();
		termInfoComparator = new TermInfoComparator();
	}
	
	void sortWords(){
		Collections.sort(sortedWords, termInfoComparator);
	}
	
	void addWord(String word){
		if (words.containsKey(word)){
			words.get(word).increment();
			sortWords();
		}
		else{
			words.put(word, new TermInfo(1,word));
			sortedWords.add(words.get(word));
		}
	}
	
	public void addStatus(Status s){
		String tweet = s.getText();
		String words[] = tweet.split(" ");
		for(String i:words){
			addWord(i);
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


}

