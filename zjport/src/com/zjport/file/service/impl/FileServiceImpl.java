package com.zjport.file.service.impl;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.service.BaseService;
import com.common.utils.FileUtils;
import com.zjport.file.dao.FileDao;
import com.zjport.file.service.FileService;
import com.zjport.model.TAttachment;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWQ on 2016/8/22.
 */
@Service("fileService")
public class FileServiceImpl extends BaseService implements FileService{

    @Resource(name = "fileDao")
    private FileDao fileDao;

    @Override
    public void saveFile(MultipartFile file, String filePath, String saveMiddleAttachmentId) throws IOException {

        TAttachment attachment = new TAttachment();

        attachment.setStAttachmentMiddleId(saveMiddleAttachmentId);
        attachment.setStFileSize((int)file.getSize());
        attachment.setStFileName(file.getOriginalFilename());
        //attachment.setStFileName(file.getName());
        System.out.println(FileUtils.getFileExtension(file.getOriginalFilename()));
        attachment.setStFileType(FileUtils.getFileExtension(file.getOriginalFilename()));
        attachment.setStFileSource(filePath);
        this.fileDao.saveAttachment(attachment);

        file.transferTo(new File(filePath));
    }

    @Override
    public TAttachment getAttachmentById(int id) {
        return this.fileDao.selectAttachment(id);
    }

    @Override
    public List<TAttachment> getAttachmentList(String middleId) {
        List<TAttachment> attachmentList = new ArrayList<TAttachment>();
        if("".equals(middleId) || middleId == null) {
            return attachmentList;
        } else {
            BaseRecords records = this.fileDao.selectAttachmentByMiddleId(middleId);
            int size = records.getData().size();

            for(int i = 0; i<size; i++) {
                attachmentList.add((TAttachment)records.getData().get(i));
            }
            return attachmentList;
        }
    }

    @Override
    public void delete(String middleAttachmentId) {
        if(!StringUtils.isEmpty(middleAttachmentId)) {
            List<TAttachment> attachmentList = this.getAttachmentList(middleAttachmentId);
            for(TAttachment attachment:attachmentList) {
                this.fileDao.delete(attachment);
            }
        }
    }

    @Override
    public BaseResult importFile(MultipartFile file) {
        String telPhone = "";
        try
        {
            InputStream is = file.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);


            // 获取第一个sheet的内容
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            if (hssfSheet == null)
            {
                hssfWorkbook.close();
                return new BaseResult(-1, "导入文件的内容错误");
            }

            // 获取总行数
            int rowNums = hssfSheet.getLastRowNum();

            for(int i = 1; i<rowNums; i++) {
                telPhone += hssfSheet.getRow(i).getCell(0)+";";
                System.out.println(hssfSheet.getRow(i).getCell(0));
            }

            System.out.println("*****************");

            // 保存或者更新营运船舶记录s

            hssfWorkbook.close();
            is.close();
        }
        catch (Exception e)
        {
            return new BaseResult(-1, "导入失败");
        }
        return new BaseResult(0, "导入成功", telPhone);
    }
}
