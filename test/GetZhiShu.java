import java.util.ArrayList;

/**
 * 
 * @author 007slm
 *	@desc 求100内质数  
 *   先去掉偶数，然后便利
 */
public class GetZhiShu {
	public static void main(String[] args) {
		ArrayList<Integer> zhishuList =new ArrayList<Integer>();
		for(int number=1;number<100;number++){
			if(!isOShu(number)){
				/**
				 * 不是偶数，有可能是质数
				 */
				boolean isZhishu=true;
				for(int bcs=3;bcs<number;bcs=bcs+2){
					if(number%bcs==0){ 
						//可以被整除，肯定不是质数
						isZhishu=false;
						break;
					}
				}
				if(isZhishu){
					zhishuList.add(number);
				}
			}
		}
		
		for(Integer in:zhishuList){
			System.out.println(in);
		}
	}
	
	public static boolean isOShu(int i){
		if(i<2) return true;	
		return i%2 ==0?true:false;
	}
}
