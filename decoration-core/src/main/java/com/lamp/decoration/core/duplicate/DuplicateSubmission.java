package com.lamp.decoration.core.duplicate;

public @interface DuplicateSubmission {

	long intervalTime() default 3000;
	
	DuplicateIdentification duplicateType() default DuplicateIdentification.NETWORK_ADDRESS;
	
	boolean lock() default false;
	
	String[] lockKey() default {};
}
