package com.zybzyb.liangyuoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zybzyb.liangyuoj.entity.Pdf;
import com.zybzyb.liangyuoj.mapper.PdfMapper;
import com.zybzyb.liangyuoj.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private PdfMapper pdfMapper;

    @Override
    public List<Pdf> getPdfByChapter(Integer chapter) {
        QueryWrapper<Pdf> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter", chapter).orderBy(true, true, "chapter", "section");
        return pdfMapper.selectList(queryWrapper);
    }

}












