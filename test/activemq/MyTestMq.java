package activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

public class MyTestMq {
	
	String activeMqUrl = "tcp://192.168.1.49:61618";
	int acknowledge = Session.AUTO_ACKNOWLEDGE;
	String queueTest ="queueNameTest";
	String topicTest ="topicTest";
	private void startMq() throws Throwable{
		BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        broker.setUseJmx(false);
        broker.addConnector(activeMqUrl);
        broker.start();
	}
	
	private Connection getConnection(String clientId) throws Throwable {
		ActiveMQConnectionFactory acf =new ActiveMQConnectionFactory(activeMqUrl);
		Connection connection = acf.createConnection();
		if(clientId!=null){
			connection.setClientID(clientId);
		}
		connection.start();
		return connection;
	}
	
	private Session createSession(Connection conn) throws Throwable {
		return conn.createSession(false, acknowledge);
		
	}
	
	private Destination createQueue(Session session,String queueName) throws Throwable{
		return session.createQueue(queueName);
	}
	
	private Destination createTopic(Session session,String topicName) throws Throwable{
		return session.createTopic(topicName);
	}
	
	public static void main(String[] args) throws Throwable {
		MyTestMq mt =new MyTestMq();
		mt.startMq();
		Connection conn = mt.getConnection("testClientId");
		//创建session
		Session session = mt.createSession(conn);
		
		//创建queue的生成者
		Destination queueTest = mt.createQueue(session,"queueNameTest");
		MessageProducer producer = session.createProducer(queueTest);
		//发送消息
		TextMessage message = session.createTextMessage();
		message.setText("我发送的第一个消息");
		producer.send(message);
		
		//创建queue消费者
		MessageConsumer consumer = session.createConsumer(queueTest);
		consumer.setMessageListener(mt.new MyMessageListener());
		//创建主题非持久主题 有时间性 一定要在主题发布消息之前的订阅才能收到copy
		Topic topic = session.createTopic("topicTest");
		
		//创建普通订阅者
		//MessageConsumer topicConsumer = session.createConsumer(topic);
		//topicConsumer.setMessageListener(mt.new MyMessageListener());
		
		//创建持久化主题 可以接受离线消息
		TopicSubscriber durableConsumer = session.createDurableSubscriber(topic, "consumerName");
		durableConsumer.receive();
		//创建订阅者
		//durableConsumer.setMessageListener(mt.new MyMessageListener());
		
		// 对主题发送消息
		TextMessage topicMsg = session.createTextMessage();
		topicMsg.setText("我发送的第一个topic消息");
		MessageProducer topicProducer = session.createProducer(topic);
		topicProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		topicProducer.send(topicMsg);
		
		
		
		
		
	}
	
	class MyMessageListener implements MessageListener{
		public void onMessage(Message message){
			try {
				System.out.println("收到消息内容是:"+((TextMessage)message).getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
