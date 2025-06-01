import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class Processing {
    private static final String BINARY_PATH = "./BinaryFiles/";
    private static String WHICH_BOOK = "";
    public Processing(String bookName){
        WHICH_BOOK = bookName;
    }
    private static String[] splitString(String sentence){
        String[] words = Arrays.stream(sentence.split("[•—'()*$%^&\';`\\~\",”/|_=+@#<>{}.!?\\s]+"))
                .filter(word -> !word.isEmpty())
                .toArray(String[]::new);
        return words;
    }
    public void Build_DefaultHashDAWG(List<String> book) throws Exception {

        dawg<String> dawg = new dawg();
        int key_add_count = 0;
        for (int i = 0; i < book.size(); ++i){
            List<String> list = Arrays.asList(splitString(book.get(i)));
            // Only add is list has items
            if (list.size() == 0){
                continue;
            }

            for (int j = 1; j < list.size(); ++j){
                // todo 1; if word is an integer, then replace with common node, as number is irrelevant value
                // todo 1; "-" chars are split words because of space as is common in books
                ++key_add_count;
                if (list.size() == 1){
                    // todo 1; this is a viable option for pruning because if the
                    // list passed in has only 1 word, it's probably not "A"
                    String node = list.get(list.size() - 1);
                    if (node != null){
                        String lower = node.toLowerCase();
                        dawg.insert(lower);
                    }
                }
                else{
                    dawg.insert(list.get(j - 1).toLowerCase(), list.get(j).toLowerCase());
                }
            }

            dawg_node<String> node = dawg.node_map.get(list.get(list.size() - 1).toLowerCase());
            if (node != null){
                node.canBeEndOfWord = true;
            }
        }

        dawg = null;
    }
}