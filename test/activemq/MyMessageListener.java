package activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

class MyMessageListener implements MessageListener{
		public void onMessage(Message message){
			try {
				System.out.println("收到消息了");
				System.out.println("收到消息内容是:"+((TextMessage)message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}