import java.util.ArrayList;
import java.util.regex.Matcher;


public class StringUtils {
	public static String[] splitWithReg(String input,String regExp){
		int index = 0;
        ArrayList<String> matchList = new ArrayList<String>();
        Matcher m =java.util.regex.Pattern.compile(regExp).matcher(input);
        while(m.find()) {
        	String match = input.substring(index, m.start());
        	if(!match.equals("")){
        		matchList.add(match);
        	}
            index = m.end();
            matchList.add(input.substring(m.start(),m.end()));
        }
        if (index == 0){
        	return new String[] {input.toString()};
        }
        matchList.add(input.subSequence(index, input.length()).toString());

        int resultSize = matchList.size();
        while (resultSize > 0 && matchList.get(resultSize-1).equals("")){
        	resultSize--;
        }
        String[] result = new String[resultSize];
        return matchList.subList(0, resultSize).toArray(result);
	}
	
	public static void main(String[] args) {
		/*String testStr ="左边&C中间&R右边";
		String[] parts = testStr.split("&[L,C,R]");
		for (String str: parts) {
			System.out.println(str);
		}
		String[] parts2 = StringUtils.splitWithReg(testStr, "&[L,C,R]");
		for (String str: parts2) {
			System.out.println(str);
		}*/
		
		String url ="http://192.168.1.49：8080/x5/dsf";
		System.out.println(url);
		System.out.println(toBj(url));
		
	}
	
	/// <summary>
    /// 判断字符是否英文半角字符或标点
    /// </summary>
    /// <remarks>
    /// 32    空格
    /// 33-47    标点
    /// 48-57    0~9
    /// 58-64    标点
    /// 65-90    A~Z
    /// 91-96    标点
    /// 97-122    a~z
    /// 123-126  标点
    /// </remarks>
    public static Boolean isBjChar(char c)
    {
      int i = (int)c;
      return i >= 32 && i <= 126;
    }

    /// <summary>
    /// 判断字符是否全角字符或标点
    /// </summary>
    /// <remarks>
    /// <para>全角字符 - 65248 = 半角字符</para>
    /// <para>全角空格例外</para>
    /// </remarks>
    public static Boolean isQjChar(char c)
    {
      if (c == '\u3000') return true;

      int i = (int)c - 65248;
      if (i < 32) return false;
      return isBjChar((char)i);
    }

    /// <summary>
    /// 将字符串中的全角字符转换为半角
    /// </summary>
    public static String toBj(String s)
    {
      if (s == null || s.trim().equals("")) return s;

      StringBuilder sb = new StringBuilder(s.length());
      for (int i = 0; i < s.length(); i++)
      {
        if (s.charAt(i) == '\u3000')
          sb.append('\u0020');
        else if (isQjChar(s.charAt(i))){
        	sb.append((char)((int)s.charAt(i) - 65248));
        }else{
        	sb.append(s.charAt(i));
        }
          
      }

      return sb.toString();
    }

}
