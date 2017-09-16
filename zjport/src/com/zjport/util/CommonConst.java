package com.zjport.util;

/**
 * 开发过程中的通用变量
 * Created by TWQ on 2016/8/11.
 */
public class CommonConst {


    /*  信息发布类型  */
    public static final String Information_Internet = "1";    //信息发布——网站信息

    public static final String Information_Board = "2";       //信息发布——情报板信息

    public static final String Information_Message = "3";     //信息发布——短信

    /*  信息发布状态  */
    public static final String InfoState_Wait_Approval = "1";    //信息发布——待审批状态

    public static final String InfoState_Approvaling = "2";      //信息发布——审批中状态

    public static final String InfoState_Rejected = "3";         //信息发布——被驳回状态

    public static final String InfoState_Send = "4";             //信息发布——已发布状态

    public static final String InfoState_Recall = "5";           //信息发布——已撤回状态

    /*  是否有效  */
    public static final Boolean VALID = true;            //有效

    public static final Boolean INVALID = false;          //无效

    /*  情报板颜色  */
    public static final String BOARD_COLOR_GREEN = "1";          //绿色
    public static final String BOARD_COLOR_YELLOW = "2";         //黄色
    public static final String BOARD_COLOR_RED = "3";            //红色

    /* 日程安排类型 */
    public static final String CALENDAR_COMMON = "1";          //日常任务
    public static final String CALENDAR_MEETING = "2";         //会议安排
    public static final String CALENDAR_WORK = "3";            //工作安排
    public static final String CALENDAR_ELSE = "4";            //其他任务


    /*  待办事项类别  */
    public static final String BACKLOG_INFORM_APPROVAL = "1";          //信息发布待办

    public static final String BACKLOG_CALENDAR = "2";        //日程安排待办

    /*  待办事项状态  */
    public static final Boolean BACKLOG_STATE_WAIT_FINISH = true;       //信息发布待完成

    public static final Boolean BACKLOG_STATE_FINISHED = false;         //信息发布已完成

    /*  portlet模块所处位置  */
    public static final String PORTLET_LEFT = "left";         //左侧portlet模块
    public static final String PORTLET_RIGHT = "right";         //右侧portlet模块

    /*  同意or不同意（通过or不通过）  */
    public static final String PASS = "1";          //通过
    public static final String NO_PASS = "0";       //不通过

    public static final int JSMC_REPEAT_CODE = 101;
    public static final String JSMC_REPEAT_INFO = "角色名称已存在,请重新输入";

    public static final int JSBH_ERROR_CODE = 102;
    public static final String JSBH_ERROR_INFO = "角色不存在";

    public static final int JS_INUSE_CODE = 103;
    public static final String JS_INUSE_INFO = "有人员相关联，不能删除";
}
