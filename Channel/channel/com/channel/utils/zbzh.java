package com.channel.utils;

public class zbzh {
	/**
	 * 将度分秒经度转换成经纬度 030:51:39.326320N 120:21:58.505640E
	 * 
	 * @param sfm
	 * @return
	 */
	public static Double dfmtodouble(String dfm) {
		Double d, f, m;
		try {
			dfm = dfm.replace("N", "");
			dfm = dfm.replace("E", "");
			String[] strs = dfm.split(":");

			if (strs != null && strs.length == 3) {
				d = Double.parseDouble(strs[0]);
				f = Double.parseDouble(strs[1]);
				m = Double.parseDouble(strs[2]);

				return d + f / 60 + m / 3600;
			}

		} catch (Exception e) {
			
		}
		return 0.0;
	}
	
	public static void main(String[] args) {
		System.out.println(dfmtodouble("030:36:45.496765N"));
		System.out.println(dfmtodouble("120:16:32.830209E"));
	}
}
