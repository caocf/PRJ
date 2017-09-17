package com.sts.sms.action;

public class SendMessage {
	/*public static void main(String[] args){
		String[] mobiles={"15958334882"};
		
		SendSMS(mobiles,"【智慧出行】您好！感谢您登录智慧出行。",5);
	}*/
	/**
	 * 发送短信、可以发送定时和即时短信
	 * sendSMS(String[] mobiles,String smsContent, String addSerial, int smsPriority)
	 * 1、mobiles 手机数组长度不能超过1000
	 * 2、smsContent 最多500个汉字或1000个纯英文、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内 
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、priority 优先级范围1~5，数值越高优先级越高(相对于同一序列号)
	 * 5、其它短信发送请参考使用手册自己尝试使用
	 * 
	 * 返回值
	 * -1	发送信息失败（短信内容长度越界）
	 * 0	短信发送成功
	 * 17	发送信息失败（未激活序列号或序列号和KEY值不对，或账户没有余额等）
	 * 101	客户端网络故障
	 * 305	服务器端返回错误，错误的返回值（返回值不是数字字符串）
	 * 307	目标电话号码不符合规则，电话号码必须是以0、1开头
	 * 997	平台返回找不到超时的短信，该信息是否成功无法确定
	 * 303	由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定
	 */
	public static int SendSMS(String[] mobiles,String smsContent,int priority) {
		try {
			//Client sdkclient=new Client("0SDK-EAA-6688-JEXOM","461440"); 
			int i=SingletonClient.getClient().sendSMS(mobiles, smsContent,priority);
			System.out.println("SendSMS====="+i);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 软件序列号注册、或则说是激活、软件序列号首次使用必须激活
	 * registEx(String serialpass)
	 * 1、serialpass 软件序列号密码、密码长度为6位的数字字符串、软件序列号和密码请通过亿美销售人员获取
	 */
	public static void RegistSMS() {
		int i = SingletonClient.getClient().registEx(SingletonClient.getKey());
		System.out.println("testTegistEx:" + i);
	}
	/**
	 * 软件注销
	 * 1、软件注销后像发送短信、接受上行短信接口都无法使用
	 * 2、软件可以重新注册、注册完成后软件序列号的金额保持注销前的状态
	 */
	public static void Logout() {
		try {
			int a = SingletonClient.getClient().logout();
			System.out.println("testLogout:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
