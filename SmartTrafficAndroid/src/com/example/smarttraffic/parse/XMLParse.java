package com.example.smarttraffic.parse;

import java.io.InputStream;
import java.io.OutputStream;

public interface XMLParse
{
	public Object fromXML(InputStream inputStream);
	public void toXML(OutputStream outputStream,Object object);
}
