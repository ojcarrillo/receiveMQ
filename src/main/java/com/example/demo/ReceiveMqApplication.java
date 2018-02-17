package com.example.demo;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

@SpringBootApplication
public class ReceiveMqApplication {

	private final static String QUEUE_NAME = "mq_movctas";

	public static void main(String[] args)
			throws java.io.IOException, java.lang.InterruptedException, TimeoutException {
		SpringApplication.run(ReceiveMqApplication.class, args);
		/* configura la conexion al servidor de colas */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("b2c_client");
		factory.setPassword("SuperPassword000");
		System.out.println("probando!");
		factory.setHost("localhost");
		factory.setPort(5672);
		/* abre la conexion */
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		/* declara la cola a la cual conectarse */
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		/* declara e implementa el listener de la cola para recibir los mensajes */
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				/* obtiene el mensaje */
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				/* valida el mensaje */
				if(message!=null && message.trim().length()>0) {
					AgregarDatosXML manager = new AgregarDatosXML();
//					manager.aggregarAlXML(message);
//					try{
//						manager.aggregarAlXML(message);
//					}catch(Exception ex) {
//						GetResponse gr = channel.basicGet(QUEUE_NAME, false);
//						channel.basicNack(gr.getEnvelope().getDeliveryTag(), false, true);
//						ex.printStackTrace();
//					}
				}
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
}
