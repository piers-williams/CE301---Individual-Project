package Project.NLP;

import Project.Game.AI.SPL.Orders.SPLObject;

/**
 *
 */
public class BasicConverter implements NLPConverter {
    String[] attackKeywords = new String[]{"attack, destroy, neutralise, neutralize, kill, strike, flank"};
    String[] defendKeywords = new String[]{"defend, protect, guard, entrench"};

    @Override
    public SPLObject convert(String message) {
        String type = getType(message);

        switch(type){
            case "attack":
                break;
            case "defend":
                break;
        }
        return null;
    }

    public String getType(String message) {
        int attack = 0, defend = 0;
        for (String word : message.split("\\s+")) {

            for (String keyword : attackKeywords) {
                if (message.contains(keyword)) {
                    attack++;
                }
            }

            for(String keyword : defendKeywords){
                if(message.contains(keyword)){
                    defend++;
                }
            }
        }
        return (attack > defend) ? "Attack" : "Defend";
    }
}
