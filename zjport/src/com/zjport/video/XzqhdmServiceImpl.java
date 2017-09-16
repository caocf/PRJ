package com.zjport.video;

import java.util.*;

import javax.annotation.Resource;

import com.zjport.video.model.CZdXzqhRela;
import com.zjport.video.model.CZdXzqhdm;
import org.springframework.stereotype.Service;

import com.common.utils.tree.TreeDao;
import com.common.utils.tree.impl.TreeServiceImpl;
import com.common.utils.tree.model.Tree;

@Service("xzqhdmservice")
public class XzqhdmServiceImpl extends TreeServiceImpl<CZdXzqhdm, CZdXzqhRela>
        implements XzqhdmService {

    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;

    @Override
    public TreeDao<CZdXzqhdm, CZdXzqhRela> getTreeDao() {
        return xzqhdmdao;
    }


}
