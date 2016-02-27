package puzzle;

import rules.Rule;

import java.util.List;
import java.util.Random;

/**
 * Created by AZbest on 27.02.2016.
 */
public class PuzzleGenerator implements Parameters{

    public static void genPuzzle(int[][] puzzle, List<Rule> rules) {
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            for (int j = 0; j < PUZZLE_SIZE; j++)
                puzzle[i][j] = j + 1;
            shuffle(puzzle[i]);
        }

        genRules(puzzle, rules);
        removeRules(puzzle, rules);
        //printPuzzle(puzzle);
        //printRules(rules);
    }
    public static void openInitial(Possibilities possibilities, List<Rule> rules) {
        for (Rule r: rules) {
            if (r.applyOnStart())
                r.apply(possibilities);
        }
    }

    public static void getHintsQty(List<Rule> rules, int vert, int horiz) {
        vert = 0;
        horiz = 0;

        for (Rule r: rules) {
            ShowOptions so = r.getShowOpts();
            switch (so) {
                case SHOW_VERT: vert++; break;
                case SHOW_HORIZ: horiz++; break;
                default:
            }
        }
    }

    private static Rule getRule(List<Rule> rules, int no)
    {
        return rules.get(no);
    }

    private static void genRules(int[][] puzzle, List<Rule> rules)
    {
        boolean rulesDone = false;
        boolean inactiveRule;
        do {
            inactiveRule = false;
            Rule rule = Rule.genRule(puzzle);
            if (rule != null) {
                String s = rule.toString();
                for (Rule r: rules)
                    if (r.toString().equals(s)) {
                        rules.remove(r);
                        inactiveRule = true;
                        break;
                    }
                if ( !inactiveRule ) {
//printf("adding rule %s\n", rule->getAsText().c_str());
                    rules.add(rule);
                    rulesDone = canSolve(puzzle, rules);
                }
            }
        } while (! rulesDone);
    }

    private static void shuffle(int arr[])
    {
        int RAND_MAX = 6;
        Random rand = new Random();
        int a, b, c;
        for (int i = 0; i < 30; i++) {
            a = (int)(((double)PUZZLE_SIZE)*rand.nextDouble()/(RAND_MAX+1.0));
            if ((a < 0) || (a >= PUZZLE_SIZE)) {
                System.out.println("Index error\n");
                throw new RuntimeException();
            }
            b = (int)(((double)PUZZLE_SIZE)*rand.nextDouble()/(RAND_MAX+1.0));
            if ((b < 0) || (b >= PUZZLE_SIZE)) {
                System.out.println("Index error\n");
                throw new RuntimeException();
            }
            c = arr[a];
            arr[a] = arr[b];
            arr[b] = c;
        }
    }

    private static boolean canSolve(int[][] puzzle, List<Rule> rules)
    {
        Possibilities pos = new Possibilities();
        boolean changed = false;

        do {
            changed = false;
            for (Rule r: rules) {
                if ( r.apply(pos) ) {
                    changed = true;
                    if (! pos.isValid(puzzle)) {
                        System.out.println("after error:");
                        System.out.println(pos);
                        throw new RuntimeException("Invalid possibilities after rule " + r);
                    }
                }
            }
        } while (changed);

        boolean res = pos.isSolved();
        return res;
    }

    private static void removeRules(int[][] puzzle, List<Rule> rules)
    {
        boolean possible;
        do {
            possible = false;
            for (Rule r: rules) {
                List<Rule> excludedRules = rules;
                excludedRules.remove(r);
                if (canSolve(puzzle, excludedRules)) {
                    possible = true;
                    rules.remove(r);
                    break;
                }
            }
        } while (possible);
    }

}

    /*
            void savePuzzle(SolvedPuzzle &puzzle, std::ostream &stream)
            {
                for (int row = 0; row < PUZZLE_SIZE; row++)
                for (int col = 0; col < PUZZLE_SIZE; col++)
                    writeInt(stream, puzzle[row][col]);
            }

            void loadPuzzle(SolvedPuzzle &puzzle, std::istream &stream)
            {
                for (int row = 0; row < PUZZLE_SIZE; row++)
                    for (int col = 0; col < PUZZLE_SIZE; col++)
                        puzzle[row][col] = readInt(stream);
            }
    */
    /*static void printPuzzle(SolvedPuzzle &puzzle)
{
    for (int i = 0; i < PUZZLE_SIZE; i++) {
        char prefix = 'A' + i;
        for (int j = 0; j < PUZZLE_SIZE; j++) {
            if (j)
                std::cout << "  ";
            std::cout << prefix << puzzle[i][j];
        }
        std::cout << std::endl;
    }
}


static void printRules(Rules &rules)
{
    for (Rules::iterator i = rules.begin(); i != rules.end(); i++)
        std::cout << (*i)->getAsText() << std::endl;;
}*/



/*int main(int argc, char *argv[])
{
    srand(time(NULL));

    Rules rules;
    Puzzle puzzle;

    genPuzzle(puzzle, rules);
    printPuzzle(puzzle);
    printRules(rules);

    return 0;
}*/