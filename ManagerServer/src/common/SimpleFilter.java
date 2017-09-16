package common;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Admin on 2016/5/13.
 */
@Component
public class SimpleFilter implements Filter
{
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        /*String path=((HttpServletRequest) req).getServletPath();
        String contextPath=((HttpServletRequest) req).getContextPath();//System.out.println(contextPath+path);
        HttpServletRequest hreq = (HttpServletRequest)req;
        HttpServletResponse hres=(HttpServletResponse)res;
        HttpSession session=hreq.getSession(true);

        String dirs[]={"/QeuryAccount","/InnerLogin","/LoginPhone","/PageLogin","/Login/login.jsp","/Login/login.js","/js/common/jquery-1.10.2.min.js","/image/login/login_icon.png","/image/login/bt_login_normal.png",
                "/Login/login.css","/Code","/image/favicon.ico","/image/login/login_bg.jpg","/image/login/content_bg.png","/image/login/bt_login_hover.png",
                "/image/login/bt_login_click.png"};

        Object user=session.getAttribute("name");
        if(user==null)
        {
            for(String s:dirs)
            {
                if(path.equals(s))
                {
                    chain.doFilter(req, res);
                    return;
                }
            }
            hres.sendRedirect("/");
            return;
        }*/
        chain.doFilter(req, res);
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy() {}

}
