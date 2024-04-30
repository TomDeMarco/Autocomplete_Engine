# CS1501 Project 2


## Goal:
To gain a better understanding of search algorithms and symbol tables by
implementing an autocompletion engine.


## Background:
In order to make typing on a mobile device quick and easy, an autocomplete
feature is commonly implemented to try to guess what word a user wishes to type
before they are finished. Such a feature requires extensive use of search
algorithms. Here, you will be building the backend of an autocomplete engine
(i.e., you won't gathering any user input).


## Specifications:
* The default main class is `./app/src/main/java/cs1501_p2/App.java`. The starter
  `App.java` simply runs the autocomplete engine with a few characters and
  prints out the predictions generated.
* There are two different dictionaries provided with the project. There is a
  large dictionary (~1.2MB) in `./app/src/main/resources/` that is used in `App.java`
  and a small dictionary (37B) in `./app/src/test/resources/` that is used by the
  basic tests.
    * Note that Gradle will automatically copy these file to be accessed by
      the running code. Specifically:
      * The contents of `./app/src/main/resources/` are copied to
        `./app/build/resources/main/` when you run `./gradlew run` (hence
        why the path to read this file is
        `"build/resources/main/dictionary.txt"` in `App.java`.
      * The contents of `./app/build/resources/test/` are copied to
        `./app/src/test/resources/dictionary.java` when you run `./gradlew test`.

* In order to get your project to actually compile and run, however, you will
  need to implement 3 classes:
      * `DLB` in `./app/src/main/java/cs1501_p2/DLB.java` which implements
      `Dict`
    * `UserHistory` in `./app/src/main/java/cs1501_p2/UserHistory.java` which
      also implements `Dict`
    * `AutoCompleter` in `./app/src/main/java/cs1501_p2/AutoCompleter.java`
      which implements `AutoComplete_Inter`.

* For `DLB`, you must implement a De La Briandais (DLB) trie data structure (as 
  described in lecture) to use in your project. This class will be used to
  store the dictionary of English words.`DLB` must be all your own code (e.g.,
  you cannot use the Java LinkedList, you must implement your own linked-list).
  You must use the `DLBNode` defined in
  `./app/src/main/java/cs1501_p2/DLBNode.java` to construct the DLB. You cannot
  modify `DLBNode.java`. You must implement all of the methods of `Dict` as
  defined in `Dict.java`, specifically:

    * `public void add(String key);`
    * `public boolean contains(String key);`
    * `public boolean containsPrefix(String pre);`
    * `public int searchByChar(char next);`
    * `public void resetByChar();`
    * `public ArrayList<String> suggest();`
    * `public ArrayList<String> traverse();`
    * `public int count();`

  For descriptions of what these methods should do, refer to the JavaDoc
  comments in `Dict.java`. `DLB` must return the results of `suggest()` in
  ascending order by their character value ("ASCIIbetical" order).

  This class must be all your own code. You cannot use any code from the
  textbook authors or the JCL in constructing the DLB. The only exception is
  that you can use an ArrayList to construct the return values for `suggest()`
  and `traverse()` (and only for constructing return values).

* `UserHistory` will be used to keep track of what words the user has actually
  selected, and how many times that word has been selected. For `UserHistory`,
  you are free to use whatever approach you choose to implement `Dict`. You are
  free to use any JCL classes or code provided by the textbook authors to
  implement `UserHistory`.
    * `UserHistory`'s implementation of `suggest()` must produce up to 5 words
      that the user has most frequently selected based off of the given prefix
      (in order). E.g., if the user has selected "free" 5 times in the past,
      "frequent" 3 times, and "frustration" 1 time in the past, after entering
      'f', then 'r', `suggest()` should return an ArrayList containing "free",
      "frequent", then "frustration".
    * Your implementation of `UserHistory` cannot have an asymptotically worse
      runtime for any operation than a DLB-based approach.

* `AutoCompleter` will bring all of this together to form the API of your
  autocomplete engine. First, you will need to have a constructor that accepts
  2 String filenames. The first is the name of a file containing the English
  dictionary, the second the name of a file containing saved user history 
  information. You should also implement an overloaded constructor that
  supports only the first argument (the English language dictionary). Both of
  these constructors should create `DLB` and `UserHistory` objects as
  attributes of `AutoCompleter` (in the case of the latter constructor, the
  `UserHistory` object will be empty).

  There are 3 methods defined in `AutoComplete_Inter`. For `finishWord()`, be
  sure to reset the state of any searches and update the user history
  attribute to reflect the word selected. Note that it may not be based on the
  current search! E.g., you should support the calls:

      * `.nextChar('a')`
      * `.nextChar('b')`
      * `.nextChar('c')`
      * `.finishWord("word")`

  For `saveUserHistory()`, you are free to adopt whatever approach you want
  for saving the state of the `UserHistory` object to a file. The save file
  does not need to be a text file or human-readable. Saving and loading the
  user history should have a O(n) runtime (where n is the number of distinct
  words in the `UserHistory` object). **Do not** add any user history save
  state files to your repository!

* None of your methods or constructors for `DLB`, `UserHistory`, or `AutoCompleter`
  should `throw` any exceptions. Any exceptions must be handled within the
  methods and constructors. Any errors in reading a file from disk should
  result in initializing an empty `DLB` or `UserHistory` object.


### Example `App.java` run

An example run of the default `App.java` would proceed as follows:

```
Entered: t
Predictions:
        1: t
        2: ta
        3: tab
        4: tab's
        5: tabbed

Entered: h
Predictions:
        1: thalami
        2: thalamus
        3: thalamus's
        4: thalidomide
        5: thalidomide's

Entered: e
Predictions:
        1: the
        2: theater
        3: theater's
        4: theatergoer
        5: theatergoer's

Entered: r
Predictions:
        1: therapeutic
        2: therapeutically
        3: therapeutics
        4: therapeutics's
        5: therapies

Entered: e
Predictions:
        1: there
        2: there's
        3: thereabout
        4: thereabouts
        5: thereafter

Selected: thereabout

Entered: t
Predictions:
        1: thereabout
        2: t
        3: ta
        4: tab
        5: tab's

Entered: h
Predictions:
        1: thereabout
        2: thalami
        3: thalamus
        4: thalamus's
        5: thalidomide

Entered: e
Predictions:
        1: thereabout
        2: the
        3: theater
        4: theater's
        5: theatergoer

Entered: r
Predictions:
        1: thereabout
        2: therapeutic
        3: therapeutically
        4: therapeutics
        5: therapeutics's

Entered: e
Predictions:
        1: thereabout
        2: there
        3: there's
        4: thereabouts
        5: thereafter
```


## Submission Guidelines:
* **DO NOT** add the `./app/build/` diectory to your repository.
    * Leave the `./app/build.gradle` file there, however

* Be sure to remember to push the latest copy of your code back to your GitHub
    repository before submitting. To submit, log into GradeScope from Canvas and
    have GradeScope pull your repository from GitHub.


## Additional Notes:
* Note that if your user history predictions contain a word that is also
  contained in the dictionary predictions, this word should not be presented
  as a suggestion to the user twice in the same prompt (e.g., the final list
  of predictions in the example above).

* Your program should run in a case-sensitive manner in order to allow for
  easier prediction of proper nouns and acronyms.

* You should not `import` any libraries for your defintion of `DLB` or
  `AutoCompleter` with the following two exceptions:
    * `ArrayList` for constructing return values as specified in the 
      provided interfaces.
    * Whatever File I/O libraries in the JCL you prefer to use for reading
      in dictionary files or `UserHistory` state.

* You cannot modify any provided files (e.g., interfaces and `DLBNode.java`).

* Don't forget to make all of your source files a part of the package 
  `cs1501_p2`!

* The default file to run is `App.java`, you can execute this by running
  `./gradlew run` on Linux or macOS.

* There a number of tests for your classes in
  `./app/src/test/java/cs1501_p2/AllTest.java`. Running `./gradlew test` on
  Linux or macOS will run these tests.

* As always, note that we will be grading your project using different tests
  than those that appear in `./app/src/test`. You are encouraged to write your
  own tests to help ensure that your project is working as specified.


## Grading Rubric:
| Feature | Points
| ------- | ------:
| `DLB` implemented as specified | 35
| `UserHistory` implemented as specified | 35
| `AutoCompleter` implemented as specified | 25
| Proper assignment submission | 5
