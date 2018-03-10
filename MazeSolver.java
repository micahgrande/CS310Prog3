/*  Micah Joseph Grande
    cssc0900
*/

import data_structures.*;

public class MazeSolver {
	
	Queue<GridCell> mazeQueue;
	Stack<GridCell> mazeStack;
	MazeGrid maze;
	int lastCell;
	
	public MazeSolver(int dimension) {
		maze = new MazeGrid(this, dimension);
		mazeQueue = new Queue<GridCell>();
		mazeStack = new Stack<GridCell>();
		lastCell = dimension-1;
	}
	
	public void mark() {
		mazeQueue.enqueue(maze.getCell(0,0));
		maze.getCell(0,0).setDistance(0);
		GridCell center;
		while(!mazeQueue.isEmpty()) {
			center = mazeQueue.dequeue();			
			markCheck(maze.getCell(center.getX(),center.getY()-1),center); //up
			markCheck(maze.getCell(center.getX()-1,center.getY()),center); //left
			markCheck(maze.getCell(center.getX(),center.getY()+1),center); //down
			markCheck(maze.getCell(center.getX()+1,center.getY()),center); //right
		}
	}
	
	private void markCheck(GridCell adjacentCell, GridCell center) {
		if(maze.isValidMove(adjacentCell) && !(adjacentCell.wasVisited())) {
				adjacentCell.setDistance(center.getDistance()+1);
				maze.markDistance(adjacentCell);
				mazeQueue.enqueue(adjacentCell);
		}
	}
	
	public boolean move() {
		GridCell last = maze.getCell(lastCell, lastCell);
		GridCell center, next;
		int distance = last.getDistance();
		if(distance == -1)
			return false;
		mazeStack.push(last);
		while(distance != 0) {
			center = mazeStack.peek();
			next = pathCheck(maze.getCell(center.getX(),center.getY()-1), maze.getCell(center.getX()-1,center.getY())); //up and left
			next = pathCheck(next, maze.getCell(center.getX(),center.getY()+1)); //next and down
			next = pathCheck(next, maze.getCell(center.getX()+1,center.getY())); //next and right
			distance = next.getDistance();
			mazeStack.push(next);
		}
		while(!mazeStack.isEmpty())
			maze.markMove(mazeStack.pop());			
		return true;
	}
	
	private GridCell pathCheck(GridCell one, GridCell two) {
		if(!maze.isValidMove(one) && !maze.isValidMove(two))
			return null;
		if(!maze.isValidMove(one) && maze.isValidMove(two))
			return two;
		if(maze.isValidMove(one) && !maze.isValidMove(two))
			return one;
		if (one.getDistance() < two.getDistance())
			return one;
		return two;
	}
	
	public void reset() {
		mazeQueue.makeEmpty();
		mazeStack.makeEmpty();
	}
	
	public static void main(String [] args) {
		new MazeSolver(50);
	}
}
