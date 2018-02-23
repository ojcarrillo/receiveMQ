package com.example.demo;

import java.io.IOException;
import java.util.Random;
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
import com.rabbitmq.client.MessageProperties;

@SpringBootApplication
public class ReceiveMqApplication {

	private final static String QUEUE_NAME = "mq_movctas";
	private final static String RETRY_EXCHANGE = "re_movctas";

	public static void main(String[] args)
			throws java.io.IOException, java.lang.InterruptedException, TimeoutException {
		SpringApplication.run(ReceiveMqApplication.class, args);
		/* configura la conexion al servidor de colas */
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("b2c_client");
		factory.setPassword("SuperPassword000");
		System.out.println("probando!");
		//factory.setHost("localhost");
		factory.setHost("35.203.110.236");
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
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
				try {
					AgregarDatosXML proc = new AgregarDatosXML();
					proc.aggregarAlXML(message);
				} catch (Exception e) {
					System.out.println("errro-------------------------------");
					channel.basicPublish(RETRY_EXCHANGE, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));	
				}
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}

}
