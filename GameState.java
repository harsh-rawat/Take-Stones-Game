import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
	private int size; // The number of stones
	private boolean[] stones; // Game state: true for available stones, false for taken ones
	private int lastMove; // The last move
	private boolean isMaxTurn;

	/**
	 * Class constructor specifying the number of stones.
	 */
	public GameState(int size) {

		this.size = size;

		// For convenience, we use 1-based index, and set 0 to be unavailable
		this.stones = new boolean[this.size + 1];
		this.stones[0] = false;

		// Set default state of stones to available
		for (int i = 1; i <= this.size; ++i) {
			this.stones[i] = true;
		}

		// Set the last move be -1
		this.lastMove = -1;
		this.isMaxTurn = false;

	}

	/**
	 * Copy constructor
	 */
	public GameState(GameState other) {
		this.size = other.size;
		this.stones = Arrays.copyOf(other.stones, other.stones.length);
		this.lastMove = other.lastMove;
	}

	/**
	 * This method is used to compute a list of legal moves
	 *
	 * @return This is the list of state's moves
	 */
	public List<Integer> getMoves() {
		
		int count = this.countNumberOfMovesCompleted();
		List<Integer> possibleMoves = new ArrayList<>();
		
		if(count == 0) {
			double size = this.size/2.0;
			int IntegerSize = this.size/2;
			
			int end = size > IntegerSize*1.0 ? IntegerSize : IntegerSize-1;
			for(int move = 1; move <= end; move+= 2) {
				possibleMoves.add(move);
			}
		} else 
			possibleMoves = this.findFactorsAndMultiples(this.lastMove);
		
		return possibleMoves;
	}
	
	private List<Integer> findFactorsAndMultiples(int move){
		
		List<Integer> list = new ArrayList<>();
		for(int i = 1; i < this.stones.length; i++) {
			if(this.stones[i]&&(move % i == 0 || i%move == 0)) list.add(i);
		}
		return list;
	}

	/**
	 * This method is used to generate a list of successors using the getMoves()
	 * method
	 *
	 * @return This is the list of state's successors
	 */
	public List<GameState> getSuccessors() {
		return this.getMoves().stream().map(move -> {
			var state = new GameState(this);
			state.removeStone(move);
			return state;
		}).collect(Collectors.toList());
	}

	/**
	 * This method is used to evaluate a game state based on the given heuristic
	 * function
	 *
	 * @return int This is the static score of given state
	 */
	public double evaluate() {
		double result = -1.0;
		List<Integer> possibleMoves = this.getMoves();
		int count = 0;

		/** Starting -- Implementing conditions of SBE */
		if (possibleMoves.size() == 0) {
			if(isMaxTurn) return 1.0;
			else return -1.0;
	}
		else {
			if (this.stones[1])
				result = 0.0;
			else if (this.lastMove == 1) {
				if (possibleMoves.size() % 2 == 1)
					result = 0.5;
				else
					result = -0.5;
			} else {
				if (Helper.isPrime(this.lastMove)) {
					count = this.getCountOfAvailableMultiples(this.lastMove,possibleMoves);

					if (count % 2 == 1)
						result = 0.7;
					else
						result = -0.7;
				} else {

					count = this.getCountOfAvailableMultiples(Helper.getLargestPrimeFactor(this.lastMove),possibleMoves);

					if (count % 2 == 1)
						result = 0.6;
					else
						result = -0.6;
				}
			}
			

			if (!isMaxTurn)
				return result;
			else
				return (-1.0 * result);
		}

		/** End -- Implementing conditions of SBE */
	}
	
	public int countNumberOfMovesCompleted() {
		int count = 0;
		for (int i = 1; i < this.stones.length; i++) {

			if (!this.stones[i])
				count++;
		}
		
		return count;
	}

	/**
	 * This function is used to obtain the count of multiples of the input which are still available.
	 * */
	private int getCountOfAvailableMultiples(int move, List<Integer> possibleMoves) {

		int count = 0;
		int current = move;

		while (move <= this.size) {
			if (this.stones[move] && possibleMoves.contains(move))
				count++;
			move += current;
		}

		return count;
	}

	/**
	 * This method is used to take a stone out
	 *
	 * @param idx Index of the taken stone
	 */
	public void removeStone(int idx) {
		this.stones[idx] = false;
		this.lastMove = idx;
	}

	/**
	 * These are get/set methods for a stone
	 *
	 * @param idx Index of the taken stone
	 */
	public void setStone(int idx) {
		this.stones[idx] = true;
	}

	public boolean getStone(int idx) {
		return this.stones[idx];
	}

	/**
	 * These are get/set methods for lastMove variable
	 *
	 * @param move Index of the taken stone
	 */
	public void setLastMove(int move) {
		this.lastMove = move;
	}

	public int getLastMove() {
		return this.lastMove;
	}

	/**
	 * This is get method for game size
	 *
	 * @return int the number of stones
	 */
	public int getSize() {
		return this.size;
	}

	public boolean isMaxTurn() {
		return isMaxTurn;
	}

	public void setMaxTurn(boolean isMaxTurn) {
		this.isMaxTurn = isMaxTurn;
	}
	
	

}
