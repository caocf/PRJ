package com.zjport.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Will on 2016/10/18 9:49.
 */
public class XMLObject implements Map<String,Object>,Cloneable, Serializable {
    private static final long serialVersionUID = -7006835342455866809L;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final Map<String, Object> map;

    public XMLObject() {
        this(DEFAULT_INITIAL_CAPACITY, false);
    }

    public XMLObject(Map<String, Object> map) {
        this.map = map;
    }

    public XMLObject(boolean ordered) {
        this(DEFAULT_INITIAL_CAPACITY, ordered);
    }

    public XMLObject(int initialCapacity) {
        this(initialCapacity, false);
    }

    @SuppressWarnings("unchecked")
    public XMLObject(int initialCapacity, boolean ordered) {
        if(ordered) {
            this.map = new LinkedHashMap(initialCapacity);
        } else {
            this.map = new HashMap(initialCapacity);
        }

    }


    @Deprecated//涉及大小写问题不建议使用
    public XMLObject(Object object,boolean ordered){
        this(ordered);
        if(object == null){
            return;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        XMLObject fieldObject = new XMLObject(fields.length);
        map.put(clazz.getSimpleName(),fieldObject);
        for (Field field : fields) {
            try {
                String name = field.getName();
                int mod = field.getModifiers();
                if(Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod)){
                    continue;
                }
                if (name.equalsIgnoreCase("SerialVersionUID")) {
                    continue;
                }
                field.setAccessible(true);
                XMLObject f = new XMLObject(1);
                f.put("value",Value.of(field.get(object),""));
                fieldObject.put(field.getName(),f);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Deprecated//涉及大小写问题不建议使用
    public XMLObject(Object object){
        this(object,false);
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsKey(value);
    }

    @Override
    public Object get(Object key) {
        return this.map.get(key);
    }

    @SuppressWarnings("unchecked")
    public XMLObject getXMLObject(String key) {
        Object value = this.map.get(key);
        if(value instanceof XMLObject){
            return (XMLObject) value;
        }else{
            return new XMLObject(Collections.EMPTY_MAP);
        }
    }

    @SuppressWarnings("unchecked")
    public List<XMLObject> getList(String key){
        Object value = this.map.get(key);
        if(value instanceof List){
            return (List) value;
        }else{
            List<XMLObject> list = new ArrayList<>();
            list.add((XMLObject) value);
            return list;
        }
    }

    @Override
    public Object put(String key, Object value) {
        return this.map.put(key,value);
    }

    @Override
    public Object remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public boolean equals(Object obj) {
        return this.map.equals(obj);
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
