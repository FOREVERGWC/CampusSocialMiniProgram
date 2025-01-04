package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.CountRateDto;
import org.example.springboot.system.domain.entity.CountRate;
import org.example.springboot.system.domain.vo.CountRateVo;
import org.example.springboot.system.service.ICountRateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 评分量前端控制器
 * </p>
 */
@RestController
@RequestMapping("/countRate")
@Tag(name = "评分量", description = "评分量")
public class CountRateController {
    @Resource
    private ICountRateService countRateService;

    /**
     * 添加、修改评分量
     *
     * @param countRate 评分量
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改评分量", description = "添加、修改评分量", method = "POST")
    public Result<Void> save(@RequestBody CountRate countRate) {
        countRateService.saveOrUpdate(countRate);
        return Result.success();
    }

    /**
     * 删除评分量
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除评分量", description = "删除评分量", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        countRateService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询评分量列表
     *
     * @param dto 评分量
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询评分量列表", description = "查询评分量列表", method = "GET")
    public Result<List<CountRateVo>> getList(CountRateDto dto) {
        List<CountRateVo> list = countRateService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询评分量分页
     *
     * @param dto 评分量
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询评分量分页", description = "查询评分量分页", method = "GET")
    public Result<IPage<CountRateVo>> getPage(CountRateDto dto) {
        IPage<CountRateVo> page = countRateService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询评分量
     *
     * @param dto 评分量
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询评分量", description = "查询评分量", method = "GET")
    public Result<CountRateVo> getOne(CountRateDto dto) {
        CountRateVo vo = countRateService.getOne(dto);
        return Result.success(vo);
    }
}
