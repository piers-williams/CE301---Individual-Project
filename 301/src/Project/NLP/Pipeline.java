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

/**
 *
 */
public class Pipeline implements NLPConverter{

    @Override
    public SPLObject convert(String message) {
        return null;
    }



    private void tagMessage(String message){
        try {
            MaxentTagger tagger = new MaxentTagger("Contents/NLPModels/english-bidirectional-distsim.tagger");

            List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new BufferedReader(new FileReader("Content/Orders/Orders.txt")));

            ArrayList<TaggedWord> tags = new ArrayList<>();
            // Take only the first sentence
            tags = tagger.tagSentence(sentences.get(0));

            for(TaggedWord word : tags){
                System.out.println(word);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
