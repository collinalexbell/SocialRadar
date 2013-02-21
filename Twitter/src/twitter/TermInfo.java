package twitter;

public class TermInfo {
	int frequency;
	String term;
	
	TermInfo(int fq, String t){
		frequency = fq;
		term = t;
	}
	
	public void increment(){
		frequency++;
	}
}
