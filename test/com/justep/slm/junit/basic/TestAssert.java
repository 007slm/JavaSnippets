package com.justep.slm.junit.basic;

import org.junit.Assert;
import org.junit.Test;

/**
 * 1．assertEquals ([String message],expected,actual)：

           用来查看对象中存的值是否是期待的值，与字符串比较中使用的equals()方法类似；

   　2．assertFalse ([String messag],boolean condition)

            assertTrue ([String messag],boolean condition)：

           用来查看变量是是否为false或true，如果assertFalse()查看的变量的值是false则

           测试成功，如果是true则失败，assertTrue()与之相反；

      3．assertSame ([String message],expected,actual)

            assertNotSame ([Spring message],expected,actual)：

           用来比较两个对象的引用是否相等和不相等，类似于通过“==”和“!=”比较两个对象；

          （与equal有区别）


　　4．assertNull ([String message]，java.lang.Object object)

            assertNotNull ([String message]，java.lang.Object object)：

           用来查看对象是否为空和不为空;

 

　　5．fail ([String message])：

           意为失败，用来抛出错误。fail()一旦被执行，会立即中止测试，java虚拟机不再执行任

          何别的代码，并且会抛出  junit.framework.AssertionFailedError错误!我个人认为有

          两个用途：首先是在测试驱动开发中，由于测试用例都是在被测试的类之前编写的，而写成

          时又不清楚其正确与否，此时就可以使用fail方法抛出错误进行模拟；其次是抛出意外的错

         误，比如要测试的内容是从数据库中读取的数据是否正确，而导致错误的原因却是数据库连

         接失败，这样便可以直接找出错误的根源。


 * @author Administrator
 *
 */
public class TestAssert {
	@Test
	public void testAssertEqulas(){
		Assert.assertEquals("这个是提示信息", true, "aaa".equals("aaa"));
		Assert.assertFalse("这个是提示信息", false);
		Assert.assertFalse("这个是提示信息", new String("aaa")==new String("aaa"));
		Assert.assertSame("这个是提示信息", "aaa","aaa");
		Assert.assertNull(null);
	}
}
