package com.channel.searchpro.action;

import com.channel.searchpro.service.SearchProService;
import com.common.action.result.BaseResult;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/3/7.
 */
@Controller
public class SearchProAction {
    @Resource(name = "searchproservice")
    private SearchProService searchProService;
    private String tname;
    private int fswlx;
    private String content;
    private List<String> contents;
    private BaseResult result;
    private String sEcho;// datatable
    private int page;
    private int rows;

    //载入字段
    public String loadField() throws ClassNotFoundException {
        result = this.searchProService.loadField(fswlx);
        return "success";
    }

    //高级搜索
    public String searchPro() {
        result = this.searchProService.searchPro(fswlx, contents, page, rows, sEcho);
        return "success";
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
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

    public String getSEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public int getFswlx() {
        return fswlx;
    }

    public void setFswlx(int fswlx) {
        this.fswlx = fswlx;
    }
}
