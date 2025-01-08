package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.RateDto;
import org.example.springboot.biz.domain.entity.Rate;
import org.example.springboot.biz.domain.vo.RateVo;
import org.example.springboot.biz.service.IRateService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评分前端控制器
 * </p>
 */
@RestController
@RequestMapping("/rate")
@Tag(name = "评分", description = "评分")
public class RateController {
    @Resource
    private IRateService rateService;

    /**
     * 添加、修改评分
     *
     * @param rate 评分
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改评分", description = "添加、修改评分", method = "POST")
    public Result<Void> save(@RequestBody Rate rate) {
        rateService.saveOrUpdate(rate);
        return Result.success();
    }

    /**
     * 删除评分
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除评分", description = "删除评分", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        rateService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询评分列表
     *
     * @param dto 评分
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询评分列表", description = "查询评分列表", method = "GET")
    public Result<List<RateVo>> getList(RateDto dto) {
        List<RateVo> list = rateService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询评分分页
     *
     * @param dto 评分
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询评分分页", description = "查询评分分页", method = "GET")
    public Result<IPage<RateVo>> getPage(RateDto dto) {
        IPage<RateVo> page = rateService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询评分
     *
     * @param dto 评分
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询评分", description = "查询评分", method = "GET")
    public Result<RateVo> getOne(RateDto dto) {
        RateVo vo = rateService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询评分
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询评分", description = "查询评分", method = "GET")
    public Result<RateVo> getById(@PathVariable Long id) {
        RateVo vo = rateService.getOne(RateDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出评分
     *
     * @param entity   评分
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出评分", description = "导出评分", method = "GET")
    public void exportExcel(Rate entity, HttpServletResponse response) {
        rateService.exportExcel(entity, response);
    }
}
