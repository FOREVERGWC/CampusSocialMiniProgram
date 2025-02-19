package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.PartnerSubjectDto;
import org.example.springboot.biz.domain.entity.PartnerSubject;
import org.example.springboot.biz.domain.vo.PartnerSubjectVo;
import org.example.springboot.biz.service.IPartnerSubjectService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 组局主题前端控制器
 * </p>
 */
@RestController
@RequestMapping("/partner/subject")
@Tag(name = "组局主题", description = "组局主题")
public class PartnerSubjectController {
    @Resource
    private IPartnerSubjectService partnerSubjectService;

    /**
     * 添加、修改组局主题
     *
     * @param partnerSubject 组局主题
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改组局主题", description = "添加、修改组局主题", method = "POST")
    public Result<Void> save(@RequestBody PartnerSubject partnerSubject) {
        partnerSubjectService.saveOrUpdate(partnerSubject);
        return Result.success();
    }

    /**
     * 删除组局主题
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除组局主题", description = "删除组局主题", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        partnerSubjectService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询组局主题列表
     *
     * @param dto 组局主题
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询组局主题列表", description = "查询组局主题列表", method = "GET")
    public Result<List<PartnerSubjectVo>> getList(PartnerSubjectDto dto) {
        List<PartnerSubjectVo> list = partnerSubjectService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询组局主题分页
     *
     * @param dto 组局主题
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询组局主题分页", description = "查询组局主题分页", method = "GET")
    public Result<IPage<PartnerSubjectVo>> getPage(PartnerSubjectDto dto) {
        IPage<PartnerSubjectVo> page = partnerSubjectService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询组局主题
     *
     * @param dto 组局主题
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询组局主题", description = "查询组局主题", method = "GET")
    public Result<PartnerSubjectVo> getOne(PartnerSubjectDto dto) {
        PartnerSubjectVo vo = partnerSubjectService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询组局主题
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询组局主题", description = "查询组局主题", method = "GET")
    public Result<PartnerSubjectVo> getById(@PathVariable Long id) {
        PartnerSubjectVo vo = partnerSubjectService.getOne(PartnerSubjectDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出组局主题
     *
     * @param entity   组局主题
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出组局主题", description = "导出组局主题", method = "GET")
    public void exportExcel(PartnerSubject entity, HttpServletResponse response) {
        partnerSubjectService.exportExcel(entity, response);
    }
}
