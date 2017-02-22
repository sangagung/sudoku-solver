

public class Coordinate {
	int x;
	int y;

	// Constructor
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}