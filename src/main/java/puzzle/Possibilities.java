package puzzle;

public class Possibilities implements Parameters {
    private int pos[][][];

    public Possibilities() {
        pos = new int[PUZZLE_SIZE][PUZZLE_SIZE][PUZZLE_SIZE];
        reset();
    }
    //Possibilities(std::istream &stream);
    //void save(std::ostream &stream);
    void reset() {
        for (int i = 0; i < PUZZLE_SIZE; i++)
            for (int j = 0; j < PUZZLE_SIZE; j++)
                for (int k = 0; k < PUZZLE_SIZE; k++)
                    pos[i][j][k] = k + 1;
    }

    public void exclude(int col, int row, int element) {
        if (pos[col][row][element - 1] == 0)
            return;
        pos[col][row][element - 1] = 0;
        checkSingles(row);
    }

    public void set(int col, int row, int element) {
        for (int i = 0; i < PUZZLE_SIZE; i++)
            if ((i != element - 1))
                pos[col][row][i] = 0;
            else
                pos[col][row][i] = element;

        for (int j = 0; j < PUZZLE_SIZE; j++)
            if (j != col)
                pos[j][row][element - 1] = 0;

        checkSingles(row);
    }

    public boolean isPossible(int col, int row, int element) {
        return pos[col][row][element - 1] == element;
    }

    public boolean isDefined(int col, int row) {
        int solvedCnt = 0, unsolvedCnt = 0;
        for (int i = 0; i < PUZZLE_SIZE; i++)
            if (pos[col][row][i] != 0)
                unsolvedCnt++;
            else
                solvedCnt++;
        return ((unsolvedCnt == PUZZLE_SIZE-1) && (solvedCnt == 1));
    }

    public int getDefined(int col, int row) {
        for (int i = 0; i < PUZZLE_SIZE; i++)
            if (pos[col][row][i] != 0)
                return i + 1;
        return 0;
    }

    public int getPosition(int row, int element) {
        int cnt = 0;
        int lastPos = -1;

        for (int i = 0; i < PUZZLE_SIZE; i++)
            if (pos[i][row][element - 1] == element) {
                cnt++;
                lastPos = i;
            }

        return cnt == 1 ? lastPos : -1;
    }

    public boolean isSolved() {
        for (int i = 0; i < PUZZLE_SIZE; i++)
            for (int j = 0; j < PUZZLE_SIZE; j++)
                if (! isDefined(i, j))
                    return false;
        return true;
    }

    @Override
    public String toString() {
        String s = "";
        for (int row = 0; row < PUZZLE_SIZE; row++) {
            s += (char)('A' + row) + " ";
            for (int col = 0; col < PUZZLE_SIZE; col++) {
                for (int i = 0; i < PUZZLE_SIZE; i++)
                    if (pos[col][row][i] != 0)
                        s += pos[col][row][i];
                    else
                        s += " ";
                s += "   ";
            }
            s += "\n";
        }
        return s;
    }

    public boolean isValid(int[][] puzzle) {
        for (int row = 0; row < PUZZLE_SIZE; row++)
            for (int col = 0; col < PUZZLE_SIZE; col++)
                if (! isPossible(col, row, puzzle[row][col]))
                    return false;
        return true;
    }

    public void makePossible(int col, int row, int element) {
        pos[col][row][element-1] = element;
    }


    public  void checkSingles(int row) {
        int cellsCnt[] = new int[PUZZLE_SIZE];   // count of elements in cells
        int elsCnt[] = new int[PUZZLE_SIZE];     // total count of elements in row
        int elements[] = new int[PUZZLE_SIZE];   // one element of each cell
        int elCells[] = new int[PUZZLE_SIZE];    // one cell of each element

        for (int i = 0; i < PUZZLE_SIZE; i++) {
            cellsCnt[i] = 0;
            elsCnt[i] = 0;
            elements[i] = 0;
            elCells[i] = 0;
        }

        // check if there is only one element left in cell(col, row)
        for (int col = 0; col < PUZZLE_SIZE; col++)
            for (int i = 0; i < PUZZLE_SIZE; i++) {
                if (pos[col][row][i] != 0) {
                    elsCnt[i]++;
                    elCells[i] = col;
                    cellsCnt[col]++;
                    elements[col] = i + 1;
                }
            }

        boolean changed = false;

        // check for cells with single element
        for (int col = 0; col < PUZZLE_SIZE; col++) {
            if ((cellsCnt[col] == 1) && (elsCnt[elements[col] - 1] != 1)) {
                // there is only one element in cell but it used somewhere else
                int e = elements[col] - 1;
                for (int i = 0; i < PUZZLE_SIZE; i++)
                    if (i != col)
                        pos[i][row][e] = 0;
                changed = true;
            }
        }

        // check for single element without exclusive cell
        for (int el = 0; el < PUZZLE_SIZE; el++)
            if ((elsCnt[el] == 1) && (cellsCnt[elCells[el]] != 1)) {
                int col = elCells[el];
                for (int i = 0; i < PUZZLE_SIZE; i++)
                    if (i != el)
                        pos[col][row][i] = 0;
                changed = true;
            }

        if (changed)
            checkSingles(row);
    }

}
