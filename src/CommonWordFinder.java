import DataStructuresSourceCode.*;
import java.io.*;
import java.util.Comparator;
import java.util.Arrays;


/**Class that analyzes the most used words in a document by using either
 * BSTMap, AVLTreeMap, or MyHashMap.
 *
 * @author Marissa Crevecoeur, mac2538
 * @version 1.0 December 15, 2022
 */

public class CommonWordFinder {

    /**
     * A hashmap to verify that a character is accepted, including "'" and "-"
     */
    protected static final MyHashMap<Character, Integer> VALID = new MyHashMap<>();
    static{
        VALID.put('a',1);VALID.put('k',1);VALID.put('v',1);
        VALID.put('b',1);VALID.put('l',1);VALID.put('w',1);
        VALID.put('c',1);VALID.put('m',1);VALID.put('x',1);
        VALID.put('c',1);VALID.put('n',1);VALID.put('y',1);
        VALID.put('d',1);VALID.put('o',1);VALID.put('z',1);
        VALID.put('e',1);VALID.put('p',1);VALID.put('\'',1);
        VALID.put('f',1);VALID.put('q',1);VALID.put('-',1);
        VALID.put('g',1);VALID.put('r',1);
        VALID.put('h',1);VALID.put('s',1);
        VALID.put('i',1);VALID.put('t',1);
        VALID.put('j',1);VALID.put('u',1);
    }

    /**
     * A method to parse the file to keep the main method short. It converts
     * a BufferedReader into a String array using StringBuilders. it also
     * removes any characters not within VALID, including dashes at the
     * beginning of a word. It also handles any event of an IO Exception here.
     * This method is only called once in the class.
     *
     * Knowledge for how to parse files using BufferedReader found from
     * www.geeksforgeeks.org/different-ways-reading-text-file-java/
     *
     * @param br A BufferedReader that turns text from a file into a "character-input stream"
     * @return String [] an array without spaces or invalid characters
     */
    protected static String [] parseFile(BufferedReader br){
        StringBuilder sb = new StringBuilder();
        int num = 1;
        StringBuilder app = new StringBuilder();
        try{
            while(br.ready()){
                char c = Character.toLowerCase((char)br.read());
                if(VALID.get(c) != null){
                    if(c != '-' || 0 != app.length()) app.append(c);
                }
                else if(Character.isWhitespace(c)){
                    sb.append(app);
                    sb.append(System.getProperty("line.separator"));
                    app.delete(0, app.length());
                    num++;
                }
             } //taking out unnecessary symbols
            if(app.length() != 0){
                sb.append(app);
            }
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred reading '");
            System.exit(1);
        } //handles it early to prevent overlapping Exceptions later
        String [] ret = new String[num];
        int i = 0;
        char [] c = sb.toString().toCharArray();
        app = new StringBuilder();
        for (char value : c) {
            if (!Character.isWhitespace(value)) {
                app.append(value);
            }
            else {
                ret[i++] = app.toString();
                app.delete(0, app.length());
            }
        } //adding chars to string array
        if(i == 0 || i < ret.length){
            ret[i] = app.toString();
        } //if the document doesn't end on a space the last word will be ignored, so this ensures that it is counted
        return ret;
    }

    /**
     * Removes any null values from the original array (created directly
     * after parsing the file) by creating an array with the lower of
     * two values, the limit or number of keys in the map
     *
     * @param map the map which the values were plotted
     * @param array the array equivalent of the map
     * @param limit the provided user input of the max words they want shown
     * @return String []
     */
    protected static String [] build(MyMap<String, Integer> map, String [] array, int limit){
        String [] ar = new String[map.size()];
        int i = 0;
        while(array[i] != null){
            ar[i] = array[i++];
        } //uses the number of values in the map to get rid of the null elements
        String [] ret = printArr(ar, map); //sorts the array
        String [] a = new String [Math.min(limit, ar.length)];
        int j = 0; //now takes only the top of either the limit or map size, whichevers smaller
        while(j < a.length){
            a[j] = ret[j++];
        }
        return a;
    }

    /**
     * This method uses a comparator to sort the provided array, first in
     * descending order for the number of occurrences, then in alphabetical
     * order for in the event of a tie.
     *
     * @param s pre-sorted array provided by the build method
     * @param map the original map which the terms are plotted
     *           to, to access values of the occurrences
     * @return String [] a sorted version of the s array
     */
    //create comparator to sort Array
    //need to use the comparator for the ints first, and in the event of a tie the alph order
    protected static String [] printArr(String [] s, MyMap<String,Integer> map) {
        if(map.size() > 1) {
            Comparator<String> compare = (one, two) -> {
                int occ =  map.get(two) - map.get(one); //descending order
                int word = one.compareTo(two); //ascending order
                return (occ != 0) ? occ : word;
            };
            Arrays.sort(s, compare);
        }
        return s;
    }

