package com.zjport.file.service;

import com.common.base.BaseResult;
import com.zjport.model.TAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by TWQ on 2016/8/22.
 */
public interface FileService {

    public void saveFile(MultipartFile file, String filePath, String saveMiddleAttachmentId) throws IOException;

    public TAttachment getAttachmentById(int id);

    public List<TAttachment> getAttachmentList(String middleId);

    public void delete(String middleAttachmentId);

    public BaseResult importFile(MultipartFile file);
}