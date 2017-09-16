package com.huzhouport.leaveandovertime;

public class PushMsg {
	private int id; // ����ID
	private int msgbelong; //��Ϣ�����ĸ��û�
	
	private int pushmsgid;// ������ϢID
	private int userid;// �û�ID 0-->�������е�, -1-->�����ڲ�, -2-->�����ⲿ, >0-->����ĳ�û�.
	private int messageid;// ��Ϣ����ID�����ճ̰��ű���������֪ͨ������Ϣ������
	private int modulerid;// ģ��ID���Զ��壺1Ϊ�����ճ̰��ŵ���Ϣ��2Ϊ����֪ͨ�������Ϣ��3Ϊ���������������Ϣ;4Ϊ���鰲�ţ�5ΪΥ��ȡ֤; 6Ϊ��ҵ��Ѷ
	private int msgstatus;// ��Ϣ״̬�Զ��壺1δ����δ����2δ�����Ѷ���3������δ����4�������Ѷ�����Ϣ��ѯ��Ҫ��δ���͵ġ�
	private String pushmsgtime;// ����ʱ��
	
	// �û�ID 0-->�������е�, -1-->�����ڲ�, -2-->�����ⲿ, >0-->����ĳ�û�
	public static int USERID_BOTH = 0;
	public static int USERID_INNER = -1;
	public static int USERID_PUBLIC = -2;
	
	// 1δ����δ����2δ�����Ѷ���3������δ����4�������Ѷ���
	public static int MSGSTATUS_NOTPUSH_NOTREAD = 1;
	public static int MSGSTATUS_NOTPUSH_READ = 2;
	public static int MSGSTATUS_PUSHED_NOTREAD = 3;
	public static int MSGSTATUS_PUSHED_READ = 4;
	
	// ģ��ID���Զ��壺1Ϊ�����ճ̰��ŵ���Ϣ��2Ϊ����֪ͨ�������Ϣ��3Ϊ���������������Ϣ;4Ϊ���鰲�ţ�5ΪΥ��ȡ֤;6Ϊ��ҵ��Ѷ
	public static int MODULERID_SCHEDULE = 1;
	public static int MODULERID_NOTIFY = 2;
	public static int MODULERID_LEAVEANDOVERTIME = 3;
	public static int MODULERID_MEETING = 4;
	public static int MODULERID_ILLEGAL = 5;
	public static int MODULERID_INDUSTRYINFO = 6;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPushmsgid() {
		return pushmsgid;
	}

	public void setPushmsgid(int pushmsgid) {
		this.pushmsgid = pushmsgid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getMessageid() {
		return messageid;
	}

	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}

	public int getModulerid() {
		return modulerid;
	}

	public void setModulerid(int modulerid) {
		this.modulerid = modulerid;
	}

	public int getMsgstatus() {
		return msgstatus;
	}

	public void setMsgstatus(int msgstatus) {
		this.msgstatus = msgstatus;
	}

	public String getPushmsgtime() {
		return pushmsgtime;
	}

	public void setPushmsgtime(String pushmsgtime) {
		this.pushmsgtime = pushmsgtime;
	}

	public int getMsgbelong() {
		return msgbelong;
	}

	public void setMsgbelong(int msgbelong) {
		this.msgbelong = msgbelong;
	}
}
