package homework1;

public class problem3 {
	
	// 20200110 정태현
	
	public static void main(String[] args) {
		String s = "XXXXOXXXX";
		// 0 ~ 8 까지 인덱스
		
		while(true) {
			int random_num = (int)(Math.random() * 9); // 0 ~ 8
			
			if(s.charAt(random_num) == 'X') {
				System.out.printf("random number is %d, miss\n", random_num);
			}
			else if(s.charAt(random_num) == 'O') {
				System.out.printf("random number is %d, hit\n", random_num);
				break;
			}
			
		}
		
	}
}
