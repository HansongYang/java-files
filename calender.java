import java.util.Scanner;
public class calender {
	public static int days = 0;
	public static int weekday = 0;
	public static String [] zodiac = {"Pig","Rat","Ox","Tiger","Rabbit","Dragon","Snake","Horse","Goat","Monkey","Rooster","Dog"};
	
	public static boolean isLeapYear(int y){
		if((y % 4 == 0 && y % 100 != 0) || (y % 400 == 0)){
			return true; 
			}
		return false;
	}
	
	public static int firstDay(int y) {
		long n = y * 365;
		for (int i = 1; i < y; i++)
		if (isLeapYear(i))
		n += 1;
		return (int) n % 7;
		}
	
	public static String cz(int year){
		int a = 0;
		a = (year + 9)%12;
		return zodiac[a];
	}
	
	public static void year(int year){		
		int weekday = firstDay(year);

		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println("                        "+ year + " The year of " + cz(year));

		for(int i = 1;i < 13; i++){
			switch(i) {
			case 2: if(isLeapYear(year))days = 29; else days = 28; break;
			case 4:
			case 6:
			case 9:
			case 11: days = 30; break;
			default: days = 31;
			}
			

			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println();
			System.out.println("Month: "+i);
			System.out.println();
		
			System.out.println("Su\t" +"Mo\t"+"Tu\t" +"We\t" +"Th\t" +"Fr\t"+"Sa");
			for(int k = 1; k <= weekday; k++){
				System.out.print("\t");
			}
			
			for(int j = 1;j <= days; j++){
				System.out.print(j + "\t");
				weekday = (weekday + 1) % 7;
				if(weekday == 0){
					System.out.println();
					}
				}				
				System.out.println();
		}
	}
	
	public static void main(String[]args){
		System.out.println("Please input a year:");
		Scanner input = new Scanner(System.in);
		int year = input.nextInt();
		year(year);
	}
}
