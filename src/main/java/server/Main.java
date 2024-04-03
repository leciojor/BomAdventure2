package server;

import server.json.ResetResponse;
import server.json.Scripture;
import spark.*;

import java.util.*;

import com.google.gson.Gson;


public class Main {

    private static HashSet<String[]> scriptures_set = new HashSet<>();
    private static List<Object[]> indexesArrays;

    private static String[] allWords;
    private static HashSet<String> wordsSet = new HashSet<>();
    private static int level = 0;



    public static void main(String[] args) {
        run(8080);
        System.out.println(" BOM adventure Server: ");

    }

    public static int run(int desiredPort){

        Spark.port(desiredPort);

        Spark.staticFiles.location("/web");

        Spark.get("/quiz", Main::mainHandler);
        Spark.post("/reset", Main::resetHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private static Object resetHandler(Request req, Response res){
        level = 0;
        Gson gson = new Gson();

        return gson.toJson(new ResetResponse("Level reset to 0", level));
    }

    private static Object mainHandler(Request request, Response response) {
        Gson gson = new Gson();
        addingScriptures();
        String[] random_scripture_array = gettingRandomScripture();
        String random_scripture = random_scripture_array[1];
        String scripture_name = random_scripture_array[0];
        gettingScriptureWithBlanks(random_scripture);
        System.out.println("Selected Scripture: " + scripture_name);

        String missing_text = "";
        for (String blancks : allWords){
            if (blancks == null){
                missing_text += "_";
            }
            else{
                missing_text += blancks + "";
            }

        }
        System.out.println("Missing Text: " + missing_text);
        System.out.println("Level: " + level);

        level++;
        Scripture scripture = new Scripture(scripture_name, allWords, random_scripture, indexesArrays, level, wordsSet);
        wordsSet = new HashSet<>();

        //serialization
        return gson.toJson(scripture);

    }

    private static void gettingScriptureWithBlanks(String randomScripture) {
        allWords = randomScripture.split("(?<=\\b|\\W)(?=\\w|\\W)|(?<=\\w|\\W)(?=\\b|\\W)");

        List<String> randomWords;

        if (level <= 3){
            getRandomWords(allWords.length/4);
        }

        else if(level >= 3 || level <= 7){
            getRandomWords(allWords.length/3);
        }

        else{
            getRandomWords(allWords.length/2);
        }

    }

    public static void getRandomWords(int numWordsRemove) {

        List<Object[]> removedWords = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < numWordsRemove;) {
            try {
                int randomIndex = rand.nextInt(allWords.length);
                String removedWord = allWords[randomIndex];

                if (removedWord != null && removedWord.length() >= 3) {
                    removedWords.add(new Object[]{randomIndex, removedWord});
                    wordsSet.add(removedWord);
                    allWords[randomIndex] = null;

                    i++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        indexesArrays = removedWords;
    }



    private static String[] gettingRandomScripture() {
        ArrayList<String[]> list = new ArrayList<>(scriptures_set);

        Random rand = new Random();
        int randomIndex = rand.nextInt(list.size());

        return list.get(randomIndex);
    }

    private static void addingScriptures(){
        scriptures_set.add(new String[]{"1 Nephi 3:7","And it came to pass that I, Nephi, said unto my father: I will go and do the things which the Lord hath commanded, for I know that the Lord giveth no commandments unto the children of men, save he shall prepare a way for them that they may accomplish the thing which he commandeth them."});
        scriptures_set.add(new String[]{"2 Nephi 2:25", "Adam fell that men might be; and men are, that they might have joy."});
        scriptures_set.add(new String[]{"2 Nephi 32:3", "Angels speak by the power of the Holy Ghost; wherefore, they speak the words of Christ. Wherefore, I said unto you, feast upon the words of Christ; for behold, the words of Christ will tell you all things what ye should do."});
        scriptures_set.add(new String[]{"Mosiah 2:17", "And behold, I tell you these things that ye may learn wisdom; that ye may learn that when ye are in the service of your fellow beings ye are only in the service of your God."});
        scriptures_set.add(new String[]{"Alma 32:21", "And now as I said concerning faith—faith is not to have a perfect knowledge of things; therefore if ye have faith ye hope for things which are not seen, which are true."});
        scriptures_set.add(new String[]{"3 Nephi 12:48", "Therefore I would that ye should be perfect even as I, or your Father who is in heaven is perfect."});
        scriptures_set.add(new String[]{"Helaman 5:12", "And now, my sons, remember, remember that it is upon the rock of our Redeemer, who is Christ, the Son of God, that ye must build your foundation; that when the devil shall send forth his mighty winds, yea, his shafts in the whirlwind, yea, when all his hail and his mighty storm shall beat upon you, it shall have no power over you to drag you down to the gulf of misery and endless wo, because of the rock upon which ye are built, which is a sure foundation, a foundation whereon if men build they cannot fall."});
        scriptures_set.add(new String[]{"Ether 12:6", "And now, I, Moroni, would speak somewhat concerning these things; I would show unto the world that faith is things which are hoped for and not seen; wherefore, dispute not because ye see not, for ye receive no witness until after the trial of your faith."});
        scriptures_set.add(new String[]{"Moroni 7:41", "And what is it that ye shall hope for? Behold I say unto you that ye shall have hope through the atonement of Christ and the power of his resurrection, to be raised unto life eternal, and this because of your faith in him according to the promise."});
        scriptures_set.add(new String[]{"Alma 39:9", "Now my son, I would that ye should repent and forsake your sins, and go no more after the lusts of your eyes, but cross yourself in all these things; for except ye do this ye can in nowise inherit the kingdom of God. Oh, remember, and take it upon you, and cross yourself in these things."});

    }


}