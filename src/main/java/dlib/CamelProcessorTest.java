package dlib;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import org.apache.camel.Processor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;

public class CamelProcessorTest{
    // Test: mvn exec:java -Dexec.mainClass="dlib.CamelProcessorTest"
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
        context.addComponent("activemq", ActiveMQComponent.activeMQComponent("tcp://yodlapp3.york.ac.uk:61617?wireFormat.maxInactivityDuration=0"));
		try {
			ProducerTemplate template = context.createProducerTemplate();
			context.start();
			template.sendBody("activemq:test.queue", "Hello Processor");
            context.removeComponent("activemq");
            template.stop();
		}catch(Exception e){
        } finally {
			context.stop();
		}
	}
}