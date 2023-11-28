package com.zybzyb.liangyuoj.controller;

import com.zybzyb.liangyuoj.annotation.NoAuth;
import com.zybzyb.liangyuoj.common.Result;
import com.zybzyb.liangyuoj.entity.Pdf;
import com.zybzyb.liangyuoj.service.PdfService;
import com.zybzyb.liangyuoj.service.ProblemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pdf")
@Validated
@Tag(name = "pdf接口", description = "pdf相关接口")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @NoAuth
    @GetMapping("/get")
    public Result<List<Pdf>> getPdfByChapter(Integer id) {
        return Result.success(pdfService.getPdfByChapter(id));
    }
}









