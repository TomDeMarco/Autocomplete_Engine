package cs1501_p2;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;
import java.util.List;

public class UserHistory implements Dict{

    DLBNode userRoot;
    Integer countOfUserDLB;
    String userWord = "";
    int userHashSize=0;
    HashMap<String, Integer> userHash;

    //Constructor
    public UserHistory(){
        userRoot = new DLBNode('?');
        countOfUserDLB =0;
        userHash = new HashMap<String, Integer>(); // create a hashmap the size of the Dictionary DLB
    }   

    /**
     * Add a new word to the dictionary
     *
     * @param key New word to be added to the dictionary
     */
    public void add(String key){
        if(key == null){ // case of a null entry 
            return;
        }
            key = key +"^";
            DLBNode curr = userRoot.getDown(); // current node
            int i = 0;
            Integer userKeyFrequency = 0;
            if(curr == null){ // userRoot is null
              userRoot.setDown( new DLBNode (key.charAt(i)));
              addDown(userRoot.getDown(), key.substring(i+1));
              countOfUserDLB++;
              userHash.put(key.substring(0,key.length()-1), 1);
              userHashSize++;
            }else{ // userRoot is not null
              while(i<key.length()){
                  curr = searchRight(key.charAt(i), curr);
                  if(curr.getLet()== key.charAt(i)){ // There is a char there, move down list
                    curr = curr.getDown();
                    i++;
                  }else{ // add a new word
                    curr.setRight( new DLBNode(key.charAt(i)));
                    curr = curr.getRight();
                    addDown( curr,key.substring(i+1));
                    countOfUserDLB++;
                    break;
                  }
                  }
                  if(userHash.containsKey(key.substring(0,key.length()-1))){
                    userKeyFrequency = userHash.get(key.substring(0,key.length()-1));
                    userKeyFrequency++;
                    userHash.remove(key.substring(0,key.length()-1));
                }else{
                    userKeyFrequency = 1;

                }
                userHash.put(key.substring(0,key.length()-1), userKeyFrequency);
                userHashSize++;
    
              }
    }

    //Add Helper Methods----------------------------------------------------------------------------------------------------------------------------------------------------
    public DLBNode searchRight(char c, DLBNode input){ //1D loop through linkedList in the right direction
      DLBNode curr = input;
      while(curr.getRight() != null && curr.getLet() != c ){ // loop until null or finds the right char
        curr = curr.getRight();
      }
      return curr; // return the node before where the new char is placed or before the existing char

    }

    public void addDown(DLBNode curr, String key){
        for(int i =0; i<key.length(); i++){
          curr.setDown( new DLBNode(key.charAt(i)));
          curr = curr.getDown();
        }
    }

    /**
     * Check if the dictionary contains a word
     *
     * @param key Word to search the dictionary for
     *
     * @return true if key is in the dictionary, false otherwise
     */
    public boolean contains(String key){
        return userHash.containsKey(key);
    }

    /**
     * Check if a String is a valid prefix to a word in the dictionary
     *
     * @param pre Prefix to search the dictionary for
     *
     * @return true if prefix is valid, false otherwise
     */
    public boolean containsPrefix(String pre){
        int i =0;
        DLBNode curr = userRoot.getDown();
        while(i<pre.length()){ // go to the last char in word, return false if there is a mismatch along the way
          curr = searchRight(pre.charAt(i), curr);
        if(curr.getLet() == pre.charAt(i)){ 
          curr = curr.getDown();
          i++;
        }else if(curr.getLet() != pre.charAt(i)){
          return false;
        }
        }
          return true;
    }

    /**
     * Search for a word one character at a time
     *
     * @param next Next character to search for
     *
     * @return int value indicating result for current by-character search:
     *         -1: not a valid word or prefix
     *         0: valid prefix, but not a valid word
     *         1: valid word, but not a valid prefix to any other words
     *         2: both valid word and a valid prefix to other words
     */
    public int searchByChar(char next){
        userWord = userWord + next;

      if(containsPrefix(userWord) == true  && contains(userWord) == false ){
          return 0;
      }else if(contains(userWord) == true  && containsPrefix(userWord) == false){
          return 1; 
      }else if( contains(userWord) == true && containsPrefix(userWord) == true ){
          return 2;
      }else{
        return -1;
      }
    }

    /**
     * Reset the state of the current by-character search
     */
    public void resetByChar(){
        userWord = "";

    }

    /**
     * Suggest up to 5 words from the dictionary based on the current
     * by-character search. Ordering should depend on the implementation.
     * 
     * @return ArrayList<String> List of up to 5 words that are prefixed by
     *         the current by-character search
     */
    public ArrayList<String> suggest(){
      ArrayList<String> suggestions = new ArrayList<>();
      if (this.userWord.isEmpty() || containsPrefix(userWord)==false) { // Case of no entries into searchByChar
        return suggestions;
      }
    
      DLBNode curr = userRoot.getDown();
      int i = 0;
      while (i < userWord.length()) { // Traverse to the node representing the last character of the prefix
        curr = searchRight(userWord.charAt(i), curr);
        if (curr.getLet() != userWord.charAt(i)) {
            // Prefix not found, return empty suggestions
            return suggestions;
        }
        curr = curr.getDown();
        i++;
      }
      userSuggestHelper(curr, userWord, suggestions);// do normal suggestions procedure
       
      //sugggestions is now the userHistory sub trie of all words prefixed by userWord

      // Sort suggestions by word frequency in descending order and sort ties by length
      for (int j = 0; j < suggestions.size() - 1; j++) {
        for (int k = 0; k < suggestions.size() - 1 - j; k++) {
          String word1 = suggestions.get(k);
          String word2 = suggestions.get(k + 1);
          int freq1 = getFreq(word1);
          int freq2 = getFreq(word2);
          if (freq1 < freq2 || (freq1 == freq2 && word1.length() > word2.length())) {
            // Swap the words
            String temp = suggestions.get(k);
            suggestions.set(k, suggestions.get(k + 1));
            suggestions.set(k + 1, temp);
        }
      }
      }

      // Return up to top 5 suggestions
      return new ArrayList<>(suggestions.subList(0, Math.min(suggestions.size(), 5)));
    }
      
  
  private void userSuggestHelper(DLBNode curr, String currWord, ArrayList<String> suggestions){
          if (curr == null || suggestions.size() >= 5) {
              return;
          }

          if (curr.getLet() == '^') { // If it's the end of the word
            suggestions.add(currWord); // Add the word to suggestions
          }

        // Recursively explore down and right
        userSuggestHelper(curr.getDown(), currWord + curr.getLet(), suggestions); // Go down
        userSuggestHelper(curr.getRight(), currWord, suggestions); // Go right
      }

    /**
     * List all of the words currently stored in the dictionary
     * 
     * @return ArrayList<String> List of all valid words in the dictionary
     */
    public ArrayList<String> traverse(){
        ArrayList<String> words = new ArrayList<>();
        DLBNode curr = userRoot.getDown();
        if(curr == null){
          return words;
        }
        userTraverseHelper(curr, "",words);
        return words;
      }
  
      //Traverse helper method: recursively goes through every word in the DLB from root.getDown()
       private void userTraverseHelper(DLBNode curr, String currWord, ArrayList<String> words){
        if(curr == null){
          return;
        }
         if(curr.getLet() == '^'){ // check whether prefix or word
           words.add(currWord); //if word add to words
          }     
          userTraverseHelper(curr.getDown(), currWord + curr.getLet(), words); // go down
          userTraverseHelper(curr.getRight(), currWord, words); // go right
       }

    /**
     * Count the number of words in the dictionary
     *
     * @return int, the number of (distinct) words in the dictionary
     */
    public int count(){
        return countOfUserDLB;
    }

    //Map Helper Methods
    public HashMap<String,Integer> getMap(){
      return userHash;
    }

    public int getFreq(String key){
      if(userHash.containsKey(key)){
        return userHash.get(key);
      }
      else{
        return 0;
      }

    }
}

