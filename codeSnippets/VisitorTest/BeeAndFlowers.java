package VisitorTest;

/*
	1) 访问者角色(Visitor)：声明一个访问接口。接口的名称和方法的参数标识了向访问者发送请求的元素角色。这样访问者就可以通过该元素角色的特定接口直接访问它。
	
	2) 具体访问者角色（Concrete Visitor）：实现访问者角色（Visitor）接口
	
	3) 元素角色（Element）：定义一个Accept操作，它以一个访问者为参数。
	
	4) 具体元素角色（Concrete Element）：实现元素角色（Element）接口。
	
	5) 对象结构角色（Object Structure）：这是使用Visitor模式必须的角色。它要具备以下特征：能枚举它的元素；可以提供一个高层的接口允许访问者角色访问它的元素；可以是一个组合（组合模式）或是一个集合，如一个列表或一个无序集合。
	
	
	作用于某个对象群中各个对象的操作. 它可以使你在不改变这些对象本身的情况下,定义作用于这些对象的新操作.
	
	对象群 我们抽象出来 元素角色（Element） 用来接受 不同操作
	不同操作 我们抽象出来访问者元素（Visitor） 用来操作不同对象
	
	所以这个Visitor中有很多重载的操作函数visit（不同对象）
	
	
	不同操作                                            					对象群
	
	Visitor                                      Element   
	visit(T extends Element)                     accept(Visitor visitor){
							                     	visitor.visit(this);
							                     }


	
*/





import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import junit.framework.TestCase;

// 访问者角色
interface Visitor{
	void visit(Gladiolus g);

	void visit(Runuculus r);

	void visit(Chrysanthemum c);
}

//concrete visitor
class StringVisitor implements Visitor {
	String s;

	public String toString() {
		return s;
	}

	public void visit(Gladiolus g) {
		s = "Gladiolus";
	}

	public void visit(Runuculus r) {
		s = "Runuculus";
	}

	public void visit(Chrysanthemum c) {
		s = "Chrysanthemum";
	}
}

// concrete visitor
class Bee implements Visitor {
	public void visit(Gladiolus g) {
		System.out.println("Bee and Gladiolus");
	}

	public void visit(Runuculus r) {
		System.out.println("Bee and Runuculus");
	}

	public void visit(Chrysanthemum c) {
		System.out.println("Bee and Chrysanthemum");
	}
}


// 元素角色 element
interface Flower {
	void accept(Visitor v);
}

// concrete element 具体元素对象
class Gladiolus implements Flower {
	public void accept(Visitor v) {
		v.visit(this);
	}
}

//concrete element 具体元素对象
class Runuculus implements Flower {
	public void accept(Visitor v) {
		v.visit(this);
	}
}

//concrete element 具体元素对象
class Chrysanthemum implements Flower {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
/**
 * 
  以上是visitor的基本接口和实现
  
  下面是测试用例
 *
 */

//这是一个对象生成器
class FlowerGenerator {
	private static Random rand = new Random();

	public static Flower newFlower() {
		switch (rand.nextInt(3)) {
		default:
		case 0:
			return new Gladiolus();
		case 1:
			return new Runuculus();
		case 2:
			return new Chrysanthemum();
		}
	}
}

//客户测试程序
public class BeeAndFlowers extends TestCase {

	/*
	 首先在客户端先获得一个具体的访问者角色
	 遍历对象结构
	 对每一个元素调用accept方法，将具体访问者角色传入
	 这样就完成了整个过程
	*/
	List flowers = new ArrayList();

	public BeeAndFlowers() {
		for (int i = 0; i < 10; i++)
			flowers.add(FlowerGenerator.newFlower());
	}

	Visitor sval;

	public void test() {
		sval = new StringVisitor();
		Iterator it = flowers.iterator();
		while (it.hasNext()) {
			((Flower) it.next()).accept(sval);
			System.out.println(sval);
		}
	}

	public static void main(String args[]) {
		junit.textui.TestRunner.run(BeeAndFlowers.class);
	}

}
