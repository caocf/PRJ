package com.visionagent

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Will on 2017/5/31 13:45.
 */
class TestServlet : HttpServlet() {
    public override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        print("do Post")
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        print("do Get")
    }
}
