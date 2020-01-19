# Take-Stones-Game
The project involves implementing the Alpha-Beta pruning algorithm to play a two-player game called Take-Stones. 

We have defined the rules of the game as below.
There are 2 players: player 1 (called Max) and player 2 (called Min). For a new game (i.e., no stones have been taken yet), the Max player always plays first. Given a specific game board state, the program computes the best move for the current player using the given heuristic static board evaluation function.

We define the game rules, Static Board evaluation rules and Input/Output format below.

<-- Game Rules -->
The game starts with n stones numbered 1, 2, 3, ..., n. Players take turns removing one of the remaining numbered stones. At a given turn there are some restrictions on which numbers (i.e., stones) are legal candidates to be taken. The restrictions are:
	• At the first move, the first player must choose an odd-numbered stone that is strictly less than n/2. For example, if n = 7 (n/2 = 3.5), the legal numbers for the first move are 1 and 3. If n = 6 (n/2 = 3), the only legal number for the first move is 1.
	• At subsequent moves, players alternate turns. The stone number that a player can take must be a multiple or factor of the last move (note: 1 is a factor of all other numbers). Also, this number may not be one of those that has already been taken. After a stone is taken, the number is saved as the new last move. If a player cannot take a stone, he/she loses the game.

An example game is given below for n = 7:
	Player 1: 3
	Player 2: 6
	Player 1: 2
	Player 2: 4
	Player 1: 1
	Player 2: 7
Winner: Player 2

<------------------------------------>

<-- Static Board Evaluation -->
The static board evaluation function should return values as follows:
• At an end game state where Player 1 (MAX) wins: 1.0
• At an end game state where Player 2 (MIN) wins: -1.0
CS 540 Fall 2019
6
• Otherwise,
o if it is Player 1 (MAX)’s turn:
 If stone 1 is not taken yet, return a value of 0 (because the current state is a relatively neutral one for both players)
 If the last move was 1, count the number of the possible successors (i.e., legal moves). If the count is odd, return 0.5; otherwise, return -0.5.
 If last move is a prime, count the multiples of that prime in all possible successors. If the count is odd, return 0.7; otherwise, return -0.7.
 If the last move is a composite number (i.e., not prime), find the largest prime that can divide last move, count the multiples of that prime, including the prime number itself if it hasn’t already been taken, in all the possible successors. If the count is odd, return 0.6; otherwise, return -0.6.
o If it is Player 2 (MIN)’s turn, perform the same checks as above, but return the opposite (negation) of the values specified above.
