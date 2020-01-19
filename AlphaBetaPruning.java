import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AlphaBetaPruning {

	double score = 0.0;
	private int evaluated = 0;
	private int depth = Integer.MAX_VALUE;
	private int nodesVisited = 0;
	private int move = -1;
	private double branching = 0.0;

    public AlphaBetaPruning() {
    }

    /**
     * This function will print out the information to the terminal,
     * as specified in the homework description.
     */
    public void printStats() {
    	NumberFormat formatter = new DecimalFormat("#0.0");
    	System.out.println("Move: "+move);
    	if(this.score == -0.0) this.score = 0.0;
    	System.out.println("Value: "+this.score);
    	System.out.println("Number of Nodes Visited: "+nodesVisited);
    	System.out.println("Number of Nodes Evaluated: "+evaluated);
    	System.out.println("Max Depth Reached: "+depth);
    	System.out.println("Avg Effective Branching Factor: "+formatter.format(branching));
    }

    /**
     * This function will start the alpha-beta search
     * @param state This is the current game state
     * @param depth This is the specified search depth
     */
    public void run(GameState state, int depth) {
        boolean isMax = state.countNumberOfMovesCompleted() % 2 == 1 ? true : false;
        state.setMaxTurn(isMax);
        
        this.score = alphabeta(state, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, !isMax, true);
        this.depth = depth - this.depth;
        this.branching = (this.nodesVisited - 1.0)/(this.nodesVisited - this.evaluated);
    }

    /**
     * This method is used to implement alpha-beta pruning for both 2 players
     * @param state This is the current game state
     * @param depth Current depth of search
     * @param alpha Current Alpha value
     * @param beta Current Beta value
     * @param maxPlayer True if player is Max Player; Otherwise, false
     * @return int This is the number indicating score of the best next move
     */
    private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer, boolean rootLevel) {
        
    	this.nodesVisited++;
    	if(depth < this.depth) this.depth = depth;
    	if(depth == 0 || state.getMoves().size() == 0) {
    		this.evaluated ++;

    		return state.evaluate();
    	}
    	
    	List<Integer> children = state.getMoves();
    	
    	if(maxPlayer) {
    		for(int child : children) {
    			GameState nextState = new GameState(state);
    			nextState.removeStone(child);
    			nextState.setLastMove(child);
    			nextState.setMaxTurn(true);
    			double score = alphabeta(nextState, depth-1, alpha, beta, false, false);
    			
    			if(score > alpha ) {
    				if(rootLevel) this.move = child;
    				alpha = score;
    			}
    			if(alpha >= beta) return alpha;
    		}
    		return alpha;
    	} else {
    		for(int child : children) {
    			GameState nextState = new GameState(state);
    			nextState.removeStone(child);
    			nextState.setLastMove(child);
    			nextState.setMaxTurn(false);
    			double score = alphabeta(nextState, depth-1, alpha, beta, true, false);
    			
    			if(score < beta ) {
    				if(rootLevel) this.move = child;
    				beta = score;
    			}
    			if(alpha >= beta) return beta;
    		}
    		return beta;
    	}
    	
    }
}