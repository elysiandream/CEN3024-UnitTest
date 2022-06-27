package application;

class WordStat implements Comparable<WordStat> {
	 public String word;
	 public int count;
	 
	 public int compareTo(WordStat ws) {
		 if (count == ws.count) {
			 return 0;
		 } else if (count > ws.count) {
			 return 1;
		 } else {
			 return -1;
		 }
	 }

	 public void print() {
		 System.out.println(getPrintable());
	 }
	 
	 public String getPrintable() {
		 return String.format("Word: %-12sFrequency: %d", "\""+ word+ "\"", count);
	 }
}