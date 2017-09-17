package com.channel.dic.action;

import javax.annotation.Resource;

import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.impl.XzqhdmServiceImpl;
import org.springframework.stereotype.Controller;
import com.channel.dic.service.DicService;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

@Controller
public class DicAction extends BaseAction {
    private int id;
    private String name;
    private String namedesc;
    private String attrdesc;
    private BaseResult result;
    @Resource(name = "dicservice")
    private DicService dicService;


    /**
     * 查询所有职务
     *
     * @return
     */
    public String queryAllTitle() {
        result = this.dicService.queryAllTitle();
        return "success";
    }

    /**
     * 查询所有在职状态
     *
     * @return
     */
    public String queryAllJStatus() {
        result = this.dicService.queryAllJStatus();
        return "success";
    }

    /**
     * 查询字典字段属性
     *
     * @return
     */
    public String queryDicAttr() {
        result = this.dicService.queryDicAttr(name);
        return "success";
    }

    /**
     * 查询属性说明
     *
     * @return
     */
    public String queryAttrDesc() {
        result = this.dicService.queryAttrDesc(id);
        return "success";
    }

    /**
     * 查询名字说明
     *
     * @return
     */
    public String queryNameDesc() {
        result = this.dicService.queryNameDesc();
        return "success";
    }

    /**
     * 显示字典
     *
     * @return
     */
    public String queryDic() {
        result = this.dicService.queryDic(id);
        return "success";
    }

    /**
     * 添加字典
     *
     * @return
     */
    public String addDic() {
        result = this.dicService.addDic(name, namedesc, attrdesc);
        return "success";
    }

    /**
     * 编辑字典
     *
     * @return
     */
    public String updateDic() {
        result = this.dicService.updateDic(id, name, namedesc,
                attrdesc);
        return "success";
    }

    /**
     * 刪除字典
     *
     * @return
     */
    public String delDic() {
        result = this.dicService.delDic(name, id);
        return "success";
    }


    /**
     * 查询所有市区
     *
     * @return
     */
    public String queryCity() {
        result = this.dicService.queryCity();
        return "success";
    }

    /**
     * 查询本级行政区划
     *
     * @return
     */
    public String queryCityXzqh() {
        result = this.dicService.queryCityXzqh(id);
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

    public String getNamedesc() {
        return namedesc;
    }

    public void setNamedesc(String namedesc) {
        this.namedesc = namedesc;
    }

    public String getAttrdesc() {
        return attrdesc;
    }

    public void setAttrdesc(String attrdesc) {
        this.attrdesc = attrdesc;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }

}
