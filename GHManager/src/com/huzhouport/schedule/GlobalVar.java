package com.huzhouport.schedule;

public class GlobalVar {
	public static final String FILE_SERVER_PATH="File/";//�ֻ��ڴ濨�����ַ
	
	public static final String FILE_DOWNLOAD_PATH="download/";//�ֻ��ڴ濨�����ַ
	
	public static final int SERVICE_TIME=5 * 60 * 60 * 1000;//5Сʱ
	
	public static final String FILE_DOWNLOAD_SUCCESS="���سɹ�,���ص��ڴ濨��download�ļ�����";//�ֻ��ڴ濨�����ַ
	
	public static final int RECODER_ICON=30;//����¼�ƽ�����־
	
	public static final int MAP_LOCATION_TIME=30*1000;//��ͼ��λ���ʱ��
	public static final int MAP_LOCATIONSIGN_TIME=15*1000;//��ͼ��λ���ʱ��
	public static final int Cruiselog_Time=10*1000;
	
	public static final int PORT_RANG=100;//��ͷ��Χ
	
	public static final int LOGLOGIN = 1;// ��¼

	public static final int LOGDELETE = 2;// ɾ��

	public static final int LOGSAVE = 3;// ���

	public static final int LOGUPDATE = 4;// �޸�

	public static final int LOGEXIT = 5;// �˳�

	public static final int LOGSEE = 6;// �鿴
	
	public static final int LOGCHECK=7;

	public static  int NUMBEROFUNREADSCHEDULE = 0;// δ���ճ̰�������
	
	public static  long PUSHTIMER = 1;//��λ���ӣ�Ĭ��1����

	public static String PUSHMSGBROADCAST = "com.huzhouport.pushmsg.broadcast";
	
	
	public static boolean  isexceptionexit=false; //timeService �Ƿ��쳣�˳� ��false�쳣�˳�
}
