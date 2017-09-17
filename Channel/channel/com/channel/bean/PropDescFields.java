package com.channel.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PropDescFields {

	class PropDescField {
		private PropDesc meta;
		private Field field;
		private String name;
		private Class<?> type;

		public PropDesc getMeta() {
			return meta;
		}

		public void setMeta(PropDesc meta) {
			this.meta = meta;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Class<?> getType() {
			return type;
		}

		public void setType(Class<?> type) {
			this.type = type;
		}
	}

	private List<PropDescField> propDescFields = new ArrayList<>();

	public PropDescFields(Class<?> cls) {
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			// 获取字段中包含fieldMeta的注解
			PropDesc meta = f.getAnnotation(PropDesc.class);
			if (meta != null) {
				PropDescField fd = new PropDescField();
				fd.setField(f);
				fd.setMeta(meta);
				fd.setName(f.getName());
				fd.setType(f.getType());

				this.propDescFields.add(fd);
			}
		}
	}

	public String getPropDesc(String filedname) {
		for (PropDescField f : propDescFields) {
			if (f.getName() != null && f.getName().equals(filedname)) {
				return f.getMeta().value();
			}
		}
		return "";
	}
}