package com.pricer.batch.scheduler;

import org.springframework.http.HttpStatus;

public class TestSomething {

	public static void main(String[] args) {
		Integer i = null;
		System.out.println(i != null && HttpStatus.OK.value() == i);
	}

}
