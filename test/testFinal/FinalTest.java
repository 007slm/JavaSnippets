package testFinal;

import org.junit.Test;

public class FinalTest {
	@Test
	public void TestFinal(){
		final Student s = new Student();
		s.setAge(26);
		s.setName("007slm");
		junit.framework.Assert.assertEquals("007slm", s.getName());
		junit.framework.Assert.assertEquals(26, s.getAge());
	}
}

class Student{
	int age;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	String name;
	
}

