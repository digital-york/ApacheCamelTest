package dlib;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelHelloWorld {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		try {
			context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://yodlapp3.york.ac.uk:61617?wireFormat.maxInactivityDuration=0"));
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
					from("activemq:queue:test.queue")
					.to("stream:out");
				}
			});
			ProducerTemplate template = context.createProducerTemplate();
			context.start();
			template.sendBody("activemq:test.queue", "Hello World");
			Thread.sleep(2000);
		} finally {
			context.stop();
		}
	}
}