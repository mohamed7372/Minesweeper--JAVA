package minesweeper;

import java.util.Scanner;

public class Main {
	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.print("How many mines do you want on the field? ");
		int nbrBombs = Integer.valueOf(sc.nextLine());
		
		Mine m = new Mine(nbrBombs);
		m.setValuesAroundCase();
		m.print();
		
		while(!m.isFindAllBomb()) {
			System.out.print("Set/delete mines marks (x and y coordinates): ");
			
			String coord = sc.nextLine();
			String[] nbr = coord.split(" ");
			if (nbr.length == 2) {
				int x = Integer.valueOf(nbr[0]);
				int y = Integer.valueOf(nbr[1]);
				m.setValueAt(x, y);
			}
		}
		System.out.println("Congratulations! You found all mines!");
	}
}
	