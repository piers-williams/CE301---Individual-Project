package Project.Game.Alliances;

import Project.Game.Faction;
import Project.Game.Factions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores information about who is allied to who
 */
public class AllianceManager {
    // Maps factions to a number for the matrix indices
    private HashMap<Faction, Integer> factionToInteger;
    private HashMap<Integer, Faction> integerToFaction;
    private int currentIndex = 0;

    // Simple matrix
    private Alliance[][] alliances;


    public AllianceManager() {

        factionToInteger = new HashMap<>();
        integerToFaction = new HashMap<>();

        for (Factions factions : Factions.values()) {
            factionToInteger.put(factions.getFaction(), currentIndex);
            integerToFaction.put(currentIndex, factions.getFaction());
            currentIndex++;
        }

        alliances = new Alliance[currentIndex][currentIndex];
    }

    public void setAlliance(Faction first, Faction second, Alliance newType) {
        alliances[factionToInteger.get(first)][factionToInteger.get(second)] = newType;
        alliances[factionToInteger.get(second)][factionToInteger.get(first)] = newType;
    }

    public Alliance getAlliance(Faction first, Faction second){
         return alliances[factionToInteger.get(first)][factionToInteger.get(second)];
    }

    // Returns list of factions that match the Alliance type given
    public ArrayList<Faction> getFactions(Faction first, Alliance type) {
        ArrayList<Faction> factions = new ArrayList<>();
        Alliance[] data = alliances[factionToInteger.get(first)];

        for (int i = 0; i < data.length; i++) {
            if (data[i] == type) {
                factions.add(integerToFaction.get(i));
            }
        }

        return factions;
    }
}
