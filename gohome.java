
import java.util.*;
public class gohome{

public static boolean guest = false;
public static boolean openthedoor(){
		System.out.println("Hi there, you have arrived in the front door of the house.");
		System.out.println("\nRight now, you have to tell me the codes if you want to get in the house, or if you are "
				+ "a guest, please answer \"I am a guest.\" Thank you very much.");
		String code = new Scanner(System.in).nextLine();
		if(code.equals("knock knock")){
			System.out.println("Hello owner, welcome home.");
			System.out.println("\nWhat would you like to do next, owner?");
			return true;
		}
		else if(code.equalsIgnoreCase("I am a guest")){
			System.out.println("Hello, welcome to my owner's home, I will tell him you are coming. Please wait for a monment.");
			guest = true;
			return true;
		}
		else{
				System.out.println("Sorry, your codes are wrong!!!!!!!!!!!! Please try again!!!!!!!!!!!!!!!!\n");
		}
		return false;
}

public static void drink(){
	System.out.println("What kind of drinks would you like, owner? Tea, coffee, wine, beer, milk or water?");
	String drinks = new Scanner(System.in).nextLine(); 
	if(drinks.equals("tea")){
		System.out.println("Do you want black tea or green tea?");
		String tea =  new Scanner(System.in).nextLine(); 
		if(tea.equals("black tea"))
			System.out.println("Here comes the splendid Chinese black tea, please enjoy it.");
		else if(tea.equals("green tea"))
			System.out.println("Here comes the fragrant Laoshan green tea, please enjoy it.");
		else{
			System.out.println("Sorry, we don't have that.  I will buy it tomorrow. ");
		}
	}
	else if(drinks.equals("beer")){
		System.out.println("What kinds of beer do you want? ");
		System.out.println("Qingdao Beer, Wheat Beer, Budweiser Beer, Trappist Beer, Canadian Beer or American Beer.");
		String beer = new Scanner(System.in).nextLine();
		System.out.println("Here comes the nice " + beer +", please enjoy it.");
	}
	else if(drinks.equals("wine")){
		System.out.println("What kinds of wine do you want? ");
		System.out.println("Ice Wine, White Wine, Red Wine, Whisky, Champagne, White Spirits or Vodka.");
		String wine = new Scanner(System.in).nextLine();
		System.out.println("Here comes the exquisite " + wine + " , please enjoy it.");
	}
	else if(drinks.equals("milk")){
		System.out.println("Do you want Chocolate Milk or Skim Milk or whole milk?");
		String milk = new Scanner(System.in).nextLine();
		System.out.println("Here comes the healthy" + milk +" , please enjoy it." );
	}
	else if (drinks.equals("water")){
		System.out.println("Do you want ice water or hot water?");
		String water = new Scanner(System.in).nextLine();
		System.out.println("Here comes the clean " + water + " , please enjoy it.");
	}
	else if (drinks.equals("coffee")){
		System.out.println("Do you want to add something? (Yes or no)");
		String coffee = new Scanner(System.in).nextLine();
		if (coffee.equals("yes")){
			System.out.println("What do you want to add?");
			String coffee2 = new Scanner(System.in).nextLine();
			System.out.println("Here comes a cup of hot coffee with "+ coffee2);
		}
		else{
			System.out.println("Here comes a cup of hot coffee.");
		}
	}
	else{
		System.out.println("Sorry, we don't have that. >_<  I will buy it tomorrow. ");
	}
}

//sleep
public static void gotosleep(){
	System.out.println("Owner, you must be tired. Ok, your bedroom is ready, you can go to sleep.");
	System.out.println("Have a nice dream, owner! See you tomorrow!");
	System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
			"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	try {
		Thread.sleep(14000);                 //1000 milliseconds is one second.
	} catch(InterruptedException ex) {
		Thread.currentThread().interrupt();
	}
	System.out.println("Bi-Bi, Bi-Bi. Owner, it's time to wake up.");
	Calendar   today = Calendar.getInstance();
	System.out.println("Today is "+ today.getTime());
	System.out.println("You can take a shower and brush your teeth.");
}
//TV
public static void watchtv(){
	System.out.println("Well, what kinds of TV program would you like to watch?");
	String tv = new Scanner(System.in).nextLine();
	System.out.println("Ok, please wait for a second. Let me find it." );
	try {
		Thread.sleep(4000);                 //1000 milliseconds is one second.
	} catch(InterruptedException ex) {
		Thread.currentThread().interrupt();
	}
	System.out.println("Here we go. These are the "+ tv +" programs, please have fun with them.");
}
//eat
public static void meal(){
	System.out.println("Owner, I know you are hungry. What do you want to eat?");
	String food = new Scanner(System.in).nextLine();
	System.out.println("Ok, please wait for a few seconds. The meal is cooking.");
	try {
		Thread.sleep(10000);                 //1000 milliseconds is one second.
	} catch(InterruptedException ex) {
		Thread.currentThread().interrupt();
	}
	
	System.out.println("Thanks for your patience. Your delicious meal is ready, please enjoy it. Hope everything is fine!");
	System.out.println("If you have any order, just tell me, please. (yes or no)");
	String order = new Scanner(System.in).nextLine();
	
	if(order.equals("yes")){
		System.out.println("Could you please tell me what you want me to do?");
		String order2 = new Scanner(System.in).nextLine();
		System.out.println("Ok, I will  do it. Please wait.");
		
		try {
			Thread.sleep(6000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		System.out.println("Thanks for you patience. Your order is here.");
	}
	else {
		System.out.println("That is fine. Please enjoy your meal.");
	}
}

public static void listentomusics(){
	System.out.println("Well, there are hot musics, new musics, classical musics, rock and roll, pure musics and country musics.");
	System.out.println("Which one would you like to choose?");
	String music = new Scanner(System.in).nextLine();
	System.out.println("Alright, Let us listen to the " + music+".");
}

public static void readnews(){
	System.out.println("Here is today's newspaper, please enjoy it. Thank you very much.");
}

public static void internet(){
	System.out.println("Your computer is ready, what kinds of website you want to look at?");
	String web = new Scanner(System.in).nextLine();
	System.out.println("OK. Here are the websites that you want to look at.");
}

public static void playgames(){
	System.out.println("What kinds of game would you like to play?");
	String game = new Scanner(System.in).nextLine();
	System.out.println(game + " is an awesome game. Alright, let us play it.");
}

public static void dohousework(){
	System.out.println("Well, could you please tell me where do you think it is dirty?");
	String housework = new Scanner(System.in).nextLine();
	System.out.println("Alright, I will clean the "+housework + " right now. Please give me a few minute.");
	try {
		Thread.sleep(10000);                 //1000 milliseconds is one second.
	} catch(InterruptedException ex) {
		Thread.currentThread().interrupt();
	}
	System.out.println("Owner, I finished the housework, pleace check it whether clean or not. Thanks!");
}

public static void work(){
	System.out.println("The studying room is ready. You can work as long as you wish.");
	System.out.println("Please remember, your health is our concern.");
	System.out.println("Therefore, please control your working time.");
}
public static void exercise(){
	System.out.println("Well, Owner. Doing exercise is always good for your health.");
	System.out.println("Alright, please follow the rules. ");
	try {
		Thread.sleep(3000);                 //1000 milliseconds is one second.
	} catch(InterruptedException ex) {
		Thread.currentThread().interrupt();
	}
	System.out.println("\nFirst of all, 30 push ups and 20 sit-ups for 3 groups.");
	System.out.println("Then, doing plank for 3 groups. Each group is 60 seconds.");
	System.out.println("Lastly, run 5kms and walk 2kms on the treadmills.");
}
public static void chat(){
	System.out.println("What do you want to talk about, owner? (Please tell me the number.)");
	System.out.println("1. Love");
	System.out.println("2. The meaning of life");
	System.out.println("3. Humour");
	System.out.println("4. Arts");
	System.out.println("5. Others");
	int talk = new Scanner(System.in).nextInt();
	if(talk == 1){
		System.out.println("Alright, let us talk about love.");
		System.out.println("Love is a variety of different feelings, states, and attitudes that ranges from interpersonal affection to pleasure. It can refer to an emotion of a strong attraction and personal attachment. ");
		System.out.println("If you have someone to love, that is so great. Love is priceless, so treasure it.");
	}
	else if(talk ==2){
		System.out.println("Well, lots of people like to talk about this question, becase there is no right or wrong answer.");
		System.out.println("I think the meaning of life is trying to solve millions of problems in one's life, once you learned something, your life will be different. Do you agree? (yes or no)");
		String a = new Scanner(System.in).nextLine();
		if(a.equals("yes")){
			System.out.println("Great minds think alike.");
		}
		else{
			System.out.println("Please tell me what are you thinking?");
			String b = new Scanner(System.in).nextLine();
			System.out.println("As the saying goes, \"One thousand readers, there are one thousand Hamlet.\"");
			System.out.println("So, everybody has different thoughts about this topic. You will have a good life, owner.");
		}       
	}
	else if (talk == 3) {
			System.out.println("Humour is the tendency of particular cognitive experiences to provoke laughter and provide amusement.");
			System.out.println("Would you like to hear a humourous stories? (yes or no)");
			String joke = new Scanner(System.in).nextLine();
			if (joke.equals("yes")){
				System.out.println("Here comes one.");
				System.out.println("What do programmers always say to their programs?");
				try {
					Thread.sleep(3000);                 //1000 milliseconds is one second.
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
	
				System.out.println("First one.     WTF... Why don't you work, bitch?");
				System.out.println("Second one.    What??? It works!!! Unbeliable!!! \n Hope you enjoy it.");
			      		}
			else{
				System.out.println("Ok, that is fine. You must think I am not humourous, right?");
			}
	}
	else if (talk == 4){
		System.out.println("Art is beautiful. Have you ever seen the beauty of arts?");
		System.out.println("It is magnificent. The nature is art, the weather is art. Art is everywhere.");
		System.out.println("Appreciating art is an important skill for everybody, art is pretty much like ou life.");
	}
	else if (talk == 5){
		System.out.println("Well, please tell me what do you want to talk?");
		String c = new Scanner(System.in).nextLine();
		System.out.println("I see." + c + "  is a good topic, but I never think about it. Please don't think too much, owner. ");
		System.out.println("Just be yourself. Don't give up, because Hope still exsits. Please remember, don't get lost on your life. BE the Best and Do the Best.");
	}
}
public static void daydream(){
	System.out.println("Well, owner. Please give a number(1 - 10).");
	int num = new Scanner(System.in).nextInt();
	if(num == 1){
		System.out.println("Imagine that you are the boss of APPLE Company, what are you going to do?");
	}
		if(num == 2){
			System.out.println("Imagine that you are dating with a beautiful girl, what are you going to do?");
		}
		if(num == 3){
			System.out.println("Imagine that you are a bird, what are you going to do?");
		}
		if(num == 4){
			System.out.println("Imagine that you are the president of USA, what are you going to do?");
		}
		if(num == 5){
			System.out.println("Imagine that you have 10 million dollars, what are you going to do?");
		}
		if(num == 6){
			System.out.println("Imagine that you can go back to the past, what are you going to do?");
		}
		if(num == 7){
			System.out.println("Imagine that you are travelling the universe, what are you going to do?");
		}
		if(num == 8){
			System.out.println("Imagine that you are in the Antarctic, what are you going to do?");
		}
		if (num == 9){
			System.out.println("Imagine that you have a really hard time and everything goes against you, what are you going to do?");
		}
		if(num == 10){
			System.out.println("Imagine that you are disabled, what are you going to do?");
		}
}
			  
public static void call(){
	System.out.println("Alright, owner. Please tell me the phone number that you want to call.");
	long phone = new Scanner(System.in).nextLong();
	System.out.println("Dialling " + phone + "..................");
	System.out.println("The call is finished.");
}
public static void refreshment(){
	System.out.println("Hi, Owner. What do you want to eat?");
	System.out.println("There are chips, peaunts, chocolates, biscuits, fried chickens, popcorns, Coca Cola, Sprite, Soda, French fries and candies.");
	String snacks = new Scanner(System.in).nextLine();
	System.out.println("Here comes the tasty "+ snacks + " , please enjoy it.");
}

public static void learn(){
	System.out.println("Mandarin course is very useful in 21st century, now please follow me to pronounce some words.");
	System.out.println("你好， 谢谢，再见。");
	try {
		Thread.sleep(5000);                 //1000 milliseconds is one second.
	} catch(InterruptedException ex) {
		Thread.currentThread().interrupt();
	}
	System.out.println("Which sentences of the following would you like to translate？");
	System.out.println("1. Hello, My name is ....");
	System.out.println("2. Nice to meet you.");
	System.out.println("3. Thank you very much.");
	System.out.println("4. You are welcome.");
	System.out.println("5. What is your name?");
	System.out.println("6. How much is it?");
	System.out.println("7. I love my family.");
	System.out.println("8. Long time no see, how are you?");
	System.out.println("9. See you later.");
	System.out.println("10. Good morning/afternoon/evening/night. ");
	int china = new Scanner(System.in).nextInt();
	if(china == 1 ){
		System.out.println("你好， 我的名字叫....");
	}
	if(china == 2){
		System.out.println("很高兴认识你。");
	}
	if(china == 3){
		System.out.println("非常感谢你。");
	}
	if(china == 4){
		System.out.println("不客气。");
	}
	if(china == 5){
		System.out.println("你叫什么名字？");
	}
	if(china == 6){
		System.out.println("多少钱？");
	}
	if(china == 7){
		System.out.println("我爱我的家庭。");
	}
	if(china == 8){
		System.out.println("好久不见,你最近怎么样？");
	}
	if(china == 9){
		System.out.println("再见。");
	}
	if(china == 10){
		System.out.println("早上好/下午好/晚上好/晚安。");
	}
}
public static void shutup(){
	
	String a = new Scanner(System.in).nextLine();
    if(a.equals("")){
    	System.out.println("Hello, owner. It is nice to see you again.");
    }
    else{
    	System.out.println("Sorry, your codes are wrong, please enter again.");
    	shutup();
    }
	
}
public static void leave(){
	System.out.println("Goodbye, owner. I will keep your home safe and neat.");
	System.out.println("Have a nice day! Bye Bye!");
}
public static void choose(){
	
	while(true){

		String input2 = new Scanner(System.in).nextLine();
		
		if(input2.equals("go to sleep")){
			gotosleep();
		}
		else if(input2.equals("watch tv")){
			watchtv();
		}
		else if(input2.equals("have a meal")){
			meal();
		}
		else if(input2.equals("listen to music")){
			listentomusics();
		}
		else if(input2.equals("read newspapers")){
			readnews();
		}
		else if(input2.equals("surf the internet")){
			internet();
		}
		else if(input2.equals("play games")){
			playgames();
		}
		else if(input2.equals("do housework")){
			dohousework();
		}
		else if(input2.equals("have a drink")){
			drink();
		}
		else if(input2.equals("work")){
			work();
		}
		else if(input2.equals("doing exercises")){
			exercise();
		}
		else if(input2.equals("chat with you")){
			chat();
		}
		else if(input2.equals("daydream")){
			daydream();
		}
		else if(input2.equals("shut up")){
			System.out.println("I am sorry for the bothering. Now, I am going to shutting down. ");
		    System.out.println("If you need me, you can call me anytime.");
		    System.out.println("Please remember that enter the codes to wake me up. Bye Bye!");
	        shutup();
		}
		else if(input2.equals("call someone")){
			call();
		}
		else if(input2.equals("refreshment")){
			refreshment();
		}
		else if(input2.equals("learn mandarin")){
			learn();
		}
		else if(input2.equals("leave the house")){
			leave();
			break;
		}
		else{
			System.out.println("Sorry, I don't understand. Please speak again. Thank you very much. ");
		}
	}
}

public static void main(String[] args){
	
	while(openthedoor() == false){		
	}
	if(guest== false){
		choose();
	}
}	
}

