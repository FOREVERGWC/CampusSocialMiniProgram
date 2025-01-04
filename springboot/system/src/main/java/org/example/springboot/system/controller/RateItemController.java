package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.RateItemDto;
import org.example.springboot.system.domain.entity.RateItem;
import org.example.springboot.system.domain.vo.RateItemVo;
import org.example.springboot.system.service.IRateItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评分项前端控制器
 * </p>
 */
@RestController
@RequestMapping("/rateItem")
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
    public Result<Void> save(@RequestBody RateItem rateItem) {
        rateItemService.saveOrUpdate(rateItem);
        return Result.success();
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
}
