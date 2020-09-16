package minesweeper;

import java.util.Scanner;

public class Main {
	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.print("How many mines do you want on the field? ");
		int nbrBombs = Integer.valueOf(sc.nextLine());
		
		Mine m = new Mine(nbrBombs);
		m.print();
		
		System.out.print("Set/unset mines marks or claim a cell as free: ");
		String coord = sc.nextLine();
		String[] nbr = coord.split(" ");
		if (nbr.length == 3) {
			int x = Integer.valueOf(nbr[0]);
			int y = Integer.valueOf(nbr[1]);
			m.setFirstValueFreeAt(x, y);
		}
		
		boolean gameOver = false;

		while(!m.isWinner() && !gameOver) {
			gameOver = playGame(m);
		}
		if (m.isWinner())
			System.out.println("Congratulations! You found all mines!");
	}
	
	public static boolean playGame(Mine m) {
		boolean gameOver = false;
		System.out.print("Set/unset mines marks or claim a cell as free: ");
		
		String coord = sc.nextLine();
		String[] nbr = coord.split(" ");
		if (nbr.length == 3) {
			int x = Integer.valueOf(nbr[0]);
			int y = Integer.valueOf(nbr[1]);
			String mode = nbr[2];
			
			if (mode.equals("mine"))
				m.setValueMineAt(x, y);
			else if (mode.equals("free")) 
				gameOver = m.setValueFreeAt(x, y);
		}
		return gameOver;
	}
}
	