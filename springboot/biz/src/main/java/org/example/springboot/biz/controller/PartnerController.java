package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.PartnerDto;
import org.example.springboot.biz.domain.entity.Partner;
import org.example.springboot.biz.domain.vo.PartnerVo;
import org.example.springboot.biz.service.IPartnerService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 组局前端控制器
 * </p>
 */
@RestController
@RequestMapping("/partner")
@Tag(name = "组局", description = "组局")
public class PartnerController {
    @Resource
    private IPartnerService partnerService;

    /**
     * 添加、修改组局
     *
     * @param partner 组局
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改组局", description = "添加、修改组局", method = "POST")
    public Result<PartnerVo> save(@RequestBody Partner partner) {
        partnerService.saveOrUpdate(partner);
        PartnerVo vo = partnerService.getOne(PartnerDto.builder().id(partner.getId()).build());
        return Result.success(vo);
    }

    /**
     * 删除组局
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除组局", description = "删除组局", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        partnerService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询组局列表
     *
     * @param dto 组局
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询组局列表", description = "查询组局列表", method = "GET")
    public Result<List<PartnerVo>> getList(PartnerDto dto) {
        List<PartnerVo> list = partnerService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询组局分页
     *
     * @param dto 组局
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询组局分页", description = "查询组局分页", method = "GET")
    public Result<IPage<PartnerVo>> getPage(PartnerDto dto) {
        IPage<PartnerVo> page = partnerService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询组局
     *
     * @param dto 组局
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询组局", description = "查询组局", method = "GET")
    public Result<PartnerVo> getOne(PartnerDto dto) {
        PartnerVo vo = partnerService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询组局
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询组局", description = "查询组局", method = "GET")
    public Result<PartnerVo> getById(@PathVariable Long id) {
        PartnerVo vo = partnerService.getOne(PartnerDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出组局
     *
     * @param entity   组局
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出组局", description = "导出组局", method = "GET")
    public void exportExcel(Partner entity, HttpServletResponse response) {
        partnerService.exportExcel(entity, response);
    }
}
