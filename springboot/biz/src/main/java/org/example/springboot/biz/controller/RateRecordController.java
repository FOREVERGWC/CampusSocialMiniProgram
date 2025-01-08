package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.RateRecordDto;
import org.example.springboot.biz.domain.entity.RateRecord;
import org.example.springboot.biz.domain.vo.RateRecordVo;
import org.example.springboot.biz.service.IRateRecordService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评分记录前端控制器
 * </p>
 */
@RestController
@RequestMapping("/rateRecord")
@Tag(name = "评分记录", description = "评分记录")
public class RateRecordController {
    @Resource
    private IRateRecordService rateRecordService;

    /**
     * 添加、修改评分记录
     *
     * @param rateRecord 评分记录
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改评分记录", description = "添加、修改评分记录", method = "POST")
    public Result<Void> save(@RequestBody RateRecord rateRecord) {
        rateRecordService.saveOrUpdate(rateRecord);
        return Result.success();
    }

    /**
     * 删除评分记录
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除评分记录", description = "删除评分记录", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        rateRecordService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询评分记录列表
     *
     * @param dto 评分记录
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询评分记录列表", description = "查询评分记录列表", method = "GET")
    public Result<List<RateRecordVo>> getList(RateRecordDto dto) {
        List<RateRecordVo> list = rateRecordService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询评分记录分页
     *
     * @param dto 评分记录
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询评分记录分页", description = "查询评分记录分页", method = "GET")
    public Result<IPage<RateRecordVo>> getPage(RateRecordDto dto) {
        IPage<RateRecordVo> page = rateRecordService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询评分记录
     *
     * @param dto 评分记录
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询评分记录", description = "查询评分记录", method = "GET")
    public Result<RateRecordVo> getOne(RateRecordDto dto) {
        RateRecordVo vo = rateRecordService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询评分记录
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询评分记录", description = "查询评分记录", method = "GET")
    public Result<RateRecordVo> getById(@PathVariable Long id) {
        RateRecordVo vo = rateRecordService.getOne(RateRecordDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出评分记录
     *
     * @param entity   评分记录
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出评分记录", description = "导出评分记录", method = "GET")
    public void exportExcel(RateRecord entity, HttpServletResponse response) {
        rateRecordService.exportExcel(entity, response);
    }
}
