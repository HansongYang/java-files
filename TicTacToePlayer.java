
public class TicTacToePlayer{
  
  /** 
   * Player's name. The name cannot be changed once it is set by the construtor.
   */
  private final String name;  // DO NOT CHANGE THIS
  private char letter;
  
  /** Getter for the players name
    * 
    * @return the name of the player
    */
  public String getName(){   // DO NOT CHANGE THIS
    return name; 
  }
  
  
  /**
   * Constructor for a tic tac toe player.
   * 
   * @param name is the name of the player.
   * @param p must be either 'x' or 'o'. 
   */
  public TicTacToePlayer(String name, char p){
    //
    // you define this constructor
    //
    letter = p;
    this.name = name;
  }
  /**
   * Checks if a given game instance is over or not.
   * 
   * @param game is an instance of a tic tac toe game (the board)
   * @return true if the game instance is over 
   * (if one player has one or if there is a tie), otherwise false.
   */ 
  public static boolean gameOver(TicTacToeGame game){
    int count = 0;
    int count2 = 0;
    int count3 =0;
    int count4 =0;
    
    //Check the horizontal of the board.
    for(int j = 0; j < game.getDimension() * game.getDimension() ; j += game.getDimension()){
      for(int i = j;  i < game.getDimension() + j; i++){
        if(game.getAtPosition(i) == 'x'){
          count++;
        }
        else if(game.getAtPosition(i) == 'o'){
          count2++;
        }
      }
      //If the count equals to the dimension, then the game is over.
      if(count == game.getDimension()){
        return true;
      }
      else{
        count = 0;
      }
      //If the count2 equals to the dimension, then the game is over.
      if(count2 == game.getDimension()){
        return true;
      }
      else{
        count2 = 0;
      }  
    }
    //Check the vertical of the board.
    for(int j = 0; j < game.getDimension(); j ++){
      for(int i = j;  i < game.getDimension() * game.getDimension() ; i += game.getDimension()){
        if(game.getAtPosition(i)== 'x'){
          count3++;
        }
        else if(game.getAtPosition(i) == 'o'){
          count4++;
        }
      }
      //If the count3 equals to the dimension, then the game is over.
      if(count3 == game.getDimension()){
        return true;
      }
      else{
        count3 = 0;
      }
      //If the count4 equals to the dimension, then the game is over.
      if(count4 == game.getDimension()){
        return true;
      }
      else{
        count4 = 0;
      }      
    }
    
    int count5 =0;
    int count6 = 0;
    int count7 = 0;
    int count8 = 0;
    //Check the diagonal of the board.
    for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){
      if(game.getAtPosition(i)== 'x'){
        count5++;
      }
      else if(game.getAtPosition(i)== 'o'){
        count6++;
      }
    }
    
