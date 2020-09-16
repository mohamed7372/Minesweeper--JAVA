package minesweeper;

import java.util.ArrayList;
import java.util.Random;

public class Mine {
    int bomb;
    ArrayList<Spot> spots;
    ArrayList<Spot> spotsTrie;
    char[][] state = new char[9][9];
    char[][] stateView = new char[9][9];
    char[][] stateGame = new char[9][9];
    {
        // empty case
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                state[i][j] = '.';
            }
        }

        for (int i = 0; i < stateGame.length; i++) {
            for (int j = 0; j < stateGame[0].length; j++) {
                stateGame[i][j] = '.';
            }
        }
    }
	
    // constructor
    Mine(int bomb) {
        this.bomb = bomb;

        // bomb case
        setBomb();
        // for find all empty case
        spots = new ArrayList<Spot>();
        spotsTrie = new ArrayList<Spot>();
    }
    // random bomb
    private void setBomb() {
        Random random = new Random(1);
        int[] arr = new int[bomb];
        arr[0] = random.nextInt(81) + 1;
        for (int i = 0; i < arr.length;) {
            int nbr = random.nextInt(81) + 1;
            boolean tr =false;
            for (int j = 0; j < i; j++) {
                if (nbr == arr[j]) {
                    tr = true;
                    break;
                }
            }
            if (!tr) {
                arr[i] = nbr;
                i++;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            int k = 0;
            boolean tr = false;
            for (int j = 0; j < state.length && !tr; j++) {
                for (int j2 = 0; j2 < state[0].length && !tr; j2++) {
                    k++;
                    if (k == arr[i]) {
                        state[j][j2] = 'X';
                        tr = true;
                    }
                }
            }
        }
    }

    private void copyArr() {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                stateView[i][j] = state[i][j];
            }
        }
    }
    // calculate nbr of bomb around this case
    private int nbrBombAround(int i, int j) {
        int nbr = 0;
        if (i - 1 != -1) 
                nbr = state[i - 1][j] == 'X' ? nbr + 1 : nbr + 0;
        if (i + 1 != state.length) 
                nbr = state[i + 1][j] == 'X' ? nbr + 1 : nbr + 0;

        if (j - 1 != -1) 
                nbr = state[i][j - 1] == 'X' ? nbr + 1 : nbr + 0;
        if (j + 1 != state.length) 
                nbr = state[i][j + 1] == 'X' ? nbr + 1 : nbr + 0;

        if (i - 1 != -1 && j - 1 != -1) 
                nbr = state[i - 1][j - 1] == 'X' ? nbr + 1 : nbr + 0;
        if (i + 1 != state.length && j + 1 != state.length) 
                nbr = state[i + 1][j + 1] == 'X' ? nbr + 1 : nbr + 0;

        if (i - 1 != -1 && j + 1 != state.length) 
                nbr = state[i - 1][j + 1] == 'X' ? nbr + 1 : nbr + 0;
        if (i + 1 != state.length && j - 1 != -1) 
                nbr = state[i + 1][j - 1] == 'X' ? nbr + 1 : nbr + 0;

        return nbr;
}
    void setValuesAroundCase() {
        int alph = 48;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] == '.') {
                    int nbr = nbrBombAround(i, j);
                    if (nbr == 0) 
                        state[i][j] = '.';
                    else 
                        state[i][j] = (char)(nbr + alph);
                }
            }
        }
        // faire une copu de state orginal de mine
        copyArr();
    }

    // check if win or not
    boolean isWinner() {
        boolean win = true;
        // first method to win
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((state[i][j] == 'X' && stateGame[i][j] != '*') || (state[i][j] != 'X' && stateGame[i][j] == '*'))
                    win = false;
            }
        }
        if (win)
            return true;

        win = true;
        // second method to win
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (state[i][j] == '.' && stateGame[i][j] == '.')
                    win = false;
            }
        }
        return win;
    }

    // set first value not lost first time play
    void setFirstValueFreeAt(int x, int y) {
    	boolean tr = false;
    	if (state[--y][--x] == 'X') {
    		state[y][x] = '.';
    		for (int i = 0; i < state.length && !tr; i++) {
				for (int j = 0; j < state[0].length && !tr; j++) {
					if (state[i][j] == '.' && i != y && j != x) {
						state[i][j] = 'X';
						tr = true;
					}
				}
			}
    	}
    	setValueFreeAt(++x, ++y);
    }
    // set/unset for mine
    void setValueMineAt(int x, int y) {
        if (x > 0 && x <= 9 && y > 0 && y <= 9) {
            char ch = stateGame[--y][--x];
            if (ch == '.' || ch == '*') 
                stateGame[y][x] = ch == '.' ? '*' : '.';
            print();
        }
    }
    // set/unset for free
    boolean setValueFreeAt(int x, int y) {
        if (x > 0 && x <= 9 && y > 0 && y <= 9) {
            if (state[y - 1][x - 1] == 'X') {
                showBomb();
                print();
                System.out.println("You stepped on a mine and failed!");
                return true;
            }
            else {
                setEmptyCase(x - 1, y - 1, spots, spotsTrie);
                print();
            }
        }
        return false;
    }
    // set '/' on the free case
    void setEmptyCase(int x, int y, ArrayList<Spot> spots, ArrayList<Spot> spotsTrie) {
        int nArr = nbrBombAround(y, x);
        // if found a number 
        if (nArr != 0) {
            stateGame[y][x] = (char)(48 + nArr);
        }
        else {
            // determine empty case                    
            for (int i = y - 1; i <= y + 1; i++) {
                for (int j = x - 1; j <= x + 1; j++) {
                    if (i >= 0 && i < 9 && j >= 0 && j < 9) {
                        // case choise (clicked)
                        if(i == y && j == x) {
                            stateGame[i][j] = '/';
                            if (!existe(spots, new Spot(i, j)) && !existe(spotsTrie, new Spot(i, j))) {
                                spotsTrie.add(new Spot(i,j));
                            }
                            continue;
                        }
                        // save and make all empty case in arr
                        nArr = nbrBombAround(i, j);
                        if (nArr == 0){
                            //stateGame[i][j] = '/';
                            if (!existe(spots, new Spot(i, j)) && !existe(spotsTrie, new Spot(i, j))) {
                                spots.add(new Spot(i,j));
                            }
                        }
                        else{
                            // en cas trouve bomb around
                            stateGame[i][j] = (char)(48 + nArr);
                            if (!existe(spots, new Spot(i, j)) && !existe(spotsTrie, new Spot(i, j))) {
                                spotsTrie.add(new Spot(i,j));
                            }
                        }
                    }
                }
            }
        }
       
        if(!spots.isEmpty()) {
            Spot nextPoint = spots.get(0);
            spots.remove(0);
            setEmptyCase(nextPoint.getY(), nextPoint.getX(), spots, spotsTrie);
        }
    }
    // verfier exist dans une Deque
    private boolean existe(ArrayList<Spot> arr, Spot s) {
        for (int i = 0; i < arr.size(); i++) 
            if (s.equals(arr.get(i))) 
                return true;
        return false;
    }

    // set bomb on 'stateGame'
    private void showBomb() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (state[i][j] == 'X')
                    stateGame[i][j] = 'X';
            }
        }
    }
    // print state
    void print() {
        System.out.println("\n |123456789|");
        System.out.println("—|—————————|");
        for (int i = 0; i < stateGame.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < stateGame[0].length; j++) {
                System.out.print(stateGame[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("—|—————————|");
    }
}
