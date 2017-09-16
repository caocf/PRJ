package net.service;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.kinggrid.kgcore.enmu.KGServerTypeEnum;
import com.kinggrid.pdf.KGPdfHummer;
import com.kinggrid.pdf.executes.PdfSignature4KG;
import common.BaseResult;
import common.ImageToPDF;
import common.Time;
import net.bean.ChartBean;
import net.bean.QztjBean;
import net.modol.*;
import org.apache.poi.xwpf.converter.xhtml.SimpleContentHandler;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Admin on 2016/7/7.
 */
@Service
public class LawService extends BaseService {
    SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");

    //获取缘由列表
    public List<?> LawReasons() {
        Session session = sessionFactory.openSession();
        List<?> tENs = session.createCriteria(LawReasonEN.class).list();
        session.close();
        return tENs;
    }

    //按ID获取违章类型
    public LawTypeEN LawTypeByID(int id) {
        Session session = sessionFactory.openSession();
        LawTypeEN type = (LawTypeEN) session.createCriteria(LawTypeEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return type;
    }

    //获取所有违章类型
    public List<LawTypeEN> AllLawType() {
        Session session = sessionFactory.openSession();
        List<LawTypeEN> types = session.createCriteria(LawTypeEN.class)
                .list();
        session.close();
        return types;
    }

    //按执法人名称获取取证列表(执法人查看)（非简易）
    public List<?> getEvidencesByName(String name) {
        Session session = sessionFactory.openSession();
        List<LawBaseEN> user = session.createCriteria(LawBaseEN.class)
                .add(Restrictions.eq("issimple", "0"))
                .add(Restrictions.or(Restrictions.eq("firstman", name), Restrictions.like("secman", "%" + name + "%")))
                .addOrder(Order.desc("sumbdate"))
                .list();
        session.close();
        return user;
    }

    //按ID获取证据路径
    public List<String> getEvidenceDirsByID(int id) {
        Session session = sessionFactory.openSession();
        List<String> user = session.createCriteria(LawEvidencesEN.class)
                .setProjection(Projections.projectionList().add(Property.forName("dir")))
                .createCriteria("lawBaseEN")
                // .createAlias("lawBaseEN","law")
                .add(Restrictions.eq("id", id))
                .list();
        session.close();
        return user;
    }

    //按条件获取取证列表（审核）(非简易)
    public BaseResult getEvidencesByStatus(String status, String tip, String type, int page) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LawBaseEN.class)
                .add(Restrictions.eq("issimple", "0"))
                .add(Restrictions.like("status", "%" + status + "%"))
                .add(Restrictions.or(Restrictions.like("target", "%" + tip + "%"), Restrictions.like("reason", "%" + tip + "%")))
                .addOrder(Order.desc("sumbdate"))
                .createCriteria("typeEN")
                .add(Restrictions.like("status", "%" + type + "%"));


        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<?> lawBaseENs = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10).list();
        session.close();
        return new BaseResult(pages, (int) count, lawBaseENs);
    }

    //按ID获取取证
    public LawBaseEN EvidenceByID(int id) {
        Session session = sessionFactory.openSession();
        LawBaseEN lawBaseEN = (LawBaseEN) session.createCriteria(LawBaseEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return lawBaseEN;
    }

    //按ID删除证据列表
    public List<String> deleteEvidenceDirsByURL(String[] dirs) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<String> list = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            for (String s : dirs) {
                LawEvidencesEN user = (LawEvidencesEN) session.createCriteria(LawEvidencesEN.class)
                        .add(Restrictions.eq("dir", s))
                        .uniqueResult();
                session.delete(user);
                list.add(s);
            }

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
        return list;
    }

    //按ID获取当事人
    public LawSimpletargetEN TargetByID(int id) {
        Session session = sessionFactory.openSession();
        LawSimpletargetEN lawSimpletargetEN = (LawSimpletargetEN) session.createCriteria(LawSimpletargetEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return lawSimpletargetEN;
    }

    //按姓名获取当事人
    public LawSimpletargetEN TargetByName(String name) {
        Session session = sessionFactory.openSession();
        LawSimpletargetEN lawSimpletargetEN = (LawSimpletargetEN) session.createCriteria(LawSimpletargetEN.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
        session.close();
        return lawSimpletargetEN;
    }

    //用模板生成DOCX
    public void writeDoc(File demoFile, HttpServletRequest request, LawSimpletargetEN simpletargetEN, LawPenaltyEN lawPenaltyEN, String s11, String s22, String s33) throws Exception {
        String s1 = simpletargetEN.getName();
        String s2 = simpletargetEN.getGender();
        String s3 = simpletargetEN.getCert();
        String s4 = simpletargetEN.getLawname();
        String s5 = simpletargetEN.getDuty();
        String s6 = simpletargetEN.getTel();
        String s7 = simpletargetEN.getLocation();
        String s8 = lawPenaltyEN.getDepend();
        String s9 = lawPenaltyEN.getNumber();
        String s10 = sd2.format(new Date());
        int fileid = lawPenaltyEN.getId();
        File outpath = new File(request.getServletContext().getRealPath("/Doc/Penalty"));
        try {
            FileInputStream in = new FileInputStream(demoFile);
            XWPFDocument hdt = new XWPFDocument(in);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("name", s1);
            params.put("gender", s2);
            params.put("cert", s3);
            params.put("lawname", s4);
            params.put("duty", s5);
            params.put("tel", s6);
            params.put("address", s7);

            params.put("actime", s11);
            params.put("location", s22);
            params.put("reason", s33);
            params.put("law", s8);
            params.put("Penalty", s9);
            params.put("D", s10);

            Iterator<XWPFParagraph> iterator = hdt.getParagraphsIterator();
            XWPFParagraph para;
            while (iterator.hasNext()) {
                para = iterator.next();
                this.replaceInPara(para, params);
            }

            if (!outpath.exists()) {
                outpath.mkdirs();
            }

            OutputStream os = new FileOutputStream(outpath.getAbsolutePath() + "/" + String.valueOf(fileid) + ".docx");
            hdt.write(os);
            os.flush();
            os.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //转PDF
        word2pdf(new File(outpath.getAbsolutePath() + "/" + String.valueOf(fileid) + ".docx"), String.valueOf(fileid), request);
    }

    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs = para.getRuns();//获取变量
        for (int i = 0; i < runs.size(); i++) {
            String runText = runs.get(i).toString();
            System.out.println(runs.get(i).toString());
            String newtext = (String) params.get(runText);
            if (newtext != null) {
                //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                para.removeRun(i);

                XWPFRun wp = para.insertNewRun(i);
                wp.setText(newtext);
                wp.setFontSize(21);
            }
        }

    }

    //OpenOffice 转PDF
    private void word2pdf(File file, String fileName, HttpServletRequest request) throws Exception {
        File outputFile = new File(request.getServletContext().getRealPath("/Doc/Pdf/" + fileName + ".pdf"));
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        // connect to an OpenOffice.org instance running on port 8100
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                "127.0.0.1", 8100);
        connection.connect();

        // convert
        DocumentConverter converter = new OpenOfficeDocumentConverter(
                connection);
        converter.convert(file, outputFile);
        while (true) {
            if (outputFile.length() > 100) {
                //System.out.println("okokokokokokokokookokokokokokokokookokokokokokokokookokokokokokokokookokokokokokokokookokokokokokokoko");
                break;
            }
        }
        // close the connection
        connection.disconnect();
        LawPenaltyEN lawPenaltyEN = PenaltyByID(Integer.valueOf(fileName));
        if (lawPenaltyEN != null) {
            LawSignedpdfEN lawSignedpdfEN = new LawSignedpdfEN();
            lawSignedpdfEN.setUnsigneddir("/Doc/Pdf/" + fileName + ".pdf");
            lawSignedpdfEN.setDir("");
            saveEN(lawSignedpdfEN);

            Set<LawSignedpdfEN> signedpdfENs = new HashSet<>();
            signedpdfENs.add(lawSignedpdfEN);
            lawPenaltyEN.setSignedpdfENSet(signedpdfENs);
            updateEN(lawPenaltyEN);
        }
    }


    //电子签章
    public void doSign(HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        LawPenaltyEN lawPenaltyEN = PenaltyByID(Integer.valueOf(name));

        KGPdfHummer hummer = null;
        FileInputStream cert = null;
        FileOutputStream fileOutputStream = null;
        try {
            cert = new FileInputStream(request.getServletContext().getRealPath("/Signer/iSignature.pfx"));
            File signed = new File(request.getServletContext().getRealPath("/Doc/SPdf/s" + name + ".pdf"));
            signed.getParentFile().mkdirs();

            fileOutputStream = new FileOutputStream(signed.getAbsolutePath());


            hummer = KGPdfHummer.createSignature(request.getServletContext().getRealPath("/Doc/Pdf") + "/" + name + ".pdf", null,
                    true, fileOutputStream, null, true);

            hummer.setCertificate(cert, "123456", "123456");

            //PdfSignature4KG pdfSignature4KG = new PdfSignature4KG("d:/tmp/iSignature.key"
            //      ,0,"123456");
            PdfSignature4KG pdfSignature4KG = new PdfSignature4KG("http://localhost:8089/iSignatureServer/OfficeServer.jsp", KGServerTypeEnum.AUTO, "001"
                    , "123456", "测试专用章");

            pdfSignature4KG.setXY(430, 200);

            //pdfSignature4KG.setText("盖 章");
            hummer.setPdfSignature(pdfSignature4KG);
            hummer.doSignature();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cert.close();
            fileOutputStream.close();
            if (hummer != null) hummer.close();
        }
        if (lawPenaltyEN != null) {
            Iterator<LawSignedpdfEN> iterator = lawPenaltyEN.getSignedpdfENSet().iterator();


            LawSignedpdfEN lawSignedpdfEN = iterator.next();
            lawSignedpdfEN.setDir("Doc/SPdf/s" + name + ".pdf");
            updateEN(lawSignedpdfEN);

            updateEN(lawPenaltyEN);
        }
    }

    //图片签章
    public BaseResult PicSign(HttpServletRequest request) throws IOException {
        String s1 = request.getParameter("id");
        LawEvidencesEN lawEvidencesEN = EvidenceByIDD(Integer.valueOf(s1));
        if (lawEvidencesEN.getSignedpdf() != null && !"".equals(lawEvidencesEN.getSignedpdf()))
            return BaseResult.newResultFailed();
        String imgpath = request.getServletContext().getRealPath(lawEvidencesEN.getDir());
        File imgfile = new File(imgpath);
        String id = request.getParameter("lawid");

        String pdfrelepath = "/Evidence/PDF/" + id;
        String pdfpath = request.getServletContext().getRealPath(pdfrelepath + "/" + imgfile.getName().substring(0, 2) + ".pdf");

        ImageToPDF.ToPDF(imgpath, pdfpath);
        File signed = new File(pdfpath);
        while (true) {
            if (signed.length() > 0) {
                //System.out.println("okokokokokokokokookokokokokokokokookokokokokokokokookokokokokokokokookokokokokokokokookokokokokokokoko");
                break;
            }
        }
        KGPdfHummer hummer = null;
        FileInputStream cert = null;
        FileOutputStream fileOutputStream = null;
        try {
            cert = new FileInputStream(request.getServletContext().getRealPath("/Signer/iSignature.pfx"));

            //signed.getParentFile().mkdirs();

            fileOutputStream = new FileOutputStream(signed.getParent() + "/s" + imgfile.getName().substring(0, 2) + ".pdf");


            hummer = KGPdfHummer.createSignature(pdfpath, null, true, fileOutputStream, null, true);

            hummer.setCertificate(cert, "123456", "123456");

            //PdfSignature4KG pdfSignature4KG = new PdfSignature4KG("d:/tmp/iSignature.key"
            //      ,0,"123456");
            PdfSignature4KG pdfSignature4KG = new PdfSignature4KG("http://localhost:8089/iSignatureServer/OfficeServer.jsp", KGServerTypeEnum.AUTO, "001", "123456", "测试专用章");

            pdfSignature4KG.setXY(200, 200);

            //pdfSignature4KG.setText("盖 章");
            hummer.setPdfSignature(pdfSignature4KG);
            hummer.doSignature();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cert.close();
            fileOutputStream.close();
            if (hummer != null) hummer.close();
        }

        lawEvidencesEN.setSignedpdf(pdfrelepath + "/s" + imgfile.getName().substring(0, 2) + ".pdf");
        updateEN(lawEvidencesEN);

        return BaseResult.newResultOK();
    }

    //按执法人获取简易执法列表
    public List<?> SimpleListByName(String name) {
        Session session = sessionFactory.openSession();
        List<?> list = session.createCriteria(LawPenaltyEN.class)

                .createCriteria("lawEvidencesEN")
                .add(Restrictions.or(Restrictions.like("firstman", "%" + name + "%"), Restrictions.like("secman", "%" + name + "%")))
                .addOrder(Order.desc("sumbdate"))
                .list();
        session.close();
        return list;
    }


    //poi office转htm
    private void convert2Html(String fileName, HttpServletRequest request) {
        XWPFDocument wordDocument = null;
        try {
            wordDocument = new XWPFDocument(new FileInputStream(fileName));
            XHTMLOptions xhtmlOptions;
            File outFile = new File(request.getServletContext().getRealPath("/Doc/DocxStructures.htm"));
            outFile.getParentFile().mkdirs();
            OutputStream out = new FileOutputStream(outFile);
            XHTMLConverter xhtmlConverter = new XHTMLConverter();
            SimpleContentHandler simpleContentHandler = new SimpleContentHandler(out);
            xhtmlConverter.convert(wordDocument, simpleContentHandler, null);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //按law ID获取取证
    public BaseResult showEvidence(int id) {
        Session session = sessionFactory.openSession();
        BaseResult result = new BaseResult();
        List<LawEvidencesEN> evidences = new ArrayList<>();
        LawBaseEN obj = new LawBaseEN();
        obj = (LawBaseEN) session.createCriteria(LawBaseEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        if (obj != null) {
            int lawid = obj.getId();
            evidences = session.createCriteria(LawEvidencesEN.class).createCriteria("lawBaseEN")
                    .add(Restrictions.eq("id", lawid))
                    .list();
        }
        session.close();
        result.setObj(obj);
        result.addToMap("evidences", evidences);
        return result;
    }

    //按证据ID获取取证
    public LawEvidencesEN EvidenceByIDD(int id) {
        Session session = sessionFactory.openSession();
        LawEvidencesEN lawEvidencesEN = (LawEvidencesEN) session.createCriteria(LawEvidencesEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

        session.close();
        return lawEvidencesEN;
    }

    public LawEvidencesEN showEvidenceById(int id) {
        Session session = sessionFactory.openSession();
        LawEvidencesEN obj = new LawEvidencesEN();
        obj = (LawEvidencesEN) session.createCriteria(LawEvidencesEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return obj;
    }

    public BaseResult qztj(int typeen, String starttime, String endtime) {
        BaseResult result = new BaseResult();
        List<ChartBean> cb = new ArrayList<>();
        Date sd = new Date();
        Date ed = new Date();
        Calendar cal = Calendar.getInstance();
        if ("".equals(starttime) && "".equals(endtime)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, -7);
            sd = cal.getTime();
        } else {
            if ("".equals(starttime)) {
                ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                cal.setTime(ed);
                cal.add(Calendar.DAY_OF_YEAR, -7);
                sd = cal.getTime();
            } else {
                if ("".equals(endtime)) {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    cal.setTime(sd);
                    cal.add(Calendar.DAY_OF_YEAR, 7);
                    ed = cal.getTime();
                } else {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                }
            }
        }
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LawBaseEN.class);
        if (typeen > 0) {
            criteria.createCriteria("typeEN").add(Restrictions.eq("id", typeen));
        }
        criteria.add(Restrictions.eq("status", "已审核"));
        criteria.add(Restrictions.eq("issimple", "0"));
        criteria.add(Restrictions.between("sumbdate", sd, ed));
        List<LawBaseEN> list = criteria.list();
        int appok = 0;
        int appnook = 0;
        int piearr[] = {0, 0, 0, 0, 0, 0};
        Map<String, List<LawBaseEN>> map = new HashMap<>();
        for (LawBaseEN en : list) {
            String s = en.getIsllegal();
            int tid = en.getTypeEN().getId();
            if ("构成违章".equals(s)) {
                appok++;
                switch (tid) {
                    case 1:
                        piearr[0] = piearr[0] + 1;
                        break;
                    case 2:
                        piearr[1] = piearr[1] + 1;
                        break;
                    case 3:
                        piearr[2] = piearr[2] + 1;
                        break;
                }
            } else {
                appnook++;
                switch (tid) {
                    case 1:
                        piearr[3] = piearr[3] + 1;
                        break;
                    case 2:
                        piearr[4] = piearr[4] + 1;
                        break;
                    case 3:
                        piearr[5] = piearr[5] + 1;
                        break;
                }
            }
            Date temp = en.getSumbdate();
            String sday = Time.getTimeFmt(temp, "yyyy-MM-dd");
            if (map.containsKey(sday)) {
                map.get(sday).add(en);
            } else {
                List<LawBaseEN> enlist = new ArrayList<>();
                enlist.add(en);
                map.put(sday, enlist);
            }
        }
        Iterator i = map.keySet().iterator();
        while (i.hasNext()) {
            String k = (String) i.next();
            List<LawBaseEN> v = map.get(k);
            int okn = 0;
            int nookn = 0;
            int t1 = 0;
            int t2 = 0;
            int t3 = 0;
            for (LawBaseEN en : v) {
                String temps = en.getIsllegal();
                if ("构成违章".equals(temps)) {
                    okn++;
                } else {
                    nookn++;
                }
                int tid = en.getTypeEN().getId();
                switch (tid) {
                    case 1:
                        t1++;
                        break;
                    case 2:
                        t2++;
                        break;
                    case 3:
                        t3++;
                        break;
                }
            }
            ChartBean b = new ChartBean();
            b.setName(k);
            b.setN1(okn);
            b.setN2(nookn);
            b.setSum(okn + nookn);
            b.setT1(t1);
            b.setT2(t2);
            b.setT3(t3);
            cb.add(b);
        }
        Collections.sort(cb, new Comparator<ChartBean>() {
            @Override
            public int compare(ChartBean o1, ChartBean o2) {
                return -o1.getName().compareTo(o2.getName());
            }
        });
        StringBuffer sb = new StringBuffer("");
        sb.append("select a.id,a.firstman,a.result,a.sumbdate,a.typeEN,c.ZZJGMC from law_base a,jcxx_yh b,jcxx_zzjg c where a.firstman=b.xm and b.dw=c.id and a.issimple='0' and a.`status`='已审核'and a.sumbdate>='" + Time.getTimeFmt(sd) + "' and a.sumbdate<='" + Time.getTimeFmt(ed) + "'");
        if (typeen > 0) {
            sb.append(" a.typeEN=" + typeen);
        }
        SQLQuery q = session.createSQLQuery(sb.toString());
        q.addScalar("id", StandardBasicTypes.INTEGER);
        q.addScalar("firstman", StandardBasicTypes.STRING);
        q.addScalar("result", StandardBasicTypes.STRING);
        q.addScalar("sumbdate", StandardBasicTypes.STRING);
        q.addScalar("typeen", StandardBasicTypes.INTEGER);
        q.addScalar("zzjgmc", StandardBasicTypes.STRING);
        q.setResultTransformer(Transformers.aliasToBean(QztjBean.class));
        List<QztjBean> datalist = q.list();
        Map<String, List<QztjBean>> zzjgmap = new HashMap<>();
        Map<String, List<QztjBean>> timemap = new HashMap<>();
        Map<String, Map<String, List<QztjBean>>> retmap = new HashMap<>();
        for (QztjBean en : datalist) {
            String temp = en.getZzjgmc();
            if (zzjgmap.containsKey(temp)) {
                zzjgmap.get(temp).add(en);
            } else {
                List<QztjBean> enlist = new ArrayList<>();
                enlist.add(en);
                zzjgmap.put(temp, enlist);
            }
        }
        Iterator j = zzjgmap.keySet().iterator();
        while (j.hasNext()) {
            String k = (String) j.next();
            List<QztjBean> v = zzjgmap.get(k);
            for (QztjBean en : v) {
                String temp = en.getSumbdate();
                String sday = temp.substring(0, 10);
                if (timemap.containsKey(sday)) {
                    timemap.get(sday).add(en);
                } else {
                    List<QztjBean> enlist = new ArrayList<>();
                    enlist.add(en);
                    timemap.put(sday, enlist);
                }
            }
            retmap.put(k, timemap);
        }
        result.addToMap("retmap", retmap);
        result.addToMap("appok", appok);
        result.addToMap("appnook", appnook);
        result.addToMap("piearr", piearr);
        result.addToMap("colqz", cb);
        session.flush();
        session.close();
        return result;
    }

    public BaseResult evidDt(String shipname, int typeen, String starttime, String endtime, int page) {
        Date sd = new Date();
        Date ed = new Date();
        Calendar cal = Calendar.getInstance();
        if ("".equals(starttime) && "".equals(endtime)) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_YEAR, -7);
            sd = cal.getTime();
        } else {
            if ("".equals(starttime)) {
                ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                cal.setTime(ed);
                cal.add(Calendar.DAY_OF_YEAR, -7);
                sd = cal.getTime();
            } else {
                if ("".equals(endtime)) {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    cal.setTime(sd);
                    cal.add(Calendar.DAY_OF_YEAR, 7);
                    ed = cal.getTime();
                } else {
                    sd = Time.getDateByStringFmt(starttime + " 00:00:00");
                    ed = Time.getDateByStringFmt(endtime + " 23:59:59");
                }
            }
        }
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LawBaseEN.class, "b");
        DetachedCriteria dc = DetachedCriteria.forClass(LawEvidencesEN.class, "e");
        dc.add(Restrictions.isNotNull("e.signedpdf"));
        dc.setProjection(Projections.projectionList().add(Projections.groupProperty("e.lawBaseEN")));
        if (typeen > 0) {
            criteria.createCriteria("typeEN").add(Restrictions.eq("id", typeen));
        }
        if (shipname != null && !"".equals(shipname)) {
            criteria.add(Restrictions.like("target", "%" + shipname + "%"));
        }
        //criteria.add(Restrictions.eq("status", "已审核"));
        criteria.add(Restrictions.eq("issimple", "0"));
        criteria.add(Restrictions.between("sumbdate", sd, ed));
        criteria.add(Subqueries.propertyIn("b.id", dc));
        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<?> datas = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10).list();
        session.close();
        return new BaseResult(pages, (int) count, datas);
    }

    public BaseResult zfcxDt(String shipname, String flag, int page) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LawBaseEN.class).addOrder(Order.desc("sumbdate"));
        if (shipname != null && !"".equals(shipname)) {
            criteria.add(Restrictions.like("target", "%" + shipname + "%"));
        }
        if (flag != null && !"".equals(flag)) {
            criteria.add(Restrictions.eq("issimple", flag));
        }
        //页码
        long count = (long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        int pages = (int) count / 10;
        pages = count % 10 > 0 ? pages + 1 : pages;
        if (pages == 0)
            pages = 1;
        List<?> datas = criteria.setProjection(null).setFirstResult((page - 1) * 10)
                .setMaxResults(10).list();
        session.close();
        return new BaseResult(pages, (int) count, datas);
    }

    public BaseResult showZfcx(int id) {
        Session session = sessionFactory.openSession();
        BaseResult result = new BaseResult();
        List<LawEvidencesEN> evidences = new ArrayList<>();
        LawBaseEN b = new LawBaseEN();
        LawPenaltyEN p = new LawPenaltyEN();
        b = (LawBaseEN) session.createCriteria(LawBaseEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        if (b != null) {
            int lawid = b.getId();
            evidences = session.createCriteria(LawEvidencesEN.class).createCriteria("lawBaseEN")
                    .add(Restrictions.eq("id", lawid))
                    .list();
            p = (LawPenaltyEN) session.createCriteria(LawPenaltyEN.class)
                    .add(Restrictions.eq("lawEvidencesEN.id", id))
                    .uniqueResult();
        }
        session.close();
        result.addToMap("baseen", b);
        result.addToMap("penaltyen", p);
        result.addToMap("evidences", evidences);
        return result;
    }

    //按ID获取penalty
    public LawPenaltyEN PenaltyByID(int id) {
        Session session = sessionFactory.openSession();
        LawPenaltyEN penaltyEN = (LawPenaltyEN) session.createCriteria(LawPenaltyEN.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
        session.close();
        return penaltyEN;
    }

    //统计待审批数量
    public int LawCheckCount(String type) {
        Session session = sessionFactory.openSession();
        long count = (long) session.createCriteria(LawBaseEN.class).add(Restrictions.eq("status", "待审核")).add(Restrictions.eq("issimple", type))
                .setProjection(Projections.rowCount()).uniqueResult();

        session.close();
        return (int) count;
    }
}
