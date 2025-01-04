package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.common.annotation.Anonymous;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.DictDataDto;
import org.example.springboot.system.domain.entity.DictData;
import org.example.springboot.system.domain.vo.DictDataVo;
import org.example.springboot.system.service.IDictDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典数据前端控制器
 * </p>
 */
@RestController
@RequestMapping("/dict/data")
@Tag(name = "字典数据", description = "字典数据")
public class DictDataController {
    @Resource
    private IDictDataService dictDataService;

    /**
     * 添加、修改字典数据
     *
     * @param dictData 字典数据
     * @return 结果
     */
    @PreAuthorize("hasAnyAuthority('system:dict:data:add', 'system:dict:data:edit')")
    @PostMapping
    @Operation(summary = "添加、修改字典数据", description = "添加、修改字典数据", method = "POST")
    public Result<Void> save(@RequestBody DictData dictData) {
        dictDataService.saveOrUpdate(dictData);
        return Result.success();
    }

    /**
     * 删除字典数据
     *
     * @param ids ID列表
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:data:delete')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除字典数据", description = "删除字典数据", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        dictDataService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询字典数据列表
     *
     * @param dto 字典数据
     * @return 结果
     */
//    @PreAuthorize("hasAuthority('system:dict:data:list')")
    @Anonymous
    @GetMapping("/list")
    @Operation(summary = "查询字典数据列表", description = "查询字典数据列表", method = "GET")
    public Result<List<DictDataVo>> getList(DictDataDto dto) {
        List<DictDataVo> list = dictDataService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询字典数据分页
     *
     * @param dto 字典数据
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:data:list')")
    @GetMapping("/page")
    @Operation(summary = "查询字典数据分页", description = "查询字典数据分页", method = "GET")
    public Result<IPage<DictDataVo>> getPage(DictDataDto dto) {
        IPage<DictDataVo> page = dictDataService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询字典数据
     *
     * @param id 主键ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:data:query')")
    @GetMapping("/{id}")
    @Operation(summary = "查询字典数据", description = "查询字典数据", method = "GET")
    public Result<DictData> getById(@PathVariable Long id) {
        DictData vo = dictDataService.getById(id);
        return Result.success(vo);
    }

    /**
     * 查询字典数据
     *
     * @param dto 字典数据
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:data:query')")
    @GetMapping
    @Operation(summary = "查询字典数据", description = "查询字典数据", method = "GET")
    public Result<DictDataVo> getOne(DictDataDto dto) {
        DictDataVo vo = dictDataService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 导出字典数据
     *
     * @param dictData 字典数据
     * @param response 响应对象
     */
    @PreAuthorize("hasAnyAuthority('system:dict:data:export')")
    @GetMapping("/export")
    @Operation(summary = "导出字典数据", description = "导出字典数据", method = "GET")
    public void exportExcel(DictData dictData, HttpServletResponse response) {
        dictDataService.exportExcel(dictData, response);
    }

    /**
     * 恢复或停用字典数据
     *
     * @param id 字典数据ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:data:edit')")
    @PutMapping("/status/{id}")
    @Operation(summary = "恢复或停用字典数据", description = "恢复或停用字典数据", method = "PUT")
    public Result<Void> handleStatus(@PathVariable Long id) {
        dictDataService.handleStatus(id);
        return Result.success();
    }
}
