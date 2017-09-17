package com.common.utils.uiinput.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.common.service.BaseService;
import com.common.utils.LogUtils;
import com.common.utils.uiinput.anotation.add.ModelUiInputAdd;
import com.common.utils.uiinput.anotation.add.UiInputAdd;
import com.common.utils.uiinput.anotation.autoajax.ModelAutoAjax;
import com.common.utils.uiinput.anotation.inputtype.InputType;
import com.common.utils.uiinput.anotation.inputtype.ModelInputType;
import com.common.utils.uiinput.anotation.other.ControlType;
import com.common.utils.uiinput.anotation.other.Filter;
import com.common.utils.uiinput.anotation.other.ModelKeyValue;
import com.common.utils.uiinput.anotation.update.ModelUiInputUpdate;
import com.common.utils.uiinput.anotation.update.UiInputUpdate;
import com.common.utils.uiinput.anotation.validator.ModelValidator;
import com.common.utils.uiinput.model.Model;
import com.common.utils.uiinput.model.ModelProperty;

@Service("ModelUtilService")
public class ModelUtilService extends BaseService implements
		ApplicationContextAware {
	private ApplicationContext springContext;
	private Logger logger = LogUtils.getLogger(getClass());

	@SuppressWarnings("unchecked")
	public Model queryModelAddInfo(String modelpath,
			Map<String, Object> inputMap) {
		try {
			Class<?> cls = Class.forName(modelpath);

			Model model = new Model();

			// 设置模型名
			model.setModelName(cls.getSimpleName());
			List<ModelProperty> properties = new ArrayList<ModelProperty>();

			// 获得所有的字段
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				UiInputAdd uiInputAdd = field.getAnnotation(UiInputAdd.class);
				UiInputUpdate uiInputUpdate = field
						.getAnnotation(UiInputUpdate.class);

				if (uiInputAdd != null) {
					ModelProperty property = new ModelProperty();
					property.setName(field.getName());
					property.setType(field.getType().getSimpleName());
					property.setUiInputAdd(new ModelUiInputAdd()
							.fromAnotation(uiInputAdd));

					InputType inputType = uiInputAdd.inputType();
					ModelUiInputAdd modelUiInput = property.getUiInputAdd();
					ModelInputType modelInputType = modelUiInput.getInputType();
					ModelAutoAjax modelAutoAjax = modelUiInput.getAutoAjax();
					String typeLower = property.getType().toLowerCase();

					/***************************** 值的设置 *********************************/
					// 如果desc没有设置，则尝试从uiInputUpdate中获取，如果还是无法获取，则自动设置为字段名
					if (modelUiInput.getDesc() == null
							|| modelUiInput.getDesc().equals("")) {
						if (uiInputUpdate != null
								&& uiInputUpdate.desc() != null
								&& !uiInputUpdate.desc().equals("")) {
							modelUiInput.setDesc(uiInputUpdate.desc());
						} else {
							modelUiInput.setDesc(field.getName());
						}
					}

					// 如果需要自动提交，但是没有设置提交名，则自动设置为模型名.字段名
					if (modelAutoAjax.isAutoAjax()
							&& modelAutoAjax.getAutoAjaxName() == null
							|| modelAutoAjax.getAutoAjaxName().equals("")) {
						modelAutoAjax.setAutoAjaxName(model.getModelName()
								.toLowerCase() + "." + property.getName());
					}

					// inputtype 可选输入的初始值绑定
					if (modelInputType.getSelKeyVals() == null
							|| modelInputType.getSelKeyVals().size() == 0) { // 如果没有通过keyval设置，则尝试从json中获取
						if (inputType.selKeyValJson() != null
								&& !inputType.selKeyValJson().equals("")) {
							Map<String, Object> dataMap = new HashMap<>();
							try {
								dataMap = JSON.parseObject(
										inputType.selKeyValJson(),
										dataMap.getClass());

								List<ModelKeyValue> keyValues = new ArrayList<>();
								for (Entry<String, Object> entry : dataMap
										.entrySet()) {
									keyValues.add(new ModelKeyValue(entry
											.getKey(), entry.getValue()));
								}
								modelInputType.setSelKeyVals(keyValues);
							} catch (Exception e) {
								logger.debug("输入有误:" + model.getModelName()
										+ "/" + property.getName()
										+ "uiInputAdd/inputtype/selKeyVals");
							}

						}
					}
					if (modelInputType.getSelKeyVals() == null // 如果还是没有初始值，则尝试从前台中获取数据
							|| modelInputType.getSelKeyVals().size() == 0) {
						if (inputType.selKeyValWebJson() != null
								&& !inputType.selKeyValWebJson().equals("")) {
							String json = (String) inputMap.get(inputType
									.selKeyValWebJson());
							if (json != null && !json.equals("")) {
								Map<String, Object> dataMap = new HashMap<>();
								try {
									dataMap = JSON.parseObject(json,
											dataMap.getClass());

									List<ModelKeyValue> keyValues = new ArrayList<>();
									for (Entry<String, Object> entry : dataMap
											.entrySet()) {
										keyValues.add(new ModelKeyValue(entry
												.getKey(), entry.getValue()));
									}
									modelInputType.setSelKeyVals(keyValues);
								} catch (Exception e) {
									logger.debug("输入有误:" + model.getModelName()
											+ "/" + property.getName()
											+ "uiInputAdd/inputtype/selKeyVals");
								}
							}
						}
					}
					// inputtype 默认值的数据绑定问题
					if (modelInputType.getDftVal() == null // 如果没有设置默认值，则尝试从前台获取数据
							|| modelInputType.getDftVal().equals("")) {
						String webkey = inputType.dftWebVal();
						if (webkey != null && !webkey.equals("")) {
							Object webval = inputMap.get(webkey);
							if (webval != null) {
								modelInputType.setDftVal("" + webval);
							} else {
								modelInputType.setDftVal("");
							}
						}
					}
					if (modelInputType.getDftVal() == null // 如果没有设置默认值，则尝试从前台名为字段名的字段中获取数据
							|| modelInputType.getDftVal().equals("")) {
						String webkey = property.getName();
						if (webkey != null && !webkey.equals("")) {
							Object webval = inputMap.get(webkey);
							if (webval != null) {
								modelInputType.setDftVal("" + webval);
							} else {
								modelInputType.setDftVal("");
							}
						}
					}

					// 提交时的默认值及初始化时的默认值，自动根据当前字段的类型进行设置
					if (typeLower.contains("int") || typeLower.contains("long")) {
						if (modelUiInput// 如果字段为整数，添加数字验证器
								.getValidator(Filter.NUMBER) == null) {
							ModelValidator modelValidator = new ModelValidator();
							modelValidator.setFilter(Filter.NUMBER);
							modelUiInput.addValidator(modelValidator);
							logger.warn(model.getModelName() + "/"
									+ property.getName() + ":"
									+ "当前字段为int或long类型，但是未设置数字过滤器，将自动添加该过滤器");
						}

						modelAutoAjax
								.setAutoAjaxDftVal(toIntString(modelAutoAjax
										.getAutoAjaxDftVal()));
					} else if (typeLower.contains("double")
							|| typeLower.contains("float")) {
						if (modelUiInput // 如果字段为小数，添加小数验证器
								.getValidator(Filter.DOUBLE) == null) {
							ModelValidator modelValidator = new ModelValidator();
							modelValidator.setFilter(Filter.DOUBLE);
							modelUiInput.addValidator(modelValidator);
							logger.warn(model.getModelName()
									+ "/"
									+ property.getName()
									+ ":"
									+ "当前字段为float或double类型，但是未设置小数检过滤器，将自动添加该过滤器");
						}

						modelAutoAjax
								.setAutoAjaxDftVal(toDoubleString(modelAutoAjax
										.getAutoAjaxDftVal()));
					} else if (typeLower.contains("date")) {
						modelAutoAjax.setAutoAjaxDftVal(toDateString(
								modelAutoAjax.getAutoAjaxDftVal(),
								modelInputType.getInputType()));

						// 如果当前数据类型为date，则需要判定数据格式,且如果不匹配，则设置为""
						modelInputType.setDftVal(toDateStringCvt(
								modelInputType.getDftVal(),
								modelInputType.getInputType()));
					} else if (typeLower.contains("string")) {
						modelAutoAjax.setAutoAjaxDftVal(toDateString(
								modelAutoAjax.getAutoAjaxDftVal(),
								modelInputType.getInputType()));
					}

					properties.add(property);
				}
			}

			// 进行排序
			Collections.sort(properties, new Comparator<ModelProperty>() {

				@Override
				public int compare(ModelProperty o1, ModelProperty o2) {
					int group1, group2;
					int order1, order2;
					int suborder1, suborder2;

					group1 = o1.getUiInputAdd().getPosition().getGroup();
					group2 = o2.getUiInputAdd().getPosition().getGroup();
					order1 = o1.getUiInputAdd().getPosition().getOrder();
					order2 = o2.getUiInputAdd().getPosition().getOrder();
					suborder1 = o1.getUiInputAdd().getPosition().getSuborder();
					suborder2 = o2.getUiInputAdd().getPosition().getSuborder();

					if (group1 != group2) {
						return group1 - group2;
					} else {
						if (order1 != order2)
							return order1 - order2;
						else {
							return suborder1 - suborder2;
						}
					}
				}
			});

			ModelProperty groupfirst = null;
			// Positon设置组内第一个元素的groupname,防止因为修改了group后忘了把groupname修改回来
			for (int i = 0; i < properties.size(); i++) {
				ModelProperty property = properties.get(i);

				// 第一个元素为组的开始元素
				if (i == 0) {
					if (groupfirst != null
							&& (groupfirst.getUiInputAdd().getPosition()
									.getGroupname() == null || groupfirst
									.getUiInputAdd().getPosition()
									.getGroupname().equals(""))) {
						groupfirst.getUiInputAdd().getPosition()
								.setGroupname("未知分组");
					}
					groupfirst = property;
				} else if (i - 1 >= 0 // 前一个元素存在且与当前元素的组号不一样
						&& (properties.get(i - 1).getUiInputAdd().getPosition()
								.getGroup() != property.getUiInputAdd()
								.getPosition().getGroup())) {
					if (groupfirst != null
							&& (groupfirst.getUiInputAdd().getPosition()
									.getGroupname() == null || groupfirst
									.getUiInputAdd().getPosition()
									.getGroupname().equals(""))) {
						groupfirst.getUiInputAdd().getPosition()
								.setGroupname("未知分组");
					}
					groupfirst = property;
				}

				String groupname = property.getUiInputAdd().getPosition()
						.getGroupname();
				if (groupname != null && !groupname.equals("")) {
					// 如果当前元素有值，则设置首元素的groupname值
					groupfirst.getUiInputAdd().getPosition()
							.setGroupname(groupname);
				}
			}

			model.setProperties(properties);
			return model;
		} catch (ClassNotFoundException e) {
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public Model queryModelUpdateInfo(String modelpath,
			Map<String, Object> inputMap) {
		try {
			Class<?> cls = Class.forName(modelpath);

			Model model = new Model();

			// 设置模型名
			model.setModelName(cls.getSimpleName());
			List<ModelProperty> properties = new ArrayList<ModelProperty>();

			// 获得所有的字段
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				UiInputUpdate uiInputUpdate = field
						.getAnnotation(UiInputUpdate.class);
				UiInputAdd uiInputAdd = field.getAnnotation(UiInputAdd.class);

				if (uiInputUpdate != null) {
					ModelProperty property = new ModelProperty();
					property.setName(field.getName());
					property.setType(field.getType().getSimpleName());
					property.setUiInputUpdate(new ModelUiInputUpdate()
							.fromAnotation(uiInputUpdate));

					InputType inputType = uiInputUpdate.inputType();
					ModelUiInputUpdate modelUiInput = property
							.getUiInputUpdate();
					ModelInputType modelInputType = modelUiInput.getInputType();
					ModelAutoAjax modelAutoAjax = modelUiInput.getAutoAjax();
					String typeLower = property.getType().toLowerCase();

					/***************************** 值的设置 *********************************/
					// 如果desc没有设置，则尝试从uiInputUpdate中获取，如果还是无法获取，则自动设置为字段名
					if (modelUiInput.getDesc() == null
							|| modelUiInput.getDesc().equals("")) {
						if (uiInputAdd != null && uiInputAdd.desc() != null
								&& !uiInputAdd.desc().equals("")) {
							modelUiInput.setDesc(uiInputAdd.desc());
						} else {
							modelUiInput.setDesc(field.getName());
						}
					}

					// 如果需要自动提交，但是没有设置提交名，则自动设置为模型名.字段名
					if (modelAutoAjax.isAutoAjax()
							&& modelAutoAjax.getAutoAjaxName() == null
							|| modelAutoAjax.getAutoAjaxName().equals("")) {
						modelAutoAjax.setAutoAjaxName(model.getModelName()
								.toLowerCase() + "." + property.getName());
					}

					// inputtype 可选输入的初始值绑定
					if (modelInputType.getSelKeyVals() == null
							|| modelInputType.getSelKeyVals().size() == 0) { // 如果没有通过keyval设置，则尝试从json中获取
						if (inputType.selKeyValJson() != null
								&& !inputType.selKeyValJson().equals("")) {
							Map<String, Object> dataMap = new HashMap<>();
							try {
								dataMap = JSON.parseObject(
										inputType.selKeyValJson(),
										dataMap.getClass());

								List<ModelKeyValue> keyValues = new ArrayList<>();
								for (Entry<String, Object> entry : dataMap
										.entrySet()) {
									keyValues.add(new ModelKeyValue(entry
											.getKey(), entry.getValue()));
								}
								modelInputType.setSelKeyVals(keyValues);
							} catch (Exception e) {
								logger.debug("输入有误:" + model.getModelName()
										+ "/" + property.getName()
										+ "uiInputUpdate/inputtype/selKeyVals");
							}

						}
					}
					if (modelInputType.getSelKeyVals() == null // 如果还是没有初始值，则尝试从前台中获取数据
							|| modelInputType.getSelKeyVals().size() == 0) {
						if (inputType.selKeyValWebJson() != null
								&& !inputType.selKeyValWebJson().equals("")) {
							String json = (String) inputMap.get(inputType
									.selKeyValWebJson());
							if (json != null && !json.equals("")) {
								Map<String, Object> dataMap = new HashMap<>();
								try {
									dataMap = JSON.parseObject(json,
											dataMap.getClass());

									List<ModelKeyValue> keyValues = new ArrayList<>();
									for (Entry<String, Object> entry : dataMap
											.entrySet()) {
										keyValues.add(new ModelKeyValue(entry
												.getKey(), entry.getValue()));
									}
									modelInputType.setSelKeyVals(keyValues);
								} catch (Exception e) {
									logger.debug("输入有误:"
											+ model.getModelName()
											+ "/"
											+ property.getName()
											+ "uiInputUpdate/inputtype/selKeyVals");
								}
							}
						}
					}
					// inputtype 默认值的数据绑定问题
					if (modelInputType.getDftVal() == null // 如果没有设置默认值，则尝试从前台获取数据
							|| modelInputType.getDftVal().equals("")) {
						String webkey = inputType.dftWebVal();
						if (webkey != null && !webkey.equals("")) {
							Object webval = inputMap.get(webkey);
							if (webval != null) {
								modelInputType.setDftVal("" + webval);
							} else {
								modelInputType.setDftVal("");
							}
						}
					}
					if (modelInputType.getDftVal() == null // 如果没有设置默认值，则尝试从前台名为字段名的字段中获取数据
							|| modelInputType.getDftVal().equals("")) {
						String webkey = property.getName();
						if (webkey != null && !webkey.equals("")) {
							Object webval = inputMap.get(webkey);
							if (webval != null) {
								modelInputType.setDftVal("" + webval);
							} else {
								modelInputType.setDftVal("");
							}
						}
					}

					// 提交时的默认值及初始化时的默认值，自动根据当前字段的类型进行设置
					if (typeLower.contains("int") || typeLower.contains("long")) {
						if (modelUiInput// 如果字段为整数，添加数字验证器
								.getValidator(Filter.NUMBER) == null) {
							ModelValidator modelValidator = new ModelValidator();
							modelValidator.setFilter(Filter.NUMBER);
							modelUiInput.addValidator(modelValidator);
							logger.warn(model.getModelName() + "/"
									+ property.getName() + ":"
									+ "当前字段为int或long类型，但是未设置数字过滤器，将自动添加该过滤器");
						}

						modelAutoAjax
								.setAutoAjaxDftVal(toIntString(modelAutoAjax
										.getAutoAjaxDftVal()));
					} else if (typeLower.contains("double")
							|| typeLower.contains("float")) {
						if (modelUiInput // 如果字段为小数，添加小数验证器
								.getValidator(Filter.DOUBLE) == null) {
							ModelValidator modelValidator = new ModelValidator();
							modelValidator.setFilter(Filter.DOUBLE);
							modelUiInput.addValidator(modelValidator);
							logger.warn(model.getModelName()
									+ "/"
									+ property.getName()
									+ ":"
									+ "当前字段为float或double类型，但是未设置小数检过滤器，将自动添加该过滤器");
						}

						modelAutoAjax
								.setAutoAjaxDftVal(toDoubleString(modelAutoAjax
										.getAutoAjaxDftVal()));
					} else if (typeLower.contains("date")) {
						modelAutoAjax.setAutoAjaxDftVal(toDateString(
								modelAutoAjax.getAutoAjaxDftVal(),
								modelInputType.getInputType()));

						// 如果当前数据类型为date，则需要判定数据格式,且如果不匹配，则设置为""
						modelInputType.setDftVal(toDateStringCvt(
								modelInputType.getDftVal(),
								modelInputType.getInputType()));
					} else if (typeLower.contains("string")) {
						modelAutoAjax.setAutoAjaxDftVal(toDateString(
								modelAutoAjax.getAutoAjaxDftVal(),
								modelInputType.getInputType()));
					}

					properties.add(property);
				}
			}

			// 进行排序
			Collections.sort(properties, new Comparator<ModelProperty>() {

				@Override
				public int compare(ModelProperty o1, ModelProperty o2) {
					int group1, group2;
					int order1, order2;
					int suborder1, suborder2;

					group1 = o1.getUiInputUpdate().getPosition().getGroup();
					group2 = o2.getUiInputUpdate().getPosition().getGroup();
					order1 = o1.getUiInputUpdate().getPosition().getOrder();
					order2 = o2.getUiInputUpdate().getPosition().getOrder();
					suborder1 = o1.getUiInputUpdate().getPosition()
							.getSuborder();
					suborder2 = o2.getUiInputUpdate().getPosition()
							.getSuborder();

					if (group1 != group2) {
						return group1 - group2;
					} else {
						if (order1 != order2)
							return order1 - order2;
						else {
							return suborder1 - suborder2;
						}
					}
				}
			});

			ModelProperty groupfirst = null;
			// Positon设置组内第一个元素的groupname,防止因为修改了group后忘了把groupname修改回来
			for (int i = 0; i < properties.size(); i++) {
				ModelProperty property = properties.get(i);

				// 第一个元素为组的开始元素
				if (i == 0) {
					if (groupfirst != null
							&& (groupfirst.getUiInputUpdate().getPosition()
									.getGroupname() == null || groupfirst
									.getUiInputUpdate().getPosition()
									.getGroupname().equals(""))) {
						groupfirst.getUiInputUpdate().getPosition()
								.setGroupname("未知分组");
					}
					groupfirst = property;
				} else if (i - 1 >= 0 // 前一个元素存在且与当前元素的组号不一样
						&& (properties.get(i - 1).getUiInputUpdate()
								.getPosition().getGroup() != property
								.getUiInputUpdate().getPosition().getGroup())) {
					if (groupfirst != null
							&& (groupfirst.getUiInputUpdate().getPosition()
									.getGroupname() == null || groupfirst
									.getUiInputUpdate().getPosition()
									.getGroupname().equals(""))) {
						groupfirst.getUiInputUpdate().getPosition()
								.setGroupname("未知分组");
					}
					groupfirst = property;
				}

				String groupname = property.getUiInputUpdate().getPosition()
						.getGroupname();
				if (groupname != null && !groupname.equals("")) {
					// 如果当前元素有值，则设置首元素的groupname值
					groupfirst.getUiInputUpdate().getPosition()
							.setGroupname(groupname);
				}
			}

			model.setProperties(properties);
			return model;
		} catch (ClassNotFoundException e) {
		}

		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.setSpringContext(context);
	}

	public ApplicationContext getSpringContext() {
		return springContext;
	}

	public void setSpringContext(ApplicationContext springContext) {
		this.springContext = springContext;
	}

	private String toIntString(String val) {
		if (val == null // 如果没有设置默认值，则设置为0;
				|| val.equals(""))
			return "0";
		else {
			int dftval = 0;
			try {
				dftval = Integer.parseInt(val);
			} catch (Exception e) {
				dftval = 0;
			}
			return "" + dftval;
		}
	}

	private String toDoubleString(String val) {
		if (val == null // 如果没有设置默认值，则设置为0;
				|| val.equals(""))
			return "0.0";
		else {
			double dftval = 0.0;
			try {
				dftval = Double.parseDouble(val);
			} catch (Exception e) {
				dftval = 0.0;
			}
			return "" + dftval;
		}
	}

	/**
	 * 将时间进行检查，如果检查不通过，则设置为时间默认值
	 * 
	 * @param val
	 * @param type
	 * @return
	 */
	private String toDateString(String val, ControlType type) {
		val = val.replaceAll("T", " ");
		if (type.equals(ControlType.DAY)) {
			String pattern = "19[0-9]{2}|2[0-9]{3}";// 时间>19**年<2***年，目前可用
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(val);
			if (!m.matches()) {
				return "1970";
			}
		} else if (type.equals(ControlType.MONTH)) {
			String pattern = "(19[0-9]{2}|2[0-9]{3})-(0[1-9]|1[0-2])";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(val);
			if (!m.matches()) {
				return "1970-01";
			}
		} else if (type.equals(ControlType.DAY)) {
			String pattern = "(19[0-9]{2}|2[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|1[1-9]|2[1-9]|31)";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(val);
			if (!m.matches()) {
				return "1970-01-01";
			}
		} else if (type.equals(ControlType.TIME)) {
			String pattern = "(19[0-9]{2}|2[0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|1[1-9]|2[1-9]|31) ([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(val);
			if (!m.matches()) {
				return "1970-01-01 00:00:00";
			}
		}
		return val;
	}

	// 将时间进行检查，如果是默认值，则转换为""
	private String toDateStringCvt(String val, ControlType type) {
		String dateValue = toDateString(val, type);
		if (type.equals(ControlType.DAY)) {
			if (dateValue.equals("1970"))
				return "";
		} else if (type.equals(ControlType.MONTH)) {
			if (dateValue.equals("1970-01"))
				return "";
		} else if (type.equals(ControlType.DAY)) {
			if (dateValue.equals("1970-01-01"))
				return "";
		} else if (type.equals(ControlType.TIME)) {
			if (dateValue.equals("1970-01-01 00:00:00"))
				return "";
		}
		return val;
	}
}
