package net.app.fixMypLACE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;

import org.apache.log4j.Logger;
/**
 * Spring Entry point.
 * Runnable class with a main method invoking the execution  of App class
 */
@SpringBootApplication
@Controller
@ComponentScan(basePackages="net.app.fixMypLACE")
public class App {

    
    public static Logger log = Logger.getLogger(App.class.getName());
    
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
