package Project.NLP;

import Project.Game.AI.SPL.Orders.SPLObject;

/**
 *
 */
public class BasicConverter implements NLPConverter {
    String[] attackKeywords = new String[]{"attack", "destroy", "neutralise", "neutralize", "kill", "strike", "flank", "dominate"};
    String[] defendKeywords = new String[]{"defend", "protect", "guard", "entrench"};
    String[] queryKeywords = new String[]{"how", "when", "why", "what"};

    public BasicConverter() {

    }

    @Override
    public SPLObject convert(String message) {

        String[] sentences = message.split("\\.");
        String type = getType(message);

        switch (type) {
            case "Query":
                break;
            case "Attack":
                break;
            case "Defend":
                break;
        }
        return null;
    }

    public String getType(String message) {
        int attack = 0, defend = 0, query = 0;
        for (String word : message.split("\\s+")) {
            for (String keyword : attackKeywords) {
                if (word.compareToIgnoreCase(keyword) == 0) {
                    attack++;
                }
            }

            for (String keyword : defendKeywords) {
                if (word.compareToIgnoreCase(keyword) == 0) {
                    defend++;
                }
            }

            for (String keyword : queryKeywords) {
                if (word.compareToIgnoreCase(keyword) == 0) {
                    query++;
                }
            }
        }

        if (attack > query && attack > defend) return "Attack";
        if (defend > attack && defend > query) return "Defend";
        if (query > attack && query > defend) return "Query";
        return "N/A";
    }
}
