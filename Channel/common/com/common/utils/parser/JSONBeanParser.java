package com.common.utils.parser;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 简单的类解析器
 * 
 * @author DJ
 * 
 */
public class JSONBeanParser<E> implements JSONParser<E> {
	private Class<?> objectParserClass;
	private Map<String, JSONParser<?>> attrParser;

	public JSONBeanParser(Class<?> objectParserClass,
			Map<String, JSONParser<?>> attrParsers) {
		this.setObjectParserClass(objectParserClass);
		this.addAttrParsers(attrParsers);
	}

	public JSONBeanParser(Class<?> objectParserClass) {
		this.setObjectParserClass(objectParserClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E parse(String str) throws ParseErrorException {
		E ret = null;
		Object obj = null;
		if (str == null)
			return null;

		try {
			obj = JSON.parse(str);
			if (obj == null)
				throw new ParseErrorException("无法将数据转换成JSONObect或JSONArray:"
						+ str);
		} catch (Exception e) {
			throw new ParseErrorException(e.getMessage());
		}
		if (obj instanceof JSONObject) {
			try {
				ret = (E) JSONObject.parseObject(str, objectParserClass);
				if (ret == null) {
					throw new ParseErrorException("无法将数据转换成Bean"
							+ objectParserClass.getSimpleName() + ":" + str);
				}
			} catch (Exception e) {
				throw new ParseErrorException("无法将JSON数据转换成Bean"
						+ objectParserClass.getSimpleName() + ":" + str);
			}
			if (attrParser != null) {
				for (Entry<String, JSONParser<?>> entry : attrParser.entrySet()) {
					String attrName = entry.getKey();
					JSONParser<?> parser = entry.getValue();
					String attrJsonStr = ((JSONObject) obj).getString(attrName);
					if (attrJsonStr == null || attrJsonStr.equals(""))
						continue;

					Field field;
					try {
						field = ret.getClass().getDeclaredField(attrName);
					} catch (Exception e) {
						throw new ParseErrorException("无法从类"
								+ ret.getClass().getSimpleName() + "中获得属性"
								+ attrName + ":" + str);
					}
					field.setAccessible(true);
					Object fieldRet = parser.parse(attrJsonStr);
					try {
						field.set(ret, fieldRet);
					} catch (Exception e) {
						throw new ParseErrorException("无法设置类"
								+ ret.getClass().getSimpleName() + "的属性"
								+ attrName + ":" + str);
					}
				}
			}
		} else if (obj instanceof JSONArray) {
			throw new ParseErrorException("无法解析数据，设置的解析器只能解析JSONObject" + ":"
					+ str);
		} else {
			ret = (E) obj;
		}
		return ret;
	}

	public Class<?> getObjectParserClass() {
		return objectParserClass;
	}

	public JSONBeanParser<E> setObjectParserClass(Class<?> objectParserClass) {
		this.objectParserClass = objectParserClass;
		return this;
	}

	public Map<String, JSONParser<?>> getAttrParser() {
		return attrParser;
	}

	public JSONBeanParser<E> addAttrParsers(
			Map<String, JSONParser<?>> attrParsers) {
		if (this.attrParser == null) {
			this.attrParser = new HashMap<String, JSONParser<?>>();
		}
		this.attrParser.putAll(attrParsers);
		return this;
	}

	public JSONBeanParser<E> addAttrParser(String key, JSONParser<?> attrParser) {
		if (this.attrParser == null) {
			this.attrParser = new HashMap<String, JSONParser<?>>();
		}
		this.attrParser.put(key, attrParser);

		return this;
	}
}
