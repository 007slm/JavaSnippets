package activemq;

import javax.jms.Session;

public class Consant {
	static String activeMqUrl = "tcp://192.168.1.49:61618";
	static int acknowledge = Session.AUTO_ACKNOWLEDGE;
	static boolean transacted = false;
	static String queueName = "queueNameTest";
}
