package Project.NLP;

import Project.Game.AI.SPL.Orders.SPLObject;

/**
 *
 */
public class BasicConverter implements NLPConverter {
    String[] attackKeywords = new String[]{"attack", "destroy", "neutralise", "neutralize", "kill", "strike", "flank", "dominate"};
    String[] defendKeywords = new String[]{"defend", "protect", "guard", "entrench"};


    public BasicConverter() {
    }

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
            System.out.println("Checking word: " + word);
            for (String keyword : attackKeywords) {
                if (word.compareToIgnoreCase(keyword) == 0) {
                    attack++;
                    //System.out.println("Attack found: " + keyword);
                }
            }

            for(String keyword : defendKeywords){
                if(word.toLowerCase().contains(keyword)){
                    defend++;
                    //System.out.println("Defend found: " + keyword);
                }
            }
        }

        if(attack == 0 && defend == 0) return "Query";
        return (attack > defend) ? "Attack" : "Defend";
    }
}
