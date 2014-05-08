import java.util.Scanner;

public class GetMax {

	public static void main(String[] args) {
		Scanner scaner = new Scanner(System.in);
		int n = scaner.nextInt();
		int[] array = new int[n];
		for(int i=0;i<n;i++){
			array[i] = scaner.nextInt();
		}
		System.out.println(getMax(array));
	}
	
	public static void display(int[] array){
		for(int i=0;i<array.length;i++){
			System.out.println(array[i]);
		}
	}
	public static int getMax(int[] array){
		int length = array.length;
		int j = length -1;
		int i = 0;
		int result = j-i;
		if(array[j]-array[i] >0){
			return result;
		}else{
			while(result-->0){
				int i1=0;
				int j1=i1+result;
				for(int n=0;n+j1<length;n++){
					if(array[j1+n]-array[i1+n] > 0){
						return result;
					}
				}
			}
		}
		return result;
		
	}

}
