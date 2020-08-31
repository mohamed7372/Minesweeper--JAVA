package minesweeper;

import java.util.Random;

public class Mine {
	int bomb;
	private char[][] state = new char[9][9];
	char[][] stateView = new char[9][9];
	{
		// empty case
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				state[i][j] = '.';
			}
		}
	}
	
	// constructor
	Mine(int bomb) {
		this.bomb = bomb;
		
		// bomb case
		setBomb();
	}
	
	void setBomb() {
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
	// set or delete mines
	void setValueAt(int x, int y) {
		if (x > 0 && x <= 9 && y > 0 && y <= 9) {
			String str = "\\d";
			String val = String.valueOf(stateView[--y][--x]);
			if (val.matches(str))
				System.out.println("There is a number here!");
			else {
				char ch = '.';
				if(stateView[y][x] == 'X' || stateView[y][x] == '.')
					ch = '*';
				stateView[y][x] = ch;
				print();
			}
		}
	}
	// if find all bomb or not
	boolean isFindAllBomb() {
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state.length; j++) {
				if ((state[i][j] == 'X' && stateView[i][j] != '*') || (state[i][j] != 'X' && stateView[i][j] == '*'))
					return false;
			}
		}
		return true;
	}
	
	void print() {
		System.out.println("\n |123456789|");
		System.out.println("—|—————————|");
		for (int i = 0; i < stateView.length; i++) {
			System.out.print(i + 1 + "|");
			for (int j = 0; j < stateView[0].length; j++) {
				if (stateView[i][j] == 'X')
					System.out.print(".");
				else
					System.out.print(stateView[i][j]);
			}
			System.out.println("|");
		}
		System.out.println("—|—————————|");
	}
}
