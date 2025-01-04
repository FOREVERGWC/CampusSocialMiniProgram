package org.example.springboot.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.springboot.common.domain.Result;
import org.example.springboot.system.domain.dto.NoteDto;
import org.example.springboot.system.domain.entity.Note;
import org.example.springboot.system.domain.vo.NoteVo;
import org.example.springboot.system.service.INoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 笔记前端控制器
 * </p>
 */
@RestController
@RequestMapping("/note")
@Tag(name = "笔记", description = "笔记")
public class NoteController {
    @Resource
    private INoteService noteService;

    /**
     * 添加、修改笔记
     *
     * @param note 笔记
     * @return 结果
     */
    @PostMapping
    @Operation(summary = "添加、修改笔记", description = "添加、修改笔记", method = "POST")
    public Result<Void> save(@RequestBody Note note) {
        noteService.saveOrUpdate(note);
        return Result.success();
    }

    /**
     * 删除笔记
     *
     * @param ids ID列表
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    @Operation(summary = "删除笔记", description = "删除笔记", method = "DELETE")
    public Result<Void> removeBatchByIds(@PathVariable List<Long> ids) {
        noteService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询笔记列表
     *
     * @param dto 笔记
     * @return 结果
     */
    @GetMapping("/list")
    @Operation(summary = "查询笔记列表", description = "查询笔记列表", method = "GET")
    public Result<List<NoteVo>> getList(NoteDto dto) {
        List<NoteVo> list = noteService.getList(dto);
        return Result.success(list);
    }

    /**
     * 查询笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    @GetMapping("/page")
    @Operation(summary = "查询笔记分页", description = "查询笔记分页", method = "GET")
    public Result<IPage<NoteVo>> getPage(NoteDto dto) {
        IPage<NoteVo> page = noteService.getPage(dto);
        return Result.success(page);
    }

    /**
     * 查询笔记
     *
     * @param dto 笔记
     * @return 结果
     */
    @GetMapping
    @Operation(summary = "查询笔记", description = "查询笔记", method = "GET")
    public Result<NoteVo> getOne(NoteDto dto) {
        NoteVo vo = noteService.getOne(dto);
        return Result.success(vo);
    }
}
