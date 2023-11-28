package com.zybzyb.liangyuoj.service;

import com.zybzyb.liangyuoj.entity.Pdf;

import java.util.List;

public interface PdfService {
    List<Pdf> getPdfByChapter(Integer chapter);
}
