package homework1;

import java.util.Scanner;

public class problem5 {
	
	// 20200110 정태현
	
	public static void main(String[] args) {
		
		int acc_num;
		int balance = 0;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Create your account number : ");
		acc_num = scan.nextInt();
		System.out.println();
		System.out.println();
		
		int input;
		int money;
		
		while(true) {
			System.out.println("-------------------------------------------------");
			System.out.println("1. Deposit | 2. Withdrawal | 3. Balance | 4. Exit");
			System.out.println("-------------------------------------------------");
			System.out.print("Input : ");
			input = scan.nextInt();
			
			if(input == 1) {
				System.out.print("Deposit money : ");
				money = scan.nextInt();
				if(money >= 0) {
					balance += money;
				}else {
					System.out.println("Deposit money should not be negative!");
				}
				
			}else if(input == 2) {
				System.out.print("Withdrawal money : ");
				money = scan.nextInt();
				if(money < 0) {
					System.out.println("Withdrawal money should not be negative!");
					continue;
				}
				
				if(balance >= money) {
					balance -= money;
				}else {
					System.out.println("!! Insuffcient Balance !!");
					System.out.println("Shortage amount : " + (money - balance));
				}
			}else if(input == 3) {
				System.out.println("Balance : " + balance);
			}else if(input == 4) {
				System.out.println();
				System.out.println("Exit Program");
				break;
			}else {
				System.out.println("Wrong Input! Input number should be from 1 to 4!");
			}
		}
		
	}
}
