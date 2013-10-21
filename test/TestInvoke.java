import java.lang.reflect.InvocationTargetException;

import com.justep.slm.Invoking;
import com.justep.slm.Mirror;


public class TestInvoke {
	public static void main(String[] args) {
		try {
			new Invoking(Mirror.class, "isPrimitiveNumber", new Object[]{long.class}).invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Mirror.class.getMethod("isPrimitiveNumber", Class.class).invoke(null, new Object[]{long.class});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
}
