public class TicTacToeGame{
  
  //
  //
  //  You are not allowed to change anything in this class 
  //  except the main method for your own testing. 
  //  
  //
  
  // The board is a NxN grid, where the default dimension is N=3.
  // We label the 9 positions in the 3x3 grid as follows
  //
  //     0 | 1 | 2
  //    ---+---+---
  //     3 | 4 | 5 
  //    ---+---+---
  //     6 | 7 | 8 
  //
  // For larger dimensions, the numbering of positions follows
  // the same pattern. 0, 1, ..., N-1 on the first row, then 
  // N, N+1, ..., 2N-2 on the second, etc.
  
  
  private char[][] board;      // the actual game board
  private int      dimension;  // the size of the game board
  
  public int getDimension(){ 
    return dimension; 
  }
  
  public TicTacToeGame(){   
    dimension =3 ;   // we play on a 3x3 board by default
    board = new char[dimension][dimension];   
  }
  
  public TicTacToeGame(int d){
    dimension = d;  // no reason why we cannot play on a bigger board 
    board = new char[dimension][dimension];
  }
  
  /** 
   * Returns what (x or o) is located in a given position in the game 
   * ('x', 'o', or '\0')
   *  
   * @param pos is the position in a game. It must be an integer in [0,D-1],  
   * where D = dimension*dimension.
   * @return 'x' if the position in the board has an x, 'o' if the position 
   * in the board has an o and returns '\0' otherwise.
   */
  public char getAtPosition(int pos){
    int r = pos/dimension;  // row in the game
    int c = pos%dimension;  // column in the game
    if( board[r][c] == 'x' ){
      return 'x';
    }else if( board[r][c] == 'o' ){
      return 'o';
    }
    return '\0';
  }
  
  /** 
   * A string representation of a tic-tac-toe game at a given instance of time
   * 
   * @return A string representation of the current game.
   */
  public String show(){
    String s = "";
    for(int i=0; i<dimension*dimension; i++){
      if( this.getAtPosition(i) == '\0' ){
        s += "  ";
      }else{
        s += this.getAtPosition(i);
      }
      if( i%dimension == dimension-1){
        s += "\n";
      }else{
        s += "|";
      }
      if( i%dimension == dimension-1 && i/dimension < dimension-1){
        for(int j=0; j<dimension-1; j++){
          s += "-+";
        }
        s += "-\n";
      }
      
    }
    return s;
  }
  
  /** 
   * Play a move on the game.
   * 
   * @param pos is a position [0,D-1] on the game board, where D=dimension*dimension.
   * @param p is a player. 
   * @return Nothing. But, has the side effect of updating the game 
   * board to play a move in position p with symbol (x or y) of player p. 
   * It is assumed that the position is not already occupied with an x or an o.
   */
  public void play(int pos, TicTacToePlayer p){
    // Player p plays a move in position p of the board
    board[pos/dimension][pos%dimension] = p.getXO();

  }
  
  
  public static void main(String[] args){
    //
    // Modify this main method to test your TicTacToePlayer class
    //
    
    TicTacToeGame board = new TicTacToeGame();
    System.out.println(board.show());
    TicTacToePlayer p1 = new TicTacToePlayer("joe", 'x');
    TicTacToePlayer p2 = new TicTacToePlayer("jane", 'o');
    
    while( true ){
      
      if( TicTacToePlayer.gameOver(board) ){
        break;
      }else{
        // player 1 takes a turn
        int pos = p1.findMove(board);
        p1.play(board, pos);
        System.out.println(board.show());
      }
      if( TicTacToePlayer.gameOver(board) ){
        break;
      }else{
        // player 2 takes a turn
        // (same logic as player 1 but written in one line)
        p2.play(board, p2.findMove(board));
        System.out.println(board.show());
      }
      
    } // end while
    
   System.out.println(TicTacToePlayer.winner(board,p1,p2).getName());
    //
    // who won?  test your player class by adding more code
    // 
    
  }  
}