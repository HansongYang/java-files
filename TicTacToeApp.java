import java.util.Scanner;  

public class TicTacToeApp {  
  public static void main(String[] args){
    Scanner keyboard = new Scanner(System.in);  
    TicTacToePlayer p2;
    TicTacToePlayer p1;
    System.out.println("Hello, what is your name?");
    String name = keyboard.nextLine();
    String letter = "";
    while(true){
      System.out.println("Please choose a letter('x' or 'o').");
      letter = keyboard.nextLine();
      
      //Making two new player, one is the user and another one is the computer.
      p1 = new TicTacToePlayer(name, letter.toLowerCase().charAt(0));
      
      //If the user chooses 'x', then computer will uses 'o'.
      if(letter.toLowerCase().charAt(0) == 'x'){
        p2 = new TicTacToePlayer("Computer", 'o');
        break;
      }
      else if(letter.toLowerCase().charAt(0) == 'o'){
        p2 = new TicTacToePlayer("Computer", 'x');
        break;
      }else{
        System.out.println("Invalid Input! Please Try again!");
      }
    }
    
    //Creating a new game.
    TicTacToeGame game = new TicTacToeGame();
    boolean myTurn = false;
    boolean computerTurn = false;
    int move = 0;
    //Determine who plays first.
    if(p1.isX() == true){
      int position = 0;
      while(true){
        System.out.println("please  choose a position to play (the position will be an integer between 0 and " + (game.getDimension() * game.getDimension()  - 1)+ " ).");
        position = keyboard.nextInt();
        if(position < 0 ||  (game.getDimension() * game.getDimension()-1) < position){
          System.out.println("Sorry, invalid input! Please try again.");
        }
        else{
          break;
        }
      }
      System.out.println("You chose position " + position +" .");
      p1.play(game, position);
      myTurn = true;
      System.out.println(game.show());
    }
    else{
      move = p2.findMove(game);
      p2.play(game,  move);
      System.out.println("The computer played in the position " + move + " .");
      computerTurn = true;
      System.out.println(game.show());
    }
    
    int won = 0;
    int lost = 0;
    int tied = 0;
    while(true){
      //The user's turn
      if(computerTurn == true){
        System.out.println("please  choose a position to play (the position will be an integer between 0 and "+ (game.getDimension() * game.getDimension() -1) + " ).");
        String input = "";
        input = keyboard.next();
        //If the user enters "quit", then the game stops. 
        if(input.equals("quit") ){
          break;
        }
        
        int position;
        while(true){
          //Convert the string input into a integer position.
          position = Integer.parseInt(input);
          if(position < 0 || position > (game.getDimension() * game.getDimension() -1) || !p1.validMove(game, position)){
            System.out.println("Invalid input! Please try again!");
          }
          else{
            break;
          }
           input = keyboard.next();
        }
        
        System.out.println("You chose position " + position +" .");
        p1.play(game, position);
        computerTurn = false;
        myTurn = true;
        System.out.println(game.show());
      }
      
      //Check whether the game is ended or not.
      if(TicTacToePlayer.gameOver(game) == true){
        //If the user wins.
        if(TicTacToePlayer.winner(game,p1,p2).getName() == p1.getName()){
          System.out.println(TicTacToePlayer.winner(game,p1,p2).getName() + " won the game.");
          System.out.println("The gameboard has been enlarged.");
          won++;
          //Starting a new game.
          int dimension = game.getDimension();
          game = new TicTacToeGame(++dimension);
        }
        //If the computer wins.
        else{
          System.out.println("The computer won the game");
          lost++;
          game = new TicTacToeGame();
        }
        computerTurn = p1.isX();
        myTurn = p2.isX();
      }
      
      //If there is a tie.
      if(p1.findMove(game) == -1){
        System.out.println("This is a tied game.");
        tied++;
        game = new TicTacToeGame();
        computerTurn = p1.isX();
        myTurn = p2.isX();
      }
      
      //The computer's turn
      if(myTurn == true){
        if(p2.findBlockingMove(game) == -1){
          move = p2.findMove(game);
          p2.play(game, p2.findMove(game));
          System.out.println("The computer played in the position " +move + " .");
          myTurn = false;
          computerTurn = true;
          System.out.println(game.show());
        }
        else if(p2.findBlockingMove(game) != -1){
          move = p2.findBlockingMove(game);
          p2.play(game, p2.findBlockingMove(game));
          System.out.println("The computer played in the position " +move + " .");
          myTurn = false;
          computerTurn = true;
          System.out.println(game.show());
        }
        else if(p2.findWinningMove(game) != -1){
          move = p2.findWinningMove(game);
          p2.play(game, p2.findWinningMove(game));
          System.out.println("The computer played in the position " +move + " .");
          myTurn = false;
          computerTurn = true;
          System.out.println(game.show());
        }
      }
      
      //Check whether the game is ended or not.
      if(TicTacToePlayer.gameOver(game) == true){
        if(TicTacToePlayer.winner(game,p1,p2).getName() == p1.getName()){
          System.out.println(TicTacToePlayer.winner(game,p1,p2).getName() + " won the game.");
          System.out.println("The gameboard has been enlarged.");
          won++;
          int dimension = game.getDimension();
          game = new TicTacToeGame(dimension++);
        }
        else{
          System.out.println("The computer won the game.");
          lost++;
          game = new TicTacToeGame();
        }
        computerTurn = p1.isX();
        myTurn = p2.isX();
      }
      if(p2.findMove(game) == -1){
        System.out.println("This is a tied game.");
        tied++;
        game = new TicTacToeGame();
        computerTurn = p1.isX();
        myTurn = p2.isX();
      }
    }
    //The summary of all the games.
    System.out.println("The sum of all the games completed are "+ (won + lost + tied) +", the user " + p1.getName() + " won "+ won + " games, lost " + lost+ " games and tied " + tied + " games."  );
  }
}
