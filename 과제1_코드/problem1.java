package homework1;

public class problem1 {
	
	// 20200110 정태현
	
	public static void main(String[] args) {
		
		int a = 1;
		int sum = 0;
		int i;
		
		for(i=0; i<10; i++) {
			System.out.printf("a%d=%d \n", i, a);

			sum += a;
			a = (a << 1) | 1;  // 2*a+1
		}
		System.out.println("Sum : " + sum);
		
	}

}
