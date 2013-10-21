import java.io.File;

import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.tools.jcore.ClassFilter;

/**
 * 
 * @author 007slm
 *
 * dump出jvm已经加载的class classFilter是过滤器
 * 
 * 依赖jdk7 的sa-jdi.jar
 */
public class DumpClass implements ClassFilter {
	public static void main(String[] args) {
		//如果需要在命令行中编译需要这样写 java -classpath ".:./bin:$JAVA_HOME/lib/sa-jdi.jar" -Dsun.jvm.hotspot.tools.jcore.filter=DumpClass sun.jvm.hotspot.tools.jcore.ClassDump 20542
		/**
		 * E:\x5_dev\x5.2.1.1900\studio\workspace\slmFrameWork\.
			Attaching to process ID 21568, please wait...
			Debugger attached successfully.
			Client compiler detected.
			JVM version is 22.0-b10
			看到以上信息 说明已经输出 目录在E:\x5_dev\x5.2.1.1900\studio\workspace\slmFrameWork\.下面也就是.下面
		 */
		System.setProperty("sun.jvm.hotspot.tools.jcore.filter","DumpClass");
		args = new String[]{"19936"};
		System.out.println(new File(".").getAbsolutePath());
		sun.jvm.hotspot.tools.jcore.ClassDump.main(args);
		
	}
	
    public boolean canInclude(InstanceKlass kls) {
    	String klassName = kls.getName().asString();
    	return klassName.startsWith("java/io/FileInputStream");
    }
}
