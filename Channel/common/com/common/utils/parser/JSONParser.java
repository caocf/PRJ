package com.common.utils.parser;

public interface JSONParser<E> {
	public E parse(String str) throws ParseErrorException;
}
