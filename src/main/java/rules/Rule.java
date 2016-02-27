package rules;

import puzzle.IconSet;
import puzzle.Possibilities;
import puzzle.ShowOptions;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.util.Random;

/**
 * Created by AZbest on 22.02.2016.
 */

public abstract class Rule implements Serializable {

    @Override
    public abstract String toString();
    public abstract boolean apply(Possibilities possibilities);
    public abstract void draw(int x, int y, IconSet iconSet, boolean highlight);
    public boolean applyOnStart() {
        return false;
    }
    public ShowOptions getShowOpts() {
        return ShowOptions.SHOW_NOTHING;
    };
    public static Rule genRule(int[][] puzzle)
    {
        Random rndGen = new Random();
        int a = rndGen.nextInt(14);
        switch (a) {
            case 0:
            case 1:
            case 2:
            case 3: return new NearRule(puzzle);
            case 4: return new OpenRule(puzzle);
            case 5:
            case 6: return new UnderRule(puzzle);
            case 7:
            case 8:
            case 9:
            case 10: return new DirectionRule(puzzle);
            case 11:
            case 12:
            case 13: return new BetweenRule(puzzle);
            default: return genRule(puzzle);
        }
    }

    protected static String getThingName(int row, int thing)
    {
        return 'A' + new Integer(row).toString() + new Integer(row).toString(thing);
    }

    /*
    void saveRules(Rules &rules, std::ostream &stream)
    {
        writeInt(stream, rules.size());
        for (Rules::iterator i = rules.begin(); i != rules.end(); i++)
            (*i)->save(stream);
    }


    void loadRules(Rules &rules, std::istream &stream)
    {
        int no = readInt(stream);

        for (int i = 0; i < no; i++) {
            std::wstring ruleType = readString(stream);
            Rule *r;
            if (ruleType == L"near")
                r = new NearRule(stream);
            else if (ruleType == L"open")
                r = new OpenRule(stream);
            else if (ruleType == L"under")
                r = new UnderRule(stream);
            else if (ruleType == L"direction")
                r = new DirectionRule(stream);
            else if (ruleType == L"between")
                r = new BetweenRule(stream);
            else
                throw Exception(L"invalid rule type " + ruleType);
            rules.push_back(r);
        }
    }
    */

}