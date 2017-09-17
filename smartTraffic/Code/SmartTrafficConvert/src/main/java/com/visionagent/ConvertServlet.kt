package com.visionagent

import com.alibaba.fastjson.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Will on 2017/5/31 11:48.
 */
class ConvertServlet : HttpServlet() {

    //    private static final String URL_STR = "http://localhost:8080/SmartTrafficConvert/test";

    @Throws(ServletException::class, IOException::class)
    public override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        doPost(req, resp)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val action = req.getParameter("action")
        val param = req.getParameter("param")
        val jsonParam = JSONObject.parseObject(param)
        val result = post(URL_STR + action, jsonParam)
        resp.setHeader("Access-Control-Allow-Origin", "*")
        resp.setHeader("Content-type", "text/html;charset=UTF-8")
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
        resp.setHeader("Access-Control-Max-Age", "3600")
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type")
        resp.setHeader("Access-Control-Allow-Credentials", "true")
        resp.characterEncoding = "utf-8"
        val pw = resp.writer
        pw.write(JSONObject.parse(result).toString())
        pw.flush()
    }

    companion object {

        private val URL_STR = "http://zhcx.jxjtj.gov.cn/SmartTraffic/"

        private fun post(urlStr: String, param: JSONObject): String {
            val result = StringBuilder()
            val os: OutputStream?
            val br: BufferedReader?
            val url = URL(urlStr)
            val connection = url.openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.doInput = true
            connection.doOutput = true
            connection.useCaches = false
            connection.requestMethod = "POST"
            connection.setRequestProperty("connection", "keep-alive")
            connection.setRequestProperty("Charsert", "UTF-8")
            val sb = StringBuilder()
            for ((key, value) in param) {
                sb.append(key)
                        .append("=")
                        .append(value)
                        .append("&")
            }
            var paramStr = sb.toString()
            if (sb.isNotEmpty()) {
                paramStr = sb.substring(0, sb.length - 1)
            }
            os = connection.outputStream
            os!!.write(paramStr.toByteArray(charset("utf-8")))
            os.flush()

            br = BufferedReader(InputStreamReader(connection.inputStream, "utf-8"))
            br.forEachLine { result.append(it) }
            os.close()
            br.close()
            return result.toString()
        }
    }
}
