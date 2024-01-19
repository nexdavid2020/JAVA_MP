package homework1;

public class problem2 {
	
	// 20200110 정태현
	
	public static void main(String[] args) {
		
		// 위 역피라미드
		for(int i=0; i<16; i++) {
			
			for(int j=0; j<i; j++) {
				System.out.print(" ");
			}
			
			for(int j=0; j<32-(i*2); j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		
		// 아래 피라미드
		for(int i=0; i<16; i++) {
			
			for(int j=0; j<16-i-1; j++) {
				System.out.print(" ");
			}
			
			for(int j=0; j<2*i+2; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		
		
	}

}
