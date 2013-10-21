import java.util.Calendar;


public class GetDate {
	public static void main(String[] args) {
		Calendar cal =Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	System.out.println(year);
    	int month = cal.get(Calendar.MONTH)+1;
    	String djmonth = "";
        if (month < 10) {
             djmonth = "0" + Integer.toString(month);
        }
        else {
             djmonth = Integer.toString(month);
        }
        
    	System.out.println(djmonth);
    	int date = cal.get(Calendar.DATE);
    	System.out.println(date);
	}
}
