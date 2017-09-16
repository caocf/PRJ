package com.hztuen.lvyou.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * json涓茶В鏋愬伐鍏风被
 * 
 * @author zengliang
 */
public class ParseUtil {

	/**
	 * 鐩存帴瑙ｆ瀽json鏁版嵁涓哄疄浣�jsonObject娌℃湁鍐嶅涓�眰jsonObject
	 * 
	 * @param object
	 *            json瀹炰綋
	 * @param clazz
	 *            瀵瑰簲瀹炰綋绫诲瓧鑺傜爜
	 * @return 杩斿洖瀵瑰簲瀹炰綋
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <T> T parseDataToEntity(JSONObject object, Class<T> clazz) {

		// T t = clazz.newInstance();
		Gson gson = new Gson();
		T entity = gson.fromJson(object.toString(), clazz);
		return entity;
	}

	/**
	 * 瑙ｆ瀽json鏁版嵁涓哄疄浣�jsonObject鍐嶅涓�眰jsonObject
	 * 
	 * @param object
	 *            json瀹炰綋
	 * @param key
	 *            鍖呭惈json鏁版嵁鐨刱ey鍊�
	 * @param clazz
	 *            瀵瑰簲瀹炰綋绫诲瓧鑺傜爜
	 * @return 杩斿洖瀵瑰簲瀹炰綋
	 * @throws Exception
	 */
	public static <T> T parseDataToEntity(JSONObject object, String key,
			Class<T> clazz) throws Exception {
		Gson gson = new Gson();
		T entity = null;
		if (key != null && object.has(key)) {
			entity = gson.fromJson(object.getJSONObject(key).toString(), clazz);
		} else {
			entity = gson.fromJson(object.toString(), clazz);
		}
		return entity;
	}

	/**
	 * 瑙ｆ瀽json鏁版嵁涓哄疄浣撻泦鍚�
	 * 
	 * @param object
	 *            json瀹炰綋
	 * @param key
	 *            鍖呭惈json鏁扮粍鐨刱ey鍊�
	 * @param clazz
	 *            瀵瑰簲瀹炰綋绫诲瓧鑺傜爜
	 * @return 杩斿洖瀵瑰簲瀹炰綋闆嗗悎
	 * @throws Exception
	 */
	public static <T> List<T> parseDataToCollection(JSONObject object,
			String key, Class<T> clazz) throws Exception {

		Gson gson = new Gson();
		JSONArray jsonArray = null;
		if (object.has(key)) {
			jsonArray = object.getJSONArray(key);
		} else {
			return null;
		}
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		List<T> data = new ArrayList<T>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject entityObj = jsonArray.getJSONObject(i);
			T entity = gson.fromJson(entityObj.toString(), clazz);
			data.add(entity);
		}
		return data;
	}

	/**
	 * 瑙ｆ瀽json瀹炰綋涓篗ap闆嗗悎,Map闆嗗悎涓殑value鍊间负 瀹炰綋绫诲瀷
	 * 
	 * @param object
	 * @param key
	 * @param mapKey
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<Object, T> parseDataToMap(JSONObject object,
			String key, String mapKey, Class<T> clazz) throws Exception {

		Gson gson = new Gson();
		JSONArray jsonArray = null;
		if (object.has(key)) {
			jsonArray = object.getJSONArray(key);
		} else {
			return null;
		}
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		Map<Object, T> data = new HashMap<Object, T>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject entityObj = jsonArray.getJSONObject(i);
			T entity = gson.fromJson(entityObj.toString(), clazz);
			data.put(entityObj.opt(mapKey), entity);
		}
		return data;
	}

	/**
	 * 瑙ｆ瀽json闆嗗悎涓篗ap闆嗗悎 Map闆嗗悎涓殑value鍊间负 瀹炰綋绫诲瀷
	 * 
	 * @param jsonArray
	 * @param mapKey
	 * @param clazz
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<Object, T> parseDataToMap(JSONArray jsonArray,
			String mapKey, Class<T> clazz) throws Exception {

		Gson gson = new Gson();
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		Map<Object, T> data = new HashMap<Object, T>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject entityObj = jsonArray.getJSONObject(i);
			T entity = gson.fromJson(entityObj.toString(), clazz);
			data.put(entityObj.opt(mapKey), entity);
		}
		return data;
	}

	/**
	 * 瑙ｆ瀽json鏁版嵁涓篗ap闆嗗悎
	 * 
	 * @param object
	 *            json涓�
	 * @param key
	 *            json涓茬殑key鍊�
	 * @param mapKey
	 *            String 绫诲瀷key
	 * @param mapValue
	 *            String 绫诲瀷value
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseDataToMap(JSONObject object,
			String key, String mapKey, String mapValue) throws Exception {
		JSONArray jsonArray = null;
		if (object.has(key)) {
			jsonArray = object.getJSONArray(key);
		} else {
			return null;
		}
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		Map<String, String> data = new HashMap<String, String>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject entityObj = jsonArray.getJSONObject(i);
			data.put(entityObj.optString(mapKey), entityObj.optString(mapValue));
		}
		return data;
	}

	/**
	 * 瑙ｆ瀽json鏁版嵁涓哄疄浣撻泦鍚�
	 * 
	 * @param jsonArray
	 *            json鏁扮粍
	 * @param clazz
	 *            瀵瑰簲瀹炰綋绫诲瓧鑺傜爜
	 * @return 杩斿洖瀵瑰簲瀹炰綋闆嗗悎
	 * @throws Exception
	 */
	public static <T> List<T> parseDataToCollection(JSONArray jsonArray,
			Class<T> clazz) throws Exception {

		Gson gson = new Gson();
		if (jsonArray == null || jsonArray.length() == 0) {
			return null;
		}
		List<T> data = new ArrayList<T>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject entityObj = jsonArray.getJSONObject(i);
			T entity = gson.fromJson(entityObj.toString(), clazz);
			data.add(entity);
		}
		return data;
	}

}
