package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.DictTypeDto;
import org.example.springboot.system.domain.entity.DictType;
import org.example.springboot.system.domain.vo.DictTypeVo;
import org.example.springboot.system.service.IDictTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典类型前端控制器
 * </p>
 */
@RestController
@RequestMapping("/dict/type")
@Tag(name = "字典类型", description = "字典类型")
public class DictTypeController {
    @Resource
    private IDictTypeService dictTypeService;

    /**
     * 添加、修改字典类型
     *
     * @param dictType 字典类型
     * @return 结果
     */
    @PreAuthorize("hasAnyAuthority('system:dict:type:add', 'system:dict:type:edit')")
    @PostMapping
    @Operation(summary = "添加、修改字典类型", description = "添加、修改字典类型", method = "POST")
    public Result<Void> save(@RequestBody DictType dictType) {
        dictTypeService.saveOrUpdate(dictType);
        return Result.success();
    }

    /**
     * 删除字典类型
     *
     * @param ids ID列表
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:type:delete')")
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除字典类型", description = "删除字典类型", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        dictTypeService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询字典类型列表
     *
     * @param dto 字典类型
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:type:list')")
    @GetMapping("/list")
    @Operation(summary = "查询字典类型列表", description = "查询字典类型列表", method = "GET")
    public Result<List<DictTypeVo>> getList(DictTypeDto dto) {
        List<DictTypeVo> list = dictTypeService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询字典类型分页
     *
     * @param dto 字典类型
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:type:list')")
    @GetMapping("/page")
    @Operation(summary = "查询字典类型分页", description = "查询字典类型分页", method = "GET")
    public Result<IPage<DictTypeVo>> getPage(DictTypeDto dto) {
        IPage<DictTypeVo> page = dictTypeService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询字典类型
     *
     * @param id 主键ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:type:query')")
    @GetMapping("/{id}")
    @Operation(summary = "查询字典类型", description = "查询字典类型", method = "GET")
    public Result<DictType> getById(@PathVariable Long id) {
        DictType vo = dictTypeService.getById(id);
        return Result.success(vo);
    }

    /**
     * 查询字典类型
     *
     * @param dto 字典类型
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:type:query')")
    @GetMapping
    @Operation(summary = "查询字典类型", description = "查询字典类型", method = "GET")
    public Result<DictTypeVo> getOne(DictTypeDto dto) {
        DictTypeVo vo = dictTypeService.getOne(dto);
        return Result.success(vo);
    }

    /**
     * 导出字典类型
     *
     * @param dictType 字典类型
     * @param response 响应对象
     */
    @PreAuthorize("hasAnyAuthority('system:dict:type:export')")
    @GetMapping("/export")
    @Operation(summary = "导出字典类型", description = "导出字典类型", method = "GET")
    public void exportExcel(DictType dictType, HttpServletResponse response) {
        dictTypeService.exportExcel(dictType, response);
    }

    /**
     * 恢复或停用字典类型
     *
     * @param id 字典类型ID
     * @return 结果
     */
    @PreAuthorize("hasAuthority('system:dict:type:edit')")
    @PutMapping("/status/{id}")
    @Operation(summary = "恢复或停用字典类型", description = "恢复或停用字典类型", method = "PUT")
    public Result<Void> handleStatus(@PathVariable Long id) {
        dictTypeService.handleStatus(id);
        return Result.success();
    }
}
