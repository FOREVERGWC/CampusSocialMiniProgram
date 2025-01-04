package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.CountViewDto;
import org.example.springboot.system.domain.entity.CountView;
import org.example.springboot.system.domain.vo.CountViewVo;
import org.example.springboot.system.service.ICountViewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 浏览量前端控制器
 * </p>
 */
@RestController
@RequestMapping("/countView")
@Tag(name = "浏览量", description = "浏览量")
public class CountViewController {
    @Resource
    private ICountViewService countViewService;

    /**
     * 添加、修改浏览量
     *
     * @param countView 浏览量
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改浏览量", description = "添加、修改浏览量", method = "POST")
    public Result<Void> save(@RequestBody CountView countView) {
        countViewService.saveOrUpdate(countView);
        return Result.success();
    }

    /**
     * 删除浏览量
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除浏览量", description = "删除浏览量", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        countViewService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询浏览量列表
     *
     * @param dto 浏览量
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询浏览量列表", description = "查询浏览量列表", method = "GET")
    public Result<List<CountViewVo>> getList(CountViewDto dto) {
        List<CountViewVo> list = countViewService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询浏览量分页
     *
     * @param dto 浏览量
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询浏览量分页", description = "查询浏览量分页", method = "GET")
    public Result<IPage<CountViewVo>> getPage(CountViewDto dto) {
        IPage<CountViewVo> page = countViewService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询浏览量
     *
     * @param dto 浏览量
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询浏览量", description = "查询浏览量", method = "GET")
    public Result<CountViewVo> getOne(CountViewDto dto) {
        CountViewVo vo = countViewService.getOne(dto);
        return Result.success(vo);
    }
}
