package com.zjport.file.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.zjport.model.TAttachment;
import org.springframework.stereotype.Repository;

/**
 * Created by TWQ on 2016/8/22.
 */
@Repository("fileDao")
public class FileDao extends BaseDaoDB {

    public void saveAttachment(TAttachment attachment) {
        super.save(attachment);
    }

    public TAttachment selectAttachment(int id) {
        return (TAttachment)super.findUnique(new ObjectQuery(TAttachment.class,"stAttachmentId",id));
    }

    public BaseRecords selectAttachmentByMiddleId(String middleId) {
        return super.find(new ObjectQuery(TAttachment.class,"stAttachmentMiddleId",middleId));
    }
}
