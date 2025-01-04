package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.RateRecordDto;
import org.example.springboot.system.domain.entity.RateRecord;
import org.example.springboot.system.domain.vo.RateRecordVo;
import org.example.springboot.system.service.IRateRecordService;
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
}
