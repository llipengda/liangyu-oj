package com.zybzyb.liangyuoj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Schema(description = "PDF")
public class Pdf {

    /**
     * PDF ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**]
     * 章
     */
    private Integer chapter;

    /**
     * 章名
     */
    private String chapterName;

    /**
     * 节
     */
    private Integer section;

    /**
     * 节名
     */
    private String sectionName;

    /**
     * PDF地址
     */
    private String address;

}
