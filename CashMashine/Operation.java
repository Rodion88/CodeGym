package com.javarush.task.task26.task2613;

public enum Operation {
	LOGIN,
	INFO,
	DEPOSIT,
	WITHDRAW,
	EXIT;

	public static Operation getAllowableOperationByOrdinal(Integer i){
		if(i == LOGIN.ordinal()){
			throw new IllegalArgumentException();
		}
		for (Operation operation : Operation.values()) {
			if(operation.ordinal() == i){
				return operation;
			}
		}
		throw new IllegalArgumentException();
	}

}
