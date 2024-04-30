/**
 * A driver for CS1501 Project 2
 * @author    Dr. Farnan
 */
package cs1501_p2;

import java.io.File;
import java.util.ArrayList;

public class App {

    public static void main(String[] args){

    System.out.println("AddDown Method Test-----------------------------------------------------------");
    // Test for add down
    DLB test = new DLB();
    test.add("Java");
    DLBNode curr0 = test.root.getDown();
    while(curr0.getDown() != null){
        System.out.println(curr0.getLet());
        curr0 = curr0.getDown();
    }
    System.out.println(curr0.getLet());

    //Test searchRight and AddDown
    System.err.println("Testing SearchRight Method(add new word)--------------------------------------------------------------------------------------");
    test.add("Sucks");
   DLBNode curr1 = test.root.getDown().getRight();
       while(curr1 != null){
        System.out.println(curr1.getLet());
        curr1 = curr1.getDown();
    }


    //System.out.println(curr.getLet());

    // Test add of words with new letters
    System.out.println("Testing Add of words with no prefixes-------------------------------------------------");
    DLB test2 = new DLB();
    test2.add("Java");
    test2.add("Alot");
    test2.add("Of");
    test2.add("Wacky");
    test2.add("Things");
    System.out.print("Should be: AOWT");
    DLBNode curr= test2.root.getDown();
    System.out.println(test2.root.getDown());
    while(curr != null){
        System.out.println(curr.getLet());
        curr = curr.getRight();
    }

    //test add of words that have prefixes in the trie
    System.out.println("Testing Add of words with prefixes-------------------------------------------------");
    DLB test3 = new DLB();
    DLBNode curr4 = test3.root;
    test3.add("Java");
    //System.out.println("Print root.down should be J: " + curr4.getDown().getLet());
    curr4 =curr4.getDown();
    while(curr4 != null){
        System.out.println(curr4.getLet());
        curr4 = curr4.getDown();
    }
    test3.add("Alot");
    curr4 = test3.root.getDown().getRight();
    //System.out.println("Print should be A: " + curr4.getDown().getRight().getLet());
    while(curr4 != null){
        System.out.println(curr4.getLet());
        curr4 = curr4.getDown();
    }
    test3.add("Just");
    curr4 = test3.root.getDown().getDown().getRight();
    //System.out.println("Print should be u: " + curr4.getDown().getDown().getRight().getLet());
    while(curr4 != null){
        System.out.println(curr4.getLet());
        curr4 = curr4.getDown();
    }
    test3.add("Add");
    curr4 = test3.root;
    //System.out.println("Print should be d: " + curr4.getDown().getRight().getDown().getRight().getLet());
    curr4 = curr4.getDown().getRight().getDown().getRight();
    while(curr4 != null){
        System.out.println(curr4.getLet());
        curr4 = curr4.getDown();
    }


    // // Testing Contains
     System.out.println("Testing Contains----------------------------------------------------------------------");
     DLB test4 = new DLB();
     test4.add("Java");
     test4.add("Alot");
     test4.add("Beans");
     test4.add("Just");
     test4.add("Add");
     System.out.println("Testing Java should be True: " +test4.contains("Java"));
     System.out.println("Testing Alot should be True: " +test4.contains("Alot"));
     System.out.println("Testing Of should be False: " +test4.contains("Of"));
     System.out.println("Testing Beans should be True: " +test4.contains("Beans"));
     System.out.println("Testing Just should be True: " +test4.contains("Just"));
     System.out.println("Testing Add should be True: " +test4.contains("Add"));
     System.out.println("Testing January should be False: " +test4.contains("January"));
     System.out.println("Testing Annual should be False: " +test4.contains("Annual"));
     System.out.println("Testing silly should be False: " +test4.contains("silly"));

     System.out.println("Testing containsPrefix-------------------------------------------");
     DLB test5 = new DLB();
     test5.add("in");
     test5.add("inside");
     test5.add("inner");
     test5.add("innert");
     test5.add("out");
     test5.add("outer");
     test5.add("outside");
     test5.add("as");
     System.out.println("Testing in should be true: " + test5.containsPrefix("in"));
     System.out.println("Testing out should be true: " + test5.containsPrefix("out"));
     System.out.println("Testing im should be false: " + test5.containsPrefix("im"));
     System.out.println("Testing oz should be false: " + test5.containsPrefix("oz"));

     System.out.println("Testing searchByChar---------------------------------------------------------------------");
     System.out.println("Disclaimer: Never given case of 1");
     System.out.println("Testing i should be 0: "+test5.searchByChar('i'));
     System.out.println("Testing n should be 2: "+test5.searchByChar('n'));
     System.out.println("Testing n should be 0: "+test5.searchByChar('n'));
     System.out.println("Testing e should be 0: "+test5.searchByChar('e'));
     System.out.println("Testing r should be 2: "+test5.searchByChar('r'));
     System.out.println("Testing ! should be -1: "+test5.searchByChar('!'));
     System.out.println("Testing resetByChar: before clear: "+ test5.word);
     test5.resetByChar();
     System.out.println("Testing resetByChar: after clear: "+ test5.word);
     System.out.println("Testing a should be 0: "+test5.searchByChar('a'));
     System.out.println("Testing s should be 2: "+test5.searchByChar('s'));
     test5.resetByChar();
     System.out.println("Testing o should be 0: "+test5.searchByChar('o'));
     System.out.println("Testing u should be 0: "+test5.searchByChar('u'));
     System.out.println("Testing t should be 2: "+test5.searchByChar('t'));
     System.out.println("Testing e should be 0: "+test5.searchByChar('e'));

     System.out.println("Testing Traverse Method-------------------------------------------------");
     System.out.println(test.traverse());
     System.out.println(test2.traverse());
     System.out.println(test3.traverse());
     System.out.println(test4.traverse());
     System.out.println(test5.traverse());

     System.out.println("Testing Suggest Method-------------------------------------------------");
     DLB test6 = new DLB();
     test6.add("A");
     test6.add("a");
     test6.add("definite");
     test6.add("dict");
     test6.add("dictionary");
     test6.add("this");
     test6.add("is");
     test6.resetByChar();
     test6.searchByChar('d');
     System.out.println(test6.suggest());
    System.out.println("Testing Suggest Part 2--------------------------------------------------------------------------------------");
    DLB test20= new DLB();
    test20.add("she");
    test20.add("shell");
    test20.add("shells");
    test20.add("shelled");
    test20.searchByChar('s');
    test20.searchByChar('h');
    System.out.println(test20.word);
    System.out.println("Printing Suggest should be {she,shell,shells,shelled}: "+ test20.suggest());

     System.out.println("User History Tests------------------------------------------------------------------------------------------------------------------------------------------------------");
     System.out.println("Testing userConstructor:");
     System.out.println();
     UserHistory test10= new UserHistory();
     System.out.println("Testing normal userAdd:");
     test10.add("A");
     test10.add("a");
     test10.add("definite");
     test10.add("dict");
     test10.add("dictionary");
     test10.add("this");
     test10.add("is");
     System.out.println("UserHashSize should be 7: " + test10.userHashSize);
     System.out.println("User History add a duplicate and change count Tests(also getFreq())------------------------------------------------------------------------------------------------------------------------------------------------------");
     test10.add("is");
    System.out.println("Printing count of is, should be 2: " + test10.getFreq("is"));
    System.out.println("Printing count of a, should be 1: " + test10.getFreq("a"));
    System.out.println("Printing count of Nice, should be 0: " + test10.getFreq("Nice"));
    System.out.println("Contains this? Should be true: " + test10.contains("this"));
    System.out.println("Contains isawesome? Should be false: "+ test10.contains("isawesome"));

    System.out.println("Testing Suggest--------------------------------------------------------------------------------------");
    test10.resetByChar();
    test10.searchByChar('d');
    System.out.println(test10.userWord);
    System.out.println("Printing Suggest should be {definite,dict,dictionary}: "+ test10.suggest());


    System.out.println("Testing AutoCompleter-------------------------------------------------------------------------------");
    String eng_dict_fname = "/workspaces/project2-Tomd445/app/src/test/resources/dictionary.txt";
    String uhist_state_fname = "/workspaces/project2-Tomd445/app/src/test/resources/userHistory.txt";

    System.out.println("Testing Basic Constructor-------------------------------------------------------------------------------");
    AutoCompleter ac;
    File check = new File(uhist_state_fname);
    if (check.exists()) {
        ac = new AutoCompleter(eng_dict_fname, uhist_state_fname);
    } else {
        ac = new AutoCompleter(eng_dict_fname);
    }
    System.out.println("Should print the test dictionary and nothing for user history");
    System.out.println("Test Dictionary: "+ac.dictionary.traverse());
    System.out.println("UserHistory: "+ac.userHistory.traverse());
    System.out.println("Testing AC.nextChar()-------------------------------------------------------------------------------");

    ArrayList<String> sugs = ac.nextChar('d');
    String[] expected = new String[] { "definite", "dict", "dictionary" };
        for (int i = 0; i < expected.length; i++) {
                       System.out.println("(Initial) Expected suggestion " + expected[i] + " got " + sugs.get(i));
            }

    System.out.println("Testing FinishWord:-----------------------------------------------------------------");
            ac.finishWord("dip");
            ac.finishWord("dip");
            sugs = ac.nextChar('d');
            expected = new String[] { "dip", "dictionary", "definite", "dict" };
            for (int i = 0; i < expected.length; i++) {
                        System.out.println("(Finished) Expected suggestion " + expected[i] + " got " + sugs.get(i));
            }

    System.out.println("Testing priority implementation of user suggest/nextChar-----------------------------------------------------");

            ac.finishWord("dip");
            ac.finishWord("dip");
            sugs = ac.nextChar('d');
            expected = new String[] { "dip", "dictionary", "definite", "dict" };
            for (int i = 0; i < expected.length; i++) {
                     System.out.println( "(finish dip x2) Expected suggestion " + expected[i] + " got " + sugs.get(i));
            }
    System.out.println("Testing saveUserHistory-----------------------------------------------------");

         String dict_fname = "/workspaces/project2-Tomd445/app/src/test/resources/dictionary.txt";
         uhist_state_fname = "/workspaces/project2-Tomd445/app/src/test/resources/userHistory.txt";

         ac = new AutoCompleter(dict_fname);

         sugs = ac.nextChar('d');
         expected = new String[] { "definite", "dict", "dictionary" };
        
        for (int i = 0; i < expected.length; i++) {
            System.out.println("(Initial) Expected suggestion " + expected[i] + " got " + sugs.get(i));
        }

        ac.finishWord("dictionary");
        sugs = ac.nextChar('d');
        expected = new String[] { "dictionary", "definite", "dict" };
        for (int i = 0; i < expected.length; i++) {
            System.out.println( "(finish dictionary) Expected suggestion " + expected[i] + " got " + sugs.get(i));
        }

        ac.finishWord("dip");
        ac.finishWord("dip");
        System.out.println("traversal1: \n"+ac.userHistory.traverse());
        System.out.println("dip1: \n"+ac.userHistory.getFreq("dip"));
        System.out.println("dictionary1: \n"+ac.userHistory.getFreq("dictionary"));
        sugs = ac.nextChar('d');
        expected = new String[] { "dip", "dictionary", "definite", "dict" };
        for (int i = 0; i < expected.length; i++) {
            System.out.println("(finish dip x2) Expected suggestion " + expected[i] + " got " + sugs.get(i));
        }
        
        ac.saveUserHistory(uhist_state_fname);

        ac = new AutoCompleter(dict_fname, uhist_state_fname);
        
        sugs = ac.nextChar('d');

        expected = new String[] { "dip", "dictionary", "definite", "dict" };

        System.out.println("traversal: \n"+ac.userHistory.traverse());
        System.out.println("dip: \n"+ac.userHistory.getFreq("dip"));
        System.out.println("dictionary: \n"+ac.userHistory.getFreq("dictionary"));
        for (int i = 0; i < expected.length; i++) {
             System.out.println("(reloaded state) Expected suggestion " + expected[i] + " got " + sugs.get(i));
        }
        



    }

    



    //     printPredictions(ac, 't');
    //     printPredictions(ac, 'h');
    //     printPredictions(ac, 'e');
    //     printPredictions(ac, 'r');
    //     printPredictions(ac, 'e');

    //     String word = "thereabout";
    //     System.out.printf("Selected: %s\n\n", word);
    //     ac.finishWord(word);

    //     printPredictions(ac, 't');
    //     printPredictions(ac, 'h');
    //     printPredictions(ac, 'e');
    //     printPredictions(ac, 'r');
    //     printPredictions(ac, 'e');

    //     ac.saveUserHistory(uhist_state_fname);
    // }

    // private static void printPredictions(AutoCompleter ac, char next) {
    //     System.out.printf("Entered: %c\n", next);

    //     ArrayList<String> preds = ac.nextChar(next);

    //     System.out.println("Predictions:");
    //     int c = 0;
    //     for (String p : preds) {
    //         System.out.printf("\t%d: %s\n", ++c, p);
    //     }
    //     System.out.println();
    // }
}
