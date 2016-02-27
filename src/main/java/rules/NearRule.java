package rules;

import puzzle.IconSet;
import puzzle.Possibilities;
import puzzle.ShowOptions;

import java.util.Random;

import static puzzle.Parameters.PUZZLE_SIZE;
import static puzzle.ShowOptions.*;

public class NearRule extends Rule {

    private int thing1[];
    private int thing2[];


    public NearRule(int[][] puzzle) {
        super();
        thing1 = new int[2];
        thing2 = new int[2];
        Random rndGen = new Random();
        int col1 = rndGen.nextInt(PUZZLE_SIZE);
        thing1[0] = rndGen.nextInt(PUZZLE_SIZE);
        thing1[1] = puzzle[thing1[0]][col1];

        int col2;
        if (col1 == 0)
            col2 = 1;
        else
        if (col1 == PUZZLE_SIZE-1)
            col2 = PUZZLE_SIZE-2;
        else
        if ( rndGen.nextInt(2) != 0 )
            col2 = col1 + 1;
        else
            col2 = col1 - 1;

        thing2[0] = rndGen.nextInt(PUZZLE_SIZE);
        thing2[1] = puzzle[thing2[0]][col2];
    }

    public String toString() {
        return getThingName(thing1[0], thing1[1]) +
                " is near to " + getThingName(thing2[0], thing2[1]);
    }

    public boolean apply(Possibilities possibilities) {
        boolean changed = false;

        for (int i = 0; i < PUZZLE_SIZE; i++) {
            if (applyToCol(possibilities, i, thing1[0], thing1[1], thing2[0], thing2[1]))
                changed = true;
            if (applyToCol(possibilities, i, thing2[0], thing2[1], thing1[0], thing1[1]))
                changed = true;
        }

        if (changed)
            apply(possibilities);

        return changed;
    }

    public void draw(int x, int y, IconSet iconSet, boolean highlight) {

    }

    private boolean applyToCol(Possibilities possibilities, int col, int nearRow, int nearNum, int thisRow, int thisNum)
    {
        boolean hasLeft, hasRight;

        if (col == 0)
            hasLeft = false;
        else
            hasLeft = possibilities.isPossible(col - 1, nearRow, nearNum);
        if (col == PUZZLE_SIZE-1)
            hasRight = false;
        else
            hasRight = possibilities.isPossible(col + 1, nearRow, nearNum);

        if ((! hasRight) && (! hasLeft) && possibilities.isPossible(col, thisRow, thisNum)) {
            possibilities.exclude(col, thisRow, thisNum);
            return true;
        } else
            return false;
    }

    public ShowOptions getShowOpts() {
        return SHOW_HORIZ;
    }

    //private void save(std::ostream &stream);
}
