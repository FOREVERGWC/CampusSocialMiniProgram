package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.RateItemDto;
import org.example.springboot.biz.domain.entity.RateItem;
import org.example.springboot.biz.domain.vo.RateItemVo;
import org.example.springboot.biz.service.IRateItemService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评分项前端控制器
 * </p>
 */
@RestController
@RequestMapping("/rate/item")
@Tag(name = "评分项", description = "评分项")
public class RateItemController {
    @Resource
    private IRateItemService rateItemService;

    /**
     * 添加、修改评分项
     *
     * @param rateItem 评分项
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改评分项", description = "添加、修改评分项", method = "POST")
    public Result<RateItemVo> save(@RequestBody RateItem rateItem) {
        rateItemService.saveOrUpdate(rateItem);
        RateItemVo vo = rateItemService.getOne(RateItemDto.builder().id(rateItem.getId()).build());
        return Result.success(vo);
    }

    /**
     * 删除评分项
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除评分项", description = "删除评分项", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        rateItemService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询评分项列表
     *
     * @param dto 评分项
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询评分项列表", description = "查询评分项列表", method = "GET")
    public Result<List<RateItemVo>> getList(RateItemDto dto) {
        List<RateItemVo> list = rateItemService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询评分项分页
     *
     * @param dto 评分项
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询评分项分页", description = "查询评分项分页", method = "GET")
    public Result<IPage<RateItemVo>> getPage(RateItemDto dto) {
        IPage<RateItemVo> page = rateItemService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询评分项
     *
     * @param dto 评分项
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询评分项", description = "查询评分项", method = "GET")
    public Result<RateItemVo> getOne(RateItemDto dto) {
        RateItemVo vo = rateItemService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 查询评分项
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询评分项", description = "查询评分项", method = "GET")
    public Result<RateItemVo> getById(@PathVariable Long id) {
        RateItemVo vo = rateItemService.getOne(RateItemDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出评分项
     *
     * @param entity   评分项
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出评分项", description = "导出评分项", method = "GET")
    public void exportExcel(RateItem entity, HttpServletResponse response) {
        rateItemService.exportExcel(entity, response);
    }
}
