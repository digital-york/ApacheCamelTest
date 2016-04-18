package dlib;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import org.apache.camel.Processor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

public class CamelProcessor implements Processor {
    public void process(Exchange inExchange) {
        System.out.println("--------Received message--------");
        Message message = inExchange.getIn();
        System.out.println(message.getBody().toString());
        // Doing own processing here ...
    }    
    
    // Test : mvn exec:java -Dexec.mainClass="dlib.CamelProcessor"
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		try {
			context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://yodlapp3.york.ac.uk:61617?wireFormat.maxInactivityDuration=0"));
			context.addRoutes(new RouteBuilder() {
				@Override
				public void configure() throws Exception {
  				    from("activemq:queue:test.queue").process(new CamelProcessor());
				}
			});
			//ProducerTemplate template = context.createProducerTemplate();
			context.start();
			//template.sendBody("activemq:test.queue", "Hello Processor");
		}catch(Exception e){
        } finally {
			//context.stop();
		}
	}
}