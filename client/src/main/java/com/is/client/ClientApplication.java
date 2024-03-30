package com.is.client;

import java.io.IOException;


import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ClientApplication {

	
	public static void main(String[] args) throws IOException, InterruptedException {
	
		ClientWeb client = new ClientWeb();
	
		client.requirements();
		
	}

	
	

}
