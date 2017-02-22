/**
 * @author Sang Agung R.P.
 */

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

public class LabEnvironment{
	
	public static Action ATAS = new DynamicAction("Kiri");

	public static Action BAWAH = new DynamicAction("Bawah");

	public static Action KIRI = new DynamicAction("Kiri");

	public static Action KANAN = new DynamicAction("Kanan");

	public static Action AMBIL = new DynamicAction("Ambil");

	private boolean[][] obstacles;

	private boolean[][] items;

	private Coordinate currCoordinate;

	private int itemAmount;

	public LabEnvironment(int xDimension, int yDimension)
	{
		obstacles = new boolean[xDimension][yDimension];
		items = new boolean[xDimension][yDimension];

		for(int i = 0; i < obstacles.length; i++){
			for(int j = 0; j < obstacles[i].length; j++){
		 		obstacles[i][j] = false;
		 		items[i][j] = false;
		 	}	
		}
	
		itemAmount = 0;

		currCoordinate = new Coordinate(0, 0);
	}

	public LabEnvironment(boolean[][] obstacles, boolean[][] items, Coordinate currCoordinate, int itemAmount){
		this.obstacles = new boolean[obstacles.length][obstacles[0].length];
		this.items = new boolean[items.length][items[0].length];

		for(int i = 0; i < obstacles.length; i++){
			for(int j = 0; j < obstacles[i].length; j++){
		 		this.obstacles[i][j] = obstacles[i][j];
		 		this.items[i][j] = items[i][j];
		 	}	
		}
		this.currCoordinate = new Coordinate(currCoordinate.getX(), currCoordinate.getY());
		this.itemAmount = itemAmount;
	}

	public void printLab(){
		for(int i = 0; i < obstacles.length; i++){
			for(int j = 0; j < obstacles[i].length; j++){
				
				if ((currCoordinate.getX() == i) && (currCoordinate.getY() == j)) {
					System.out.print("J");
				} 
				if(obstacles[i][j]){
					System.out.print("# ");
				} else if (items[i][j]) {
					System.out.print("I ");
				} else {
					System.out.print("_ ");
				}
		 	}	
		 	System.out.println();
		}
	} 

	public void setObstacle(int xCoord, int yCoord){
		obstacles[xCoord][yCoord] = true;	
	}

	public void setObstacle(Coordinate coord) {
		obstacles[coord.getX()][coord.getY()] = true;
	}

	public boolean hasObstacle(int xCoord, int yCoord) {
		return obstacles[xCoord][yCoord];
	}

	public boolean hasObstacle(Coordinate coord) {
		return obstacles[coord.getX()][coord.getY()];
	}

	public void setItem(int xCoord, int yCoord){
		items[xCoord][yCoord] = true;
		itemAmount++;
	}

	public void setItem(Coordinate coord) {
		items[coord.getX()][coord.getY()] = true;
	}

	public boolean hasItem(int xCoord, int yCoord) {
		return items[xCoord][yCoord];
	}

	public boolean hasItem(Coordinate coord) {
		return items[coord.getX()][coord.getY()];
	}

	public boolean canMove(Action where){
		boolean retVal = false;
		int x = getCurXCoord();
		int y = getCurYCoord();

		if (where.equals(ATAS)) {
			retVal = (x > 0) && !obstacles[x-1][y];
		}
		else if (where.equals(BAWAH)) {
			retVal = (x < obstacles.length-1 && !obstacles[x+1][y]);
		}
		else if (where.equals(KIRI)) {
			retVal = ((y > 0) && !obstacles[x][y-1]);
		}
		else if (where.equals(KANAN)) {
			retVal = (y < obstacles[x].length-1 && !obstacles[x][y+1]);
		}
		else if (where.equals(AMBIL)) {
			retVal = hasItemHere();
		}
		return retVal;
	}

	public boolean equals(Object le) {
		if (this == le) {
			return true;
		}
		if ((le == null) || (this.getClass() != le.getClass())) {
			return false;
		}
		LabEnvironment temp = (LabEnvironment) le;

		for (int i = 0; i < obstacles.length; i++) {
			for (int j = 0; j < obstacles[i].length; j++){
				if ((obstacles[i][j] != temp.hasObstacle(i, j)) 
					&& (items[i][j] != temp.hasItem(i, j))) {
					return false;
				}
			}
		}
		if(itemAmount != temp.getItemAmount()){
			return false;
		}
		if(currCoordinate != temp.getCurCoordinate()){
			return false;
		}

		return true;
	}

	public void setCurrentLocation(int xCoordinate, int yCoordinate) {
		currCoordinate = new Coordinate(xCoordinate, yCoordinate);
	}

	public void moveUp(){
		int x = currCoordinate.getX();
		int y = currCoordinate.getY();

		setCurrentLocation(x-1, y);
	}

	public void moveDown(){
		int x = currCoordinate.getX();
		int y = currCoordinate.getY();

		setCurrentLocation(x+1, y);
	}

	public void moveLeft(){
		int x = currCoordinate.getX();
		int y = currCoordinate.getY();

		setCurrentLocation(x, y-1);
	}

	public void moveRight(){
		int x = currCoordinate.getX();
		int y = currCoordinate.getY();

		setCurrentLocation(x, y+1);
	}

	public void pickUp() {
		int x = currCoordinate.getX();
		int y = currCoordinate.getY();

		if(items[x][y]) {
			items[x][y] = false;
		}
		--itemAmount;
	}

	public int getItemAmount(){
		return itemAmount;
	}

	public boolean hasItemHere(){
		return items[currCoordinate.getX()][currCoordinate.getY()];
	}

	public Coordinate getCurCoordinate(){
		return currCoordinate;
	}

	public boolean[][] getObstacles(){
		return obstacles;
	}

	public boolean[][] getItems(){
		return items;
	}	

	public int getCurXCoord(){
		return currCoordinate.getX();
	}

	public int getCurYCoord(){
		return currCoordinate.getY();
	}

	public int getXDimension(){
		return obstacles.length;
	}

	public int getYDimension(){
		return obstacles[0].length;
	}
}