    /**
     * A method that formats the output according to the guidelines.
     *
     * @param map the map where the words were plotted
     * @param actual the sorted array of elements, ready for printing
     * @param longe the length of the longest word, for printing purposes
     * @return StringBuilder the object that will be printed
     */
    protected static StringBuilder formatting(MyMap<String,Integer> map , String [] actual, int longe){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 1;
        while(i < actual.length){
            String temp = "" + j;
            String wow = "" + actual.length;
            sb.append(" ".repeat(Math.max(0, (wow.length() - temp.length())))); //spaces before element
            sb.append(j++).append(". ").append(actual[i]); // word
            sb.append(" ".repeat(Math.max(1, (longe + 1 - actual[i].length())))); //spaces after element
            sb.append(map.get(actual[i++])); //the number of occurrences
            if(i != actual.length) sb.append(System.getProperty("line.separator"));
        }
        return sb;
    }

    public static void main(String[] args) {
        if(args.length > 3 || args. length < 2 ){
            System.err.println("Usage: java CommonWordFinder <filename> <bst|avl|hash> [limit]");
            System.exit(1);
        }
       try{
           File file = new File(args[0]);
           BufferedReader br = new BufferedReader(new FileReader(file));
           String[] strb = parseFile(br);
           int unique = 0;
           int longest=0; //number for longest word in array
           MyMap<String, Integer> BSTmap = new BSTMap<>();
           MyMap<String, Integer> avl = new AVLTreeMap<>();
           MyMap<String, Integer> hash = new MyHashMap<>();
           int limit;
           if(args.length == 3){
               boolean tf = true;
               for(int i = 0; i < args[2].length(); ++i){
                   if(!Character.isDigit(args[2].charAt(i))) {
                       tf = false; //if it is not a number
                   }
               }
               if (tf && Integer.parseInt(args[2]) > 0) { //and greater than zero
                   limit = Integer.parseInt(args[2]);
               } else {
                   throw new NumberFormatException();
               }
           }
           else {
               limit = 10;
           }
           switch (args[1]) {
               case "bst":
                   String [] actual = new String[strb.length];
                   int count = 0;
                   for (String s : strb) {
                       if (BSTmap.get(s) != null) {
                           BSTmap.put(s, BSTmap.get(s) + 1); //incrementing occurrence if word already exists
                       } else {
                           if(s.length() > 0) {
                               BSTmap.put(s, 1);
                               actual[count++] = s;
                               if (longest < s.length()) {
                                   longest = s.length(); //adding with a value of 0 to map if doesn't exist
                               }
                               unique++;
                           }
                       } //parses through the file array and adds the indices to the map
                   }
                   //need to iterate through map and add numbers in a filled array
                   //then sort the array and then print the numbers up until either
                   //the limit or the num of words in map
                   String [] ret = build(BSTmap, actual, limit);
                   System.out.println("Total unique words: " + unique);
                   StringBuilder sb = formatting(BSTmap, ret, longest);
                   System.out.println(sb);
                   break;
               case "avl":
                   actual = new String[strb.length];
                   count = 0;
                   for (String s : strb) {
                       if (avl.get(s) != null) {
                           avl.put(s, avl.get(s) + 1); //incrementing occurrence if word already exists
                       } else {
                           if(s.length() > 0) {
                               avl.put(s, 1);
                               actual[count++] = s;
                               if (longest < s.length()) {
                                   longest = s.length(); //adding with a value of 0 to map if doesn't exist
                               }
                               unique++;
                           }
                       } //parses through the file array and adds the indices to the map
                   }
                   //need to iterate through map and add numbers in a filled array
                   //then sort the array and then print the numbers up until either
                   //the limit or the num of words in map
                   ret = build(avl, actual, limit);
                   System.out.println("Total unique words: " + unique);
                   sb = formatting(avl, ret, longest);
                   System.out.println(sb);
                   break;
               case "hash":
                   actual = new String[strb.length];
                   count = 0;
                   for (String s : strb) {
                       if(s!= null) {
                           if (hash.get(s) != null) {
                               hash.put(s, hash.get(s) + 1); //incrementing occurrence if word already exists
                           } else {
                               if(s.length() > 0) {
                                   hash.put(s, 1);
                                   actual[count++] = s;
                                   if (longest < s.length()) {
                                       longest = s.length();
                                   }
                                   unique++;
                               }
                           }
                       } //parses through the file array and adds the indices to the map
                   }
                   //need to iterate through map and add numbers in a filled array
                   //then sort the array and then print the numbers up until either
                   //the limit or the num of words in map
                   ret = build(hash, actual, limit);
                   System.out.println("Total unique words: " + unique);
                   sb = formatting(hash, ret, longest);
                   System.out.println(sb);
                   break;
               default:
                   System.err.println("Error: Invalid data structure '" + args[1] + "' received.");
                   System.exit(1);
           }
       }
       catch(FileNotFoundException ffe){
           if(!System.err.checkError()){
               System.err.println("Error: Cannot open file '" + args[0] + "' for input.");
           }
           else{
               System.err.println(ffe.getMessage() + args[0] +  "'.");
           }
           System.exit(1);
       }
       catch (NumberFormatException nfe) {
           System.err.println("Error: Invalid limit '" + args[2] + "' received." );
           System.exit(1);
       }
    }
}