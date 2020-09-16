package minesweeper;

public class Spot {
	private int x;
	private int y;
	
	public Spot(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Spot [x=" + x + ", y=" + y + "]";
	}
	public boolean equals(Spot s) {
		return getX() == s.getX() && getY() == s.getY();
	}
}
