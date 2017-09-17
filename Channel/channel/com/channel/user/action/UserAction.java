package com.channel.user.action;

import com.channel.bean.Constants;
import com.channel.user.service.UserService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.utils.ValidateCode;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
public class UserAction extends BaseAction {
    @Resource(name = "userservice")
    private UserService userService;
    private int id;// 用户id
    private String strids;// 用户删除id
    private String username;// 用户名
    private String password;// 密码
    private String oldpassword;// 旧密码
    private String name;// 姓名
    private String tel;// 联系电话
    private String email;// 邮箱
    private int title;// 职务
    private String lawid;// 执法证编号
    private int jstatus;// 在职状态
    private int department;// 部门
    private int dptflag = 0;// 是否递归 0不是1是
    private String content;// 查询内容
    private int loginflag;// 1授权登入2系统登陆
    private String Echo;// datatable
    private BaseResult result;
    private int page;
    private String roles;
    private int rows;

    /**
     * 验证码  change by 庄佳彬 at 2017-03-28
     */
    private String verifyCode;

    /**
     * 查询版本
     *
     * @return
     */
    public String queryVersion() {
        result = this.userService.queryVersion();
        return "success";
    }

    /**
     * 登录
     *
     * @return
     */
    public String Login() throws UnsupportedEncodingException {
//        result = this.userService.initPerm();
//        return "success";
// change by 庄佳彬 at 2017-03-28

//        result = this.userService.login(username, password);

        if(checkverifyCode(verifyCode)){
            result = this.userService.login(username, password);
        }else{
            result = new BaseResultFailed(Constants.VERIFY_CODE_ERROR,"验证码错误");
        }
        return "success";
    }


    /**
     * 验证码 change by 庄佳彬 at 2017-03-28
     * @return
     */
    public String getVerifyCodeImg(){
        try{
            //生成验证码
            ValidateCode.ValidatecodeImage image = ValidateCode.genValidateCodeImage(4,100,42);
            String code  = image.getValidatecode();
            BufferedImage bi = image.getImage();
            //获取outputstream对象
            OutputStream os = ServletActionContext.getResponse().getOutputStream();
            //图片
            ImageIO.write( bi,"jpg",os);
            //session存code值
            ActionContext.getContext().getSession().put(Constants.VERIFY_CODE,code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 登出
     *
     * @return
     */
    public String Logout() {
        result = this.userService.logout();
        return "success";
    }

    /**
     * 查询用户
     *
     * @return
     */
    public String queryUser() {
        result = this.userService.queryUser(department, dptflag, content, page,
                rows, Echo);
        return "success";
    }

    /**
     * 更新账户信息
     *
     * @return
     */
    public String updateUser() {
        result = this.userService.updateUser(id, username, password, name,
                tel, email, title, jstatus, lawid, roles);
        return "success";
    }

    /**
     * 禁用用户
     *
     * @return
     */
    public String disableUser() {
        result = this.userService.disableUser(id);
        return "success";
    }

    /**
     * 激活用户
     *
     * @return
     */
    public String enableUser() {
        result = this.userService.enableUser(id);
        return "success";
    }

    /**
     * 刪除用户
     *
     * @return
     */
    public String delUser() {
        result = this.userService.delUser(strids);
        return "success";
    }

    private boolean checkverifyCode(String verifyCode) {
        Map<String,Object> session = ServletActionContext.getContext().getSession();
        String code = (String) session.get(Constants.VERIFY_CODE);
        return code == null ? false: code.equalsIgnoreCase(verifyCode);
    }
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getJstatus() {
        return jstatus;
    }

    public void setJstatus(int jstatus) {
        this.jstatus = jstatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLawid() {
        return lawid;
    }

    public void setLawid(String lawid) {
        this.lawid = lawid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrids() {
        return strids;
    }

    public void setStrids(String strids) {
        this.strids = strids;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEcho() {
        return Echo;
    }

    public void setEcho(String echo) {
        Echo = echo;
    }

    private void getid() {
        // TODO Auto-generated method stub

    }

    private void setid() {
        // TODO Auto-generated method stub

    }

    public int getDptflag() {
        return dptflag;
    }

    public void setDptflag(int dptflag) {
        this.dptflag = dptflag;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