    for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i-(game.getDimension() -1)){
      if(game.getAtPosition(i) == 'x'){
        count7++;
      }
      else if(game.getAtPosition(i) =='o'){
        count8++;
      }
    }
    
    if(count5 == game.getDimension() || count6 == game.getDimension() || count7 == game.getDimension() || count8 == game.getDimension()){
      return true;
    }
    return false;
  }
  
  /**
   * Checks if a given game instance is over or not.
   * 
   * @param game is an instance of a tic tac toe game (the board)
   * @param p1 is one player in the game
   * @param p2 is the other player in the game
   * @return p1 if player p1 has won the game, p2 if player p2 has 
   * won the game and returns null otherwise (if there is no winner in the given game).
   */
  public static TicTacToePlayer winner(TicTacToeGame game, 
                                       TicTacToePlayer p1,
                                       TicTacToePlayer p2)
  {
    int count = 0;
    int count2 = 0;
    int count3 =0;
    int count4 =0;
    
    //Checking the horizontal of the board.
    for(int j = 0; j < game.getDimension() * game.getDimension() ; j += game.getDimension()){
      for(int i = j;  i < game.getDimension() + j; i++){
        if(game.getAtPosition(i) == 'x'){
          count++;
        }
        else if(game.getAtPosition(i) == 'o'){
          count2++;
        }
      }
      //If the p1 uses 'x', return p1. 
      //If the p2 uses 'o', return p2.
      if(count == game.getDimension()){
        return (p1.isX() ? p1 : p2);
      }
      else {
        count = 0;
      }
      //If the p1 uses 'x', return p1. 
      //If the p2 uses 'o', return p2.
      if(count2 == game.getDimension()){
        return (!p1.isX() ? p1 : p2);
      }
      else {
        count2 = 0;
      }
    }
    //Check the vertical of the board.
    for(int j = 0; j < game.getDimension(); j ++){
      for(int i = j;  i < game.getDimension() * game.getDimension(); i += game.getDimension()){
        if(game.getAtPosition(i)== 'x'){
          count3++;
        }
        else if(game.getAtPosition(i) == 'o'){
          count4++;
        }
      }
      //If the p1 uses 'x', return p1. 
      //If the p2 uses 'o', return p2.
      if(count3 == game.getDimension()){
        return (p1.isX() ? p1 : p2);
      }
      else {
        count3 = 0;
      }
      //If the p1 uses 'x', return p1. 
      //If the p2 uses 'o', return p2.
      if(count4 == game.getDimension()){
        return (!p1.isX() ? p1 : p2);
      }
      else {
        count4 = 0;
      }
    }
    
    int count5 =0;
    int count6 = 0;
    int count7 = 0;
    int count8 = 0;
    //Checking the diagonal of the board.
    for(int i =0; i < game.getDimension() * game.getDimension() ; i = i +game.getDimension() +1){
      if(game.getAtPosition(i)== 'x'){
        count5++;
      }
      else if(game.getAtPosition(i)== 'o'){
        count6++;
      }
    }
    
    for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
      if(game.getAtPosition(i) == 'x'){
        count7++;
      }
      else if(game.getAtPosition(i) =='o'){
        count8++;
      }
    }
    //If the p1 uses 'x', return p1. 
    //If the p2 uses 'o', return p2.
    if(count5 == game.getDimension() || count7 == game.getDimension()){
      return (p1.isX() ? p1 : p2);
    }
    else if (count6 == game.getDimension() || count8 == game.getDimension()){
      return (!p1.isX() ? p1 : p2);
    }
    return null;
  }
  
  
  
  /** 
   * Checks if current player is playing x's or o's.
   * 
   * @param none
   * @return true if this object is playing x's and false if the object is playing o's.
   */
  public boolean isX(){
    if(letter == 'o'){
      return false;
    }
    return true;
  }
  
  /**
   * Getter method that tells if player is playing x's or o's.
   * 
   * @return 'x' if this player is playing x's and returns 'o' if this player is playing o's.
   */
  public char getXO(){
    if(letter == 'x'){
      return 'x';
    }
    return 'o';
  }
  
  
  /**
   * Finds a valid move in a tic-tac-toe game for this player
   * 
   * @param game is an instance of a tic-tac-toe game
   * @return A position in the board [0,D-1] that is a valid move for this player to make, 
   * where D = dimention*dimension. 
   * If there are multiple valid moves any is acceptable. 
   * If there are no valid moves then the function returns -1.
   */
  public int findMove(TicTacToeGame game){
    int position = 0;
    
    for(int i = 0; i < game.getDimension() * game.getDimension() ; i++){
      if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
        position =  i;
        return position;
      }
    }
    return -1;
  }
  
  /**
   * Finds a valid move in a tic-tac-toe game for this player
   * 
   * @param game is an instance of a tic-tac-toe game
   * @return An array of positions in the board [0,D-1] that are each a valid move 
   * for this player to make, where D = dimension*dimension. 
   * All valid moves must be included in the output. 
   * The order of the positions in the output array do not matter. If there are no
   * valid moves then the function return null. 
   */
  public int[] findAllMoves(TicTacToeGame game){
    int position = 0;
    int countSize = 0;
    //First of all, count the size of the array.
    for(int i = 0; i < game.getDimension() * game.getDimension(); i++){
      if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
        countSize++;
      }
    }
    int [] validMoves = new int [countSize];
    int num = 0;
    for(int i = 0; i < game.getDimension() * game.getDimension(); i++){
      if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
        position = i;
        //Putting positions into this array.
        for(int j = num; j < countSize ; j++){
          validMoves[j] = position;
        }
        num++;
        }
    }
       return validMoves;
  }
  
  /**
   * Finds a winning move if possible for this player.
   * 
   * @param game is an instance of a tic-tac-toe game
   * @return A position in the board [0,D-1] that is a valid winning move for this player to make, 
   * where D = dimension*dimension.
   * If there are multiple winning moves then any is acceptable. 
   * Returns -1 if there is no winning move for the player. 
   */
  public int findWinningMove(TicTacToeGame game){
    int position = 0;
    int count = 0;
    int count2 = 0;
    int count3 =0;
    int count4 =0;
    int count5 = 0;
    int count6 = 0;
    int count7 =0;
    int count8 =0;
    //If the player uses 'x'.
    if(isX() == true){
      //Check the horizontal of the board.
      for(int j = 0; j < game.getDimension() * game.getDimension() ; j += game.getDimension()){
        for(int i = j;  i < game.getDimension() + j; i++){
          if(game.getAtPosition(i) == 'x'){
            count++;
          }
        }
        if(count == game.getDimension() -1){
          for(int i = j; i < game.getDimension() +j; i++){
            if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
              position = i;
              return position;
            }
          }
        }
        else{
          count = 0;
        }
      }
      //Check the vertical of the board.
      for(int j = 0; j < game.getDimension(); j ++){
        for(int i = j;  i < game.getDimension() * game.getDimension(); i += game.getDimension()){
          if(game.getAtPosition(i) == 'x'){
            count2 ++;
          }
        }
        if(count2 == game.getDimension() - 1){
          for(int i = j;  i < game.getDimension() * game.getDimension(); i += game.getDimension()){
            if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
              position = i;
              return position;
            }
          }
        }
        else {
          count2 =0;
        }
      }
      
      //Check the diagonal of the board.
      for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){
        if(game.getAtPosition(i) == 'x'){
          count3++;
        }
      }
      
      if (count3 == game.getDimension() -1){
        for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){      
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;
            return position;
          }
        }
      }
      
      for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
        if(game.getAtPosition(i) == 'x'){
          count4++;
        }
      }
      
      if(count4 == game.getDimension() - 1 ){
        for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;
            return position;
          }
        }
      }
    }
    
    //If the player uses 'o'
    else {
      //Check the horizontal of the board.
      for(int j = 0; j < game.getDimension() * game.getDimension() ; j += game.getDimension()){
        for(int i = j;  i < game.getDimension() + j; i++){
          if(game.getAtPosition(i) == 'o'){
            count5++;
          }
        }
        if(count5 == game.getDimension() -1){
          for(int i = j; i < game.getDimension() + j; i++){
            if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
              position = i;
              return position;
            }
          }
        }
        else{
          count5 = 0;
        }
      }
      //Check the vertical of the board.
      for(int j = 0; j < game.getDimension(); j ++){
        for(int i = j;  i < game.getDimension() * game.getDimension(); i += game.getDimension()){
          if(game.getAtPosition(i) == 'o'){
            count6 ++;
          }
        }
        if(count6 == game.getDimension() - 1){
          for(int i = j;  i < game.getDimension() * game.getDimension(); i += game.getDimension()){
            if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
              position = i;
              return position;
            }
          }
        }
        else {
          count6 =0;
        }
      }
      
      //check the diagonal of the board.
      for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){
        if(game.getAtPosition(i) == 'o'){
          count7++;
        }
      }
      
      if (count7 == game.getDimension() -1){
        for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){      
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;
            return position;
          }
        }
      }
      
      for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
        if(game.getAtPosition(i) == 'o'){
          count8++;
        }
      }
      
      if(count8 == game.getDimension() - 1 ){
        for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;
            return position;
          }
        }
      }
    }
    return -1;
  }
  
  /**
   * Finds a blocking move if possible for this player
   * 
   * 
   * @param game is an instance of a tic-tac-toe game
   * @return A position in the board [0,D-1] that is a valid bocking 
   * move for this player to make, where D = dimension*dimension.
   * If there are multiple blocking moves then any is acceptable.
   * Returns -1 if there is no blocking move for this player.
   */
  public int findBlockingMove(TicTacToeGame game){
    int position = 0;    
    int count =0;
    int count2 = 0;
    int count3 =  0;
    int count4 = 0;
    int count5 =0;
    int count6 = 0;
    int count7 =  0;
    int count8 = 0;
    //If the user uses 'x'.
    if(isX() == true){
      //Check the horizontal of the board.
      for(int j = 0; j < game.getDimension() * game.getDimension() ; j += game.getDimension()){
        for(int i = j;  i < game.getDimension() + j; i++){
          if(game.getAtPosition(i) == 'o'){
            count++;
          }
        }
        if(count == game.getDimension() -1){
          for(int k = j; k < game.getDimension() + j; k++){
            if(game.getAtPosition(k) != 'x' && game.getAtPosition(k) != 'o'){
              position = k;
              return position;
            }
          }
        }
        else{
          count = 0;
        }
      }
      //Check the vertical of the board.
      for(int j = 0; j < game.getDimension(); j ++){
        for(int i = j;  i < game.getDimension() * game.getDimension(); i += game.getDimension()){
          if(game.getAtPosition(i) == 'o'){
            count2 ++;
          }
        }
        if(count2 == game.getDimension() - 1){
          for(int k = j;  k < game.getDimension() * game.getDimension(); k += game.getDimension()){
            if(game.getAtPosition(k) != 'x' && game.getAtPosition(k) != 'o'){
              position = k;
              return position;
            }
          }
        }
        else {
          count2 = 0;
        }
      }
      
      //check the diagonal of the board.
      for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){
        if(game.getAtPosition(i) == 'o'){
          count3++;
        }
      }
      
      if (count3 == game.getDimension() -1){
        for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){      
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;
            return position;
          }
        }
      }
      
      for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
        if(game.getAtPosition(i) == 'o'){
          count4++;
        }
      }
      
      if(count4 == game.getDimension() - 1 ){
        for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;
            return position;
          }
        }
      }
    }
    
    else {
      //Check the horizontal of the board.
      for(int j = 0; j < game.getDimension() * game.getDimension() ; j += game.getDimension()){
        for(int i = j;  i < game.getDimension() + j; i++){
          if(game.getAtPosition(i) == 'x'){
            count5++;
          }
        }
        if(count5 == game.getDimension() -1){
          for(int k = j; k < game.getDimension() + j; k++){
            if(game.getAtPosition(k) != 'x' && game.getAtPosition(k) != 'o'){
              position = k;        
              return position;
            }
          }
        }
        else{
          count5 = 0;
        }
      }
      //Check the vertical of the board.
      for(int j = 0; j < game.getDimension(); j ++){
        for(int i = j;  i < game.getDimension() * game.getDimension(); i += game.getDimension()){
          if(game.getAtPosition(i) == 'x'){
            count6 ++;
          }
        }
        if(count6 == game.getDimension() - 1){
          for(int k = j;  k < game.getDimension() * game.getDimension(); k += game.getDimension()){
            if(game.getAtPosition(k) != 'x' && game.getAtPosition(k) != 'o'){
              position = k;          
              return position;
            }
          }
        }
        else {
          count6 =0;
        }
      }
      
      //Check the diagonal of the board.
      for(int i =0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){
        if(game.getAtPosition(i) == 'x'){
          count7++;
        }
      }
      
      if (count7 == game.getDimension() -1){
        for(int i = 0; i < game.getDimension() * game.getDimension() ; i= i+game.getDimension() +1){      
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;        
            return position;
          }
        }
      }
      
      for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
        if(game.getAtPosition(i) == 'x'){
          count8++;
        }
      }
      
      if(count8 == game.getDimension() - 1 ){
        for(int i = game.getDimension() * game.getDimension() - game.getDimension(); i >0; i = i - (game.getDimension() -1)){
          if(game.getAtPosition(i) != 'x' && game.getAtPosition(i) != 'o'){
            position = i;        
            return position;
          }
        }
      }
    }  
    return -1;
  }
  
  
  /** 
   * Plays a move for this player in a game
   * 
   * @param game is a tic-tac-toe game that the player is playing.
   * @param pos is the position [-1,D-1] in the game that the player is playing a move,
   * where D=dimension*dimension.
   * @return Nothing. The function has the side effect of playing a move on the board, 
   * using this player's symbol (x or o) at the specified position. If the position 
   * is -1 then the function does nothing.
   */
  public void play(TicTacToeGame game, int pos){
    if(pos != -1){
      game.play(pos, this);
    }
  }
}