package net.controller;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import common.*;
import net.modol.*;
import net.service.LawService;
import net.service.UserService;
import org.apache.commons.fileupload.FileUpload;
import org.apache.poi.xwpf.converter.xhtml.SimpleContentHandler;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.hibernate.transform.ToListResultTransformer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Admin on 2016/7/13.
 */
@Controller
public class Law {
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    LawService lawService;

    //添加缘由
    @RequestMapping(value = "AddLawReason", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult AddLawReason(HttpServletRequest request) throws IOException {
        String s3 = request.getParameter("reason");

        LawReasonEN lawReasonEN = new LawReasonEN();
        lawReasonEN.setReason(s3);
        int id = lawService.saveEN(lawReasonEN);
        return new BaseResult(id, "添加成功");
    }

    //获取缘由列表
    @RequestMapping(value = "LawReasons", method = RequestMethod.POST)
    @ResponseBody
    public List<?> LawReasons(HttpServletRequest request) throws IOException {
        return lawService.LawReasons();
    }

    //提交取证
    @RequestMapping(value = "evidence", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult SubmitEvidence(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("firstman");
        String s2 = request.getParameter("secman");
        String s3 = request.getParameter("target");
        String s4 = request.getParameter("reason");
        String s5 = request.getParameter("detail");
        String s6 = request.getParameter("location");
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");
        String s7 = request.getParameter("type");
        String issimple = request.getParameter("issimple");
        Date date = new Date();

        LawBaseEN lawBaseEN = new LawBaseEN();
        lawBaseEN.setFirstman(s1);
        lawBaseEN.setSecman(s2);
        lawBaseEN.setTarget(s3);
        lawBaseEN.setReason(s4);
        lawBaseEN.setDetail(s5);
        lawBaseEN.setLocation(s6);
        lawBaseEN.setLat(Double.valueOf(lat));
        lawBaseEN.setLon(Double.valueOf(lon));
        lawBaseEN.setChecker("");
        lawBaseEN.setStatus("待审核");
        lawBaseEN.setSumbdate(date);
        lawBaseEN.setIssimple(issimple);//是否简易执法
        LawTypeEN type = lawService.LawTypeByID(Integer.valueOf(s7));
        if (type != null)
            lawBaseEN.setTypeEN(type);
        int result = lawService.saveEN(lawBaseEN);
        //保存证据
        List<String> list = FileUpLoad.uploadFiles(request, "/Evidence/" + result + "/", "evidence");
        for (String s : list)
        {
            LawEvidencesEN lawEvidencesEN = new LawEvidencesEN();
            lawEvidencesEN.setDir(s);
            lawEvidencesEN.setLawBaseEN(lawBaseEN);
            lawService.saveEN(lawEvidencesEN);
        }
        return new BaseResult(result, "取证");
    }

    //获取全部违章类别
    @RequestMapping(value = "AllLawType", method = RequestMethod.POST)
    @ResponseBody
    public List<?> AllLawType(HttpServletRequest request) throws IOException {
        return lawService.AllLawType();
    }

    //按执法人名称获取取证列表(执法人查看)
    @RequestMapping(value = "myevidences", method = RequestMethod.POST)
    @ResponseBody
    public BaseRecords getEvidencesByName(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("name");
        List list = lawService.getEvidencesByName(s1);
        return new BaseRecords(list);
    }

    //按id获取文件路径
    @RequestMapping(value = "evidencedir", method = RequestMethod.POST)
    @ResponseBody
    public List<?> getEvidenceDirsByID(HttpServletRequest request) throws IOException {
        int id = Integer.valueOf(request.getParameter("id"));

        return lawService.getEvidenceDirsByID(id);
    }

    //修改取证
    @RequestMapping(value = "ModifyEV", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult ModifyEV(HttpServletRequest request) throws IOException {
        String s0 = request.getParameter("id");
        String s3 = request.getParameter("target");
        String s4 = request.getParameter("reason");
        String s5 = request.getParameter("detail");
        String s6 = request.getParameter("location");
        String lat = request.getParameter("lat");
        String lon = request.getParameter("lon");

        String s8 = request.getParameter("oldfileStrin");
        String oldfileStrin[] = s8.split(",");
        Date date = new Date();

        LawBaseEN lawBaseEN = lawService.EvidenceByID(Integer.valueOf(s0));

        lawBaseEN.setTarget(s3);
        lawBaseEN.setReason(s4);
        lawBaseEN.setDetail(s5);
        lawBaseEN.setLocation(s6);
        lawBaseEN.setLat(Double.valueOf(lat));
        lawBaseEN.setLat(Double.valueOf(lon));
        lawBaseEN.setChecker("");
        lawBaseEN.setStatus("待审核");
        lawBaseEN.setSumbdate(date);

        int result = lawService.updateEN(lawBaseEN);
        //删除老的
        List<String> dir = lawService.deleteEvidenceDirsByURL(oldfileStrin);
        for (String s : dir) {
            String path = request.getSession().getServletContext().getRealPath(s);
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
        //重新保存
        List<String> list = FileUpLoad.uploadFiles(request, "Evidence/" + s0 + "/", "evidence");
        for (String s : list) {
            LawEvidencesEN lawEvidencesEN = new LawEvidencesEN();
            lawEvidencesEN.setDir(s);
            lawEvidencesEN.setLawBaseEN(lawBaseEN);
            lawService.saveEN(lawEvidencesEN);
        }
        return new BaseResult(result, "取证");
    }

    //按条件获取取证列表
    @RequestMapping(value = "evidencelist", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getEvidencesByStatus(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("status");
        String status = s1 == null ? "" : s1.trim();
        String s2 = request.getParameter("type");
        String type = s2 == null ? "" : s2.trim();
        String s3 = request.getParameter("tip");
        String tip = s3 == null ? "" : s3.trim();

        String page = request.getParameter("page");
        BaseResult ret = lawService.getEvidencesByStatus(status, tip, type, Integer.valueOf(page));
        return ret;
    }

    //提交违章审核
    @RequestMapping(value = "postcheck", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult postCheck(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("id");
        String s2 = request.getParameter("isillegalBoolean");
        String s3 = request.getParameter("descr");
        String s4 = request.getParameter("checker");
        LawBaseEN lawBaseEN = lawService.EvidenceByID(Integer.valueOf(s1));
        lawBaseEN.setStatus("已审核");
        lawBaseEN.setChecker(s4);
        lawBaseEN.setDescr(s3);
        lawBaseEN.setIsllegal(s2);
        lawService.updateEN(lawBaseEN);
        return new BaseResult(1, "");
    }

    //按ID获取取证
    @RequestMapping(value = "EvidenceByID", method = RequestMethod.POST)
    @ResponseBody
    public LawBaseEN EvidenceByID(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("id");

        LawBaseEN l = lawService.EvidenceByID(Integer.valueOf(s1));
        return l;
    }

    //修改违章类别
    @RequestMapping(value = "ChangeLawType", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult ChangeLawType(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("id");
        String s2 = request.getParameter("typeid");

        LawBaseEN l = lawService.EvidenceByID(Integer.valueOf(s1));
        LawTypeEN type = lawService.LawTypeByID(Integer.valueOf(s2));
        if (type != null)
            l.setTypeEN(type);
        lawService.updateEN(l);

        return new BaseResult(1, "");
    }

    //提交违章对象
    @RequestMapping(value = "CommitTarget", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CommitTarget(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("name");
        String s2 = request.getParameter("gender");
        String s3 = request.getParameter("cert");
        String s4 = request.getParameter("lawname");
        String s5 = request.getParameter("duty");
        String s6 = request.getParameter("tel");
        String s7 = request.getParameter("location");

        LawSimpletargetEN lawSimpletargetEN = new LawSimpletargetEN();
        lawSimpletargetEN.setName(s1);
        lawSimpletargetEN.setGender(s2);
        lawSimpletargetEN.setCert(s3);
        lawSimpletargetEN.setLawname(s4);
        lawSimpletargetEN.setDuty(s5);
        lawSimpletargetEN.setTel(s6);
        lawSimpletargetEN.setLocation(s7);

        long num = lawService.saveEN(lawSimpletargetEN);
        return new BaseResult((int) num, "数量");
    }



    //提交处罚
    @RequestMapping(value = "CommitPenalty", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult CommitPenalty(HttpServletRequest request) throws Exception {
        String s1 = request.getParameter("depend");
        String s2 = request.getParameter("n1");
        String s3 = request.getParameter("n2");
        String s4 = request.getParameter("n3");
        String s5 = request.getParameter("suggest");
        String s6 = request.getParameter("number");
        String s7 = request.getParameter("evidid");
        String status = request.getParameter("status");

        String result=request.getParameter("result");


        LawPenaltyEN lawPenaltyEN = new LawPenaltyEN();
        lawPenaltyEN.setDepend(s1);
        lawPenaltyEN.setItem1(s2);
        lawPenaltyEN.setItem2(s3);
        lawPenaltyEN.setItem3(s4);
        lawPenaltyEN.setSuggest(s5);
        lawPenaltyEN.setNumber(s6);

        LawBaseEN lawBaseEN = lawService.EvidenceByID(Integer.valueOf(s7));
        lawBaseEN.setStatus(status);
        lawBaseEN.setIsllegal(result);
        lawService.updateEN(lawBaseEN);
        lawPenaltyEN.setLawEvidencesEN(lawBaseEN);
        long num = lawService.saveEN(lawPenaltyEN);

        //生成文档
        String s9 = request.getParameter("actiontime");
        String s10 = request.getParameter("location");
        String s8 = request.getParameter("targetid");
        String s11 = request.getParameter("reason");
        lawPenaltyEN.setId((int) num);

        LawSimpletargetEN simpletargetEN = lawService.TargetByID(Integer.valueOf(s8));
        String p = request.getServletContext().getRealPath("/Doc/template.docx");
        File file = new File(p);
        lawService.writeDoc(file, request, simpletargetEN, lawPenaltyEN, s9, s10, s11);

        return new BaseResult((int) num, simpletargetEN.getName());
    }

    //电子签章
    @RequestMapping(value = "Sign", method = RequestMethod.POST)
    public String Sign(HttpServletRequest request) throws Exception {
        lawService.doSign(request);
        return "ok";
    }

    //电子签章
    @RequestMapping(value = "signpc", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult SignPc(HttpServletRequest request) throws Exception
    {
        String pid=request.getParameter("id");



        lawService.doSign(request);
        LawPenaltyEN penaltyEN=lawService.PenaltyByID(Integer.valueOf(pid));
        LawBaseEN lawBaseEN=penaltyEN.getLawEvidencesEN();

        lawBaseEN.setIsllegal("构成违章");
        lawBaseEN.setStatus("已审核");
        lawService.updateEN(lawBaseEN);
        return new BaseResult(1, "");
    }

    //图片签章
    @RequestMapping(value = "PicSign", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult PicSign(HttpServletRequest request) throws Exception {

        return lawService.PicSign(request);
    }

    //按姓名获取当事人
    @RequestMapping(value = "TargetByName", method = RequestMethod.POST)
    @ResponseBody
    public LawSimpletargetEN TargetByID(HttpServletRequest request) throws IOException {
        String s3 = request.getParameter("name");

        return lawService.TargetByName(s3);
    }

    //按执法人获取简易执法列表
    @RequestMapping(value = "SimpleListByName", method = RequestMethod.POST)
    @ResponseBody
    public List<?> SimpleListByName(HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");

        return lawService.SimpleListByName(name);
    }

    //按lawID获取取证
    @RequestMapping(value = "showevidence", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult showEvidence(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("id");
        BaseResult result = lawService.showEvidence(Integer.valueOf(s1));
        return result;
    }

    //按ID上传证据
    @RequestMapping(value = "uploadevidence", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult uploadEvidence(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        //保存证据
        LawBaseEN lawBaseEN = lawService.EvidenceByID(Integer.valueOf(id));
        List<String> list = FileUpLoad.uploadFiles(request, "Evidence/" + id + "/", "attachment");
        for (String s : list) {
            LawEvidencesEN lawEvidencesEN = new LawEvidencesEN();
            lawEvidencesEN.setDir(s);
            lawEvidencesEN.setLawBaseEN(lawBaseEN);
            lawService.saveEN(lawEvidencesEN);
        }
        return new BaseResult();
    }

    /**
     * 文件下载
     *
     * @return
     */
    @RequestMapping("/lawdownload")
    public void lawDownload(HttpServletRequest request,
                            HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        LawEvidencesEN en = lawService.showEvidenceById(Integer.valueOf(id));
        String path = request.getRealPath("/");
        String filepath = path + en.getDir();
        filepath.replace("/", "\\");
        filepath.replace("\\", "\\\\");
        FileDownload.download(filepath, request, response);
    }

    //取证统计
    @RequestMapping(value = "qztj", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult qztj(HttpServletRequest request) {
        String typeen = request.getParameter("typeen");
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        BaseResult result = lawService.qztj(Integer.valueOf(typeen), starttime, endtime);
        return result;
    }


    //进港分页
    @RequestMapping(value = "eviddt", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult evidDt(HttpServletRequest request) throws IOException {
        String temp = request.getParameter("typeen");
        int typeen = temp == null ? 0 : Integer.parseInt(temp.trim());
        String starttime = request.getParameter("starttime");
        String endtime = request.getParameter("endtime");
        String shipname = request.getParameter("shipname");
        String page = request.getParameter("page");
        BaseResult result = lawService.evidDt(shipname, typeen, starttime, endtime, Integer.parseInt(page));
        return result;
    }

    //执法查询
    @RequestMapping(value = "zfcx", method = RequestMethod.GET)
    public String zfcx(HttpServletRequest request, Model model) throws IOException {
        return "WEB-INF/ship/zfcx";
    }

    //简易执法
    @RequestMapping(value = "jycx", method = RequestMethod.GET)
    public String jyzf(HttpServletRequest request, Model model) throws IOException {
        return "WEB-INF/ship/jycx";
    }

    //一般执法
    @RequestMapping(value = "ybcx", method = RequestMethod.GET)
    public String ybzf(HttpServletRequest request, Model model) throws IOException {
        return "WEB-INF/ship/ybcx";
    }

    //跳转
    @RequestMapping(value = "zfcx_detail", method = RequestMethod.GET)
    public String zfcx_detail(HttpServletRequest request, Model model) throws IOException {
        String id = request.getParameter("id");
        model.addAttribute("id", id);
        String flag = request.getParameter("flag");
        model.addAttribute("flag", flag);
        return "WEB-INF/ship/zfcx_detail";
    }

    //执法分页
    @RequestMapping(value = "zfcxdt", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult zfcxDt(HttpServletRequest request) throws IOException {
        String flag = request.getParameter("flag");
        String shipname = request.getParameter("shipname");
        String page = request.getParameter("page");
        BaseResult result = lawService.zfcxDt(shipname, flag, Integer.parseInt(page));
        return result;
    }

    @RequestMapping(value = "showzfcx", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult showZfcx(HttpServletRequest request) throws IOException {
        String id = request.getParameter("id");
        BaseResult result = lawService.showZfcx(Integer.valueOf(id));
        return result;
    }

    //统计待审批数量
    @RequestMapping(value = "LawCheckCount", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult LawCheckCount(HttpServletRequest request) throws IOException {
        String type = request.getParameter("type");
        int count = lawService.LawCheckCount(type);

        return new BaseResult(count, "数量");
    }
}
