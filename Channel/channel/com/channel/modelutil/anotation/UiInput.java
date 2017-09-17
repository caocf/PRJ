package com.channel.modelutil.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于自动生成新增编辑的前台页面
 *
 * @author DJ
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UiInput {
    /***********************
     * 位置信息
     **************************/
    // 分组
    public int group();

    // 分组排序
    public int order() default 0;

    // 用于自定义排序
    public int suborder() default 0;

    // 组名字
    public String groupname() default "";

    // 是否一行显示,如备注等信息需要
    public boolean oneline() default false;

    // 属性描述，用于新增编辑时左侧显示label
    public String desc() default "";

    // 在编辑模式下，该变量是否允许编辑
    public boolean editable() default true;

    // 是否强制该变量为隐藏
    public boolean hidden() default false;

    /**********************
     * 初始值化输入控件相关
     *************************/
    // 对应UI是input输入还是select等, 目前支持
    // input -->输入框
    // textarea --> 输入区域
    // selectyesno -->下拉选择框，只有是与否两个选项
    // selectdict --> 从字典表加载选项，如果inputtype为该类型，必须指定selectdictname字段
    // selectdpt --> 选择部门
    // selectxzqh --> 选择行政区划
    // selectdate --> 选择日期
    // selectyear --> 选择年份
    public String inputtype() default "input";

    // 输入是否只读
    public boolean readonly() default false;

    // 当inputtype为selectdict时设置有效,该值如何设置，需问亮
    public String selectdictname() default "";

    // 初始化控件时从何处获取值
    public String defaultval() default "";

    // 前台会传入一些传放在Map中，可使用此功能向前台传递默认值,具体应该与前台对应起来
    // 设置该值后，defaultval选项自动失效
    public String defaultvalfromweb() default "";

    /*************************
     * 校验相关
     ************************/
    // 是否为必须字段，如为必须，则会添加*表示必须输入,注： 如果设置了notempty过滤器，则该must值强制为true
    public boolean must() default false;

    /**********************
     * 表单提交相关
     ******************/
    // 是否自动提交
    public boolean autoajax() default true;

    // 表单提交时提交名
    public String autoajax_name() default "";

    // 表单提交时提交属性或VALUE
    public String autoajax_attr() default "";

    // 表单提交时，如果没有输入或输入为""，则自动以此属性进行提交
    public String autoajax_defaultval() default "-999999";
}
