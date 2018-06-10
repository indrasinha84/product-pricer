package com.pricer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pricer.batch.core.JobManager;
import com.pricer.batch.core.JobManagerWorker;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class ProductPricerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProductPricerApplication.class, args);
	}

	@Autowired
	JobManager jobManager;

	@Override
	public void run(String... args) throws Exception {

		(new Thread(new JobManagerWorker(jobManager))).start();
	}
}
