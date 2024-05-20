package th.co.truecorp.crmdev.asset;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import th.co.truecorp.crmdev.util.logging.LogProductName;
import th.co.truecorp.crmdev.util.logging.LogSystem;

@SpringBootApplication(scanBasePackages = {
	"th.co.truecorp.crmdev.asset",
	"th.co.truecorp.crmdev.asset.rest",
	"th.co.truecorp.crmdev.asset.dao"
})
public class Application {

	private static AssetLogger logger;

	public Application() {
		// Default constructor
	}

	/*
	Command Line: 
		mvn clean package -Dspring.profiles.active=prod1
		Solution 1: Define an environment variable on OS.
			export spring.profiles.active=prod1
			java -jar CRMIAsset-1.2.0.jar
			
		mvn clean package -Dcassandra.member=172.16.2.138,172.16.2.139,172.16.2.140 -Dcassandra.port=9042 -Dcassandra.keyspace=crm_adapter_2 -Dspring.profiles.active=uat2
		
		Solution 2: JVM 
		UAT
			java -jar -Dcassandra.member=172.16.2.138,172.16.2.139,172.16.2.140 -Dcassandra.port=9042 -Dcassandra.keyspace=crm_adapter_2 -Dspring.profiles.active=uat2 CRMIAsset-1.3.0.jar
			java -jar -Dspring.config.location=/data/appcfg/application.properties CRMIAsset-1.2.0.jar
		PRD
			java -jar -Dcassandra.member=172.19.191.219,172.19.190.34,172.19.190.75,172.19.248.74,172.19.248.75,172.19.248.76 -Dcassandra.port=9042 -Dcassandra.keyspace=crm_adapter -Dcassandra.user=crmuser -Dcassandra.pass=Sep@crm -Dspring.profiles.active=prod1 CRMIAsset-1.4.0.jar
	*/
    public static void main(String[] args) {
    	String transID = null;
    	
    	try {
	    	logger = new AssetLogger(LogProductName.All, LogSystem.CRM_INBOUND, LogSystem.CRM_INBOUND);
	    	
	    	transID = UUID.randomUUID().toString();
	    	
	    	logger.info(transID, "Starting Spring Boot application .....");
	        ConfigurableApplicationContext appContext = SpringApplication.run(Application.class, args);
	        
	        for (String name : appContext.getBeanDefinitionNames()) {
	        	logger.debug(transID, "Bean Name: " + name);
			}
    	}
    	catch (Exception e) {
    		logger.error(transID, e.getMessage(), e);
    	}
    }
}