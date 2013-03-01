package Project.NLP;

import Project.Game.AI.SPL.Orders.SPLObject;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class Pipeline implements NLPConverter {

    @Override
    public SPLObject convert(String message) {
        String[] temp = extractSenderAndMessage(message);
        // This is needed when we integrate with the game
        String address = temp[0];

        String strippedMessage = temp[1];
        // Store the tagged message
        ArrayList<TaggedWord> taggedMessage = tagMessage(strippedMessage);

        SPLObject splObject = extractOrder(taggedMessage);

        return splObject;
    }

    public String[] extractSenderAndMessage(String message) {
        String[] data = new String[2];

        String regexForStripping = "[A-Za-z]+:\\s*";
        String regexForSender = "[A-Za-z]+:";

        Pattern pattern = Pattern.compile(regexForStripping);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            data[1] = message.substring(matcher.end());
        }

        pattern = Pattern.compile(regexForSender);
        matcher = pattern.matcher(message);
        while (matcher.find()) {
            data[0] = message.substring(matcher.start(), matcher.end() - 1);
        }

        return data;
    }

    public ArrayList<TaggedWord> tagMessage(String message) {
        try {
            MaxentTagger tagger = new MaxentTagger("Contents/NLPModels/english-bidirectional-distsim.tagger");

            List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader("Content/Orders/Orders.txt")));

            return tagger.tagSentence(sentences.get(0));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Tagging went wrong");
    }

    public SPLObject extractOrder(ArrayList<TaggedWord> words) {
        return null;
    }
}
