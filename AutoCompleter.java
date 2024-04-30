/**
 * Autocomplete specification interface for CS1501 Project 2
 * @author    Dr. Farnan
 */
package cs1501_p2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;
import java.io.IOException;
import java.util.Map;


public class AutoCompleter implements AutoComplete_Inter {

    public DLB dictionary;
    public UserHistory userHistory;

    public AutoCompleter(String aDictionary, String aUserHistory){ 
        
        userHistory = new UserHistory();
        dictionary = new DLB();
        String line = "";

        //Dictionary
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(aDictionary));
            while((line = fileReader.readLine()) != null){ // read file untill there is no more input
                dictionary.add(line);
            }
            fileReader.close();
        }catch(IOException exception){
            System.out.println("Something bad happened: " + exception);
        }
        //UserHistory
        try{
            BufferedReader fileReader = new BufferedReader(new FileReader(aUserHistory));
            line = "";
            while((line = fileReader.readLine()) != null){ // read file untill there is no more input
                userHistory.add(line);
            }
            fileReader.close();
        }catch(IOException exception){ //incase file is invalid
            System.out.println("Something bad happened: " + exception);
        }
    }

    //Overloaded Constructor
    public AutoCompleter(String aDictionary){
        String line = "";
        userHistory = new UserHistory();
        dictionary = new DLB();
               //Dictionary
               try{
                BufferedReader fileReader = new BufferedReader(new FileReader(aDictionary));
                while((line = fileReader.readLine()) != null){ // read file untill there is no more input
                    dictionary.add(line);
                }
                fileReader.close();
            }catch(IOException exception){ //incase file is invalid
                System.out.println("Something bad happened: " + exception);
            }
    }

    /**
     * Produce up to 5 suggestions based on the current word the user has
     * entered These suggestions should be pulled first from the user history
     * dictionary then from the initial dictionary. Any words pulled from user
     * history should be ordered by frequency of use. Any words pulled from
     * the initial dictionary should be in ascending order by their character
     * value ("ASCIIbetical" order).
     *
     * @param next char the user just entered
     *
     * @return ArrayList<String> List of up to 5 words prefixed by cur
     */
    public ArrayList<String> nextChar(char next){
        if(userHistory==null||userHistory.count()<=0){
            dictionary.searchByChar(next);
            return dictionary.suggest();
        }
        userHistory.searchByChar(next);
        dictionary.searchByChar(next);
        ArrayList<String> finalSuggestions = new ArrayList<>();
        ArrayList<String> userPredictions = userHistory.suggest();
        ArrayList<String> dictPredictions = dictionary.suggest();

        // Add user predictions from the sorted list
        int userSuggestionsAdded = 0;
        for (String userPrediction : userPredictions) {
            if (userSuggestionsAdded >= 5) {
            break;
            }
        finalSuggestions.add(userPrediction);
        userSuggestionsAdded++;
    }

    // Fill remaining slots with dictionary predictions in ascending order
    int i = 0;
    while (finalSuggestions.size() < 5 && i < dictPredictions.size()) {
        String suggestion = dictPredictions.get(i++);
        if (!finalSuggestions.contains(suggestion)) {
            finalSuggestions.add(suggestion);
        }
    }

        return finalSuggestions;
    }


    /**
     * Process the user having selected the current word
     *
     * @param cur String representing the text the user has entered so far
     */
    public void finishWord(String cur){
        userHistory.add(cur);
        userHistory.resetByChar();
        dictionary.resetByChar();

    }

    /**
     * Save the state of the user history to a file
     *
     * @param fname String filename to write history state to
     */
    public void saveUserHistory(String fname){
        HashMap<String,Integer> map = userHistory.getMap();
        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fname));
            for(Map.Entry<String,Integer> entries: map.entrySet()){
                String word = entries.getKey();
                int count = entries.getValue();

                for(int i=0; i< userHistory.userHashSize;i++){
                    fileWriter.write(word);
                    fileWriter.newLine();
                }
            }
            fileWriter.close();
        }
        catch(IOException e){
            System.out.println("Cannot Write to File");
        }
    }
}

