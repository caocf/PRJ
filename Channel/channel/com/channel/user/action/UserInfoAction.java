package com.channel.user.action;

import java.util.Date;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import com.channel.model.user.CXtUser;
import com.channel.user.service.UserInfoService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.utils.Md5Secure;

@Controller
public class UserInfoAction extends BaseAction {
    @Resource(name = "userinfoservice")
    private UserInfoService userInfoService;
    private int id;// 用户id
    private String username;// 用户名
    private String password;// 密码
    private String oldpassword;// 旧密码
    private String name;// 姓名
    private String tel;// 电话
    private String email;// 电子邮件
    private int title;// 职务
    private int jstatus;// 在职状态
    private String lawid;// 执法证编号
    private int ustatus;//用户状态
    private int department;//部门,组织机构
    private String content;//内容
    private BaseResult result;
    private int page;
    private int rows;
    private String roles;

    /**
     * 查询用戶信息
     *
     * @return
     */
    public String queryUserInfo() {
        result = this.userInfoService.queryUserInfo(id);
        return "success";
    }

    /**
     * 根据用户名查询用戶信息
     * (department对应)
     *
     * @return
     */
    public String queryUserInfoByName() {
        result = this.userInfoService.queryUserInfoByName(department, content, page, rows);
        return "success";
    }

    /**
     * 修改用戶信息
     *
     * @return
     */
    public String updateUserInfo() {
        result = this.userInfoService.updateUserInfo(id, name, tel, email,
                lawid);
        return "success";
    }

    /**
     * 修改密码
     *
     * @return
     */
    public String updateUserPassword() {
        result = this.userInfoService.updateUserPassword(id, password,
                oldpassword);
        return "success";
    }

    /**
     * 添加用戶
     *
     * @return
     */
    public String addUserInfo() {
        CXtUser user = new CXtUser(username, Md5Secure.encode(password), name, tel, email, title, jstatus, lawid, ustatus, department, new Date(), new Date());
        result = this.userInfoService.addUserInfo(user,roles);
        return "success";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getLawid() {
        return lawid;
    }

    public void setLawid(String lawid) {
        this.lawid = lawid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getUstatus() {
        return ustatus;
    }

    public void setUstatus(int ustatus) {
        this.ustatus = ustatus;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

}
