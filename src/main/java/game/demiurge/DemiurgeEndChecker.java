package game.demiurge;

import game.conditions.Condition;

import java.util.ArrayList;
import java.util.List;

public class DemiurgeEndChecker {

    ArrayList<Condition> conditions;

    public DemiurgeEndChecker(){ conditions = new ArrayList<>(); }

    public void addCondition(Condition condition){ conditions.add(condition); }

    public List<Condition> getConditions() {
        return conditions;
    }

    public boolean check(){

        for(Condition condition: conditions) {
            if (!condition.check())
                return false;
        }
        return true;
    }

}
