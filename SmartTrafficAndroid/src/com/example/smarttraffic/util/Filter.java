package com.example.smarttraffic.util;

import java.util.ArrayList;
import java.util.List;

public class Filter
{

	public static String filterImage(String html)
	{
		String regex = "<img.*?>";

		String result = html.replaceAll(regex, "");

		result = result.replaceAll("<img>", "");

		return result;

	}

	public static String filterHtmlStyle(String strHtml)
	{
		if(strHtml==null)
			return "";
		
		// regex_str="<script type=\\s*[^>]*>[^<]*?</script>";//替换<script>内容</script>为空格
		String regex_str = "(?is)<script[^>]*>.*?</script>";// 替换<script>内容</script>为空格
		strHtml = strHtml.replaceAll(regex_str, "");

		// regex_str="<script type=\\s*[^>]*>[^<]*?</script>";//替换<style>内容</style>为空格
		regex_str = "(?is)<style[^>]*>.*?</style>";// 替换<style>内容</style>为空格
		strHtml = strHtml.replaceAll(regex_str, "");

		return strHtml;
	}

	// 获取HTML中文字部分
	public static String filterHTML(String strHtml)
	{
		// regex_str="<script type=\\s*[^>]*>[^<]*?</script>";//替换<script>内容</script>为空格
		String regex_str = "(?is)<script[^>]*>.*?</script>";// 替换<script>内容</script>为空格
		strHtml = strHtml.replaceAll(regex_str, "");

		// regex_str="<script type=\\s*[^>]*>[^<]*?</script>";//替换<style>内容</style>为空格
		regex_str = "(?is)<style[^>]*>.*?</style>";// 替换<style>内容</style>为空格
		strHtml = strHtml.replaceAll(regex_str, "");

		// regex_str = "(&nbsp;)+";//替换&nbsp;为空格
		regex_str = "(?i)&nbsp;";// 替换&nbsp;为空格
		strHtml = strHtml.replaceAll(regex_str, " ");

		// regex_str = "(\r\n)*";//替换\r\n为空
		regex_str = "[\r\n]*";// 替换\r\n为空
		strHtml = strHtml.replaceAll(regex_str, "");

		// regex_str = "<[^<]*>";//替换Html标签为空
		regex_str = "<[^<>]*>";// 替换Html标签为空
		strHtml = strHtml.replaceAll(regex_str, "");

		// regex_str = "\n*";//替换\n为空
		regex_str = "\n*";// 替换\n为空
		strHtml = strHtml.replaceAll(regex_str, "");

		// 可以这样
		regex_str = "\t*";// 替换\t为空
		strHtml = strHtml.replaceAll(regex_str, "");

		// 可以
		regex_str = "'";// 替换'为’
		strHtml = strHtml.replaceAll(regex_str, "’");

		// 可以
		regex_str = " +";// 替换若干个空格为一个空格
		strHtml = strHtml.replaceAll(regex_str, "  ");

		regex_str = "<.+?>";

		String strOutput = strHtml.replaceAll(regex_str, "");// 替换掉"<"和">"之间的内容
		strOutput = strOutput.replaceAll("<", "");
		strOutput = strOutput.replaceAll(">", "");
		strOutput = strOutput.replaceAll("&nbsp;", "");

		return strOutput;
	}

	public static boolean[] filterType(int type, int size)
	{
		boolean[] result = new boolean[size];

		for (int i = 0; i < size; i++)
		{
			if (type % 10 == 1)
			{
				result[size - 1 - i] = true;
			} else
			{
				result[size - 1 - i] = false;
			}

			type /= 10;
		}

		return result;
	}

	public static List<Integer> filterTypeForInt(int type, int size)
	{
		boolean[] result = filterType(type, size);
		List<Integer> r = new ArrayList<Integer>();

		for (int i = 0; i < result.length; i++)
		{
			if (result[i])
				r.add(i + 1);

		}

		return r;
	}

}
