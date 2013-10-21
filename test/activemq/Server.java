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

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

/**
 * 
 * @author 007slm
 *
 * 用来发送消息 
 */
public class Server implements MessageListener {
	
	/**
	 *   Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		1、 Session.AUTO_ACKNOWLEDGE：在完成接收消息时，Session自动发送一个确认回执。

		2、 Session.CLIENT_ACKNOWLEDGE：由客户端程序通过手工调用Message.acknowledge()方法显示确认接收。

		3、 Session.DUPS_OK_ACKNOWLEDGE：让Session延迟发送确认回执。
	 */
    private static int ackMode;
    
    /**
     * 消息队列的名字
     */
    private static String messageQueueName;
    
    /**
     * 消息容器的url
     */
    private static String messageBrokerUrl;

    private Session session;
    
    /**
     * 是否使用事务
     */
    private boolean transacted = false;
    private MessageProducer replyProducer;

    static {
        messageBrokerUrl = "tcp://localhost:61616";
        messageQueueName = "messagesQueueName";
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    public Server() {
        try {
            //启动activemq 相当与启动数据库
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(messageBrokerUrl);
            broker.start();
        } catch (Exception e) {
            //Handle the exception appropriately
        }

        //Delegating the handling of messages to another class, instantiate it before setting up JMS so it
        //is ready to handle messages
        this.setupMessageQueueConsumer();
    }

    private void setupMessageQueueConsumer() {
    	//连接池
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
        Connection connection;
        try {
        	//获取连接
            connection = connectionFactory.createConnection();
            connection.start();
            this.session = connection.createSession(this.transacted, ackMode);
            
            //创建一个消息队列
            Destination msgQueue = this.session.createQueue(messageQueueName);

            //创建一个消息生产者
            this.replyProducer = this.session.createProducer(msgQueue);
            
            //设置这个生产者的分发模式  --  不需要持久化的分发(jms 提供者关闭的时候 没有消费的消息会丢失的)	
            this.replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //消息的消费者 
            MessageConsumer consumer = this.session.createConsumer(msgQueue);
            consumer.setMessageListener(this);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void onMessage(Message message) {
        try {
            TextMessage response = this.session.createTextMessage();
            if (message instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) message;
                String messageText = txtMsg.getText();
                response.setText(messageText);
            }

            //设置关联的id
            response.setJMSCorrelationID(message.getJMSCorrelationID());

            
            this.replyProducer.send(message.getJMSReplyTo(), response);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}

