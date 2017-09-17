package com.common.utils.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 简单的Map解析器
 * 
 * 用于指定哪一个属性使用哪个解析器来解析
 * 
 * @author DJ
 * 
 */
public class JSONMapParser implements JSONParser<Map<String, Object>> {
	private Map<String, JSONParser<?>> mapItemParsers = null;

	public JSONMapParser() {
	}

	public JSONMapParser(Map<String, JSONParser<?>> mapItemParsers) {
		this.addMapItemParsers(mapItemParsers);
	}

	public JSONMapParser(String key, JSONParser<?> mapItemParser) {
		this.addMapItemParser(key, mapItemParser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> parse(String str) throws ParseErrorException {
		Map<String, Object> ret = null;
		if (str == null)
			return null;
		Object obj = null;
		try {
			obj = JSON.parse(str);
			if (obj == null)
				throw new ParseErrorException("无法将数据转换成JSONObect或JSONArray:"
						+ str);
		} catch (Exception e) {
			throw new ParseErrorException(e.getMessage());
		}
		if (obj instanceof JSONObject) {
			ret = JSONObject.parseObject(str, Map.class);
			for (Entry<String, Object> entry : ret.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (value != null
						&& !value.equals("")
						&& (value instanceof JSONObject || value instanceof JSONArray)) {
					JSONParser<?> parser = getBeanParser(key);
					if (parser != null) {
						Object valueParsered = parser.parse(value.toString());
						ret.put(key, valueParsered);
					}
				}
			}
		} else {
			throw new ParseErrorException("无法解析数据，设置的解析器只能解析JSONObject:" + str);
		}
		return ret;
	}

	public JSONMapParser addMapItemParsers(
			Map<String, JSONParser<?>> mapItemParsers) {
		if (this.mapItemParsers == null)
			this.mapItemParsers = new HashMap<String, JSONParser<?>>();
		this.mapItemParsers.putAll(mapItemParsers);
		return this;
	}

	public JSONMapParser addMapItemParser(String key,
			JSONParser<?> mapItemParser) {
		if (this.mapItemParsers == null)
			this.mapItemParsers = new HashMap<String, JSONParser<?>>();
		this.mapItemParsers.put(key, mapItemParser);
		return this;
	}

	public Map<String, JSONParser<?>> getMapItemParsers() {
		return mapItemParsers;
	}

	private JSONParser<?> getBeanParser(String key) {
		if (mapItemParsers == null)
			return null;
		if (!mapItemParsers.containsKey(key))
			return null;

		return mapItemParsers.get(key);
	}
}
