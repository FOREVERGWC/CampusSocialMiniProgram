package org.example.springboot.biz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.example.springboot.biz.domain.dto.NoteDto;
import org.example.springboot.biz.domain.entity.Note;
import org.example.springboot.biz.domain.vo.NoteVo;
import org.example.springboot.biz.service.INoteService;
import org.example.springboot.common.domain.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public Result<NoteVo> save(@RequestBody Note note) {
        noteService.saveOrUpdate(note);
        NoteVo vo = noteService.getOne(NoteDto.builder().id(note.getId()).build());
        return Result.success(vo);
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
     * 查询我的笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    @GetMapping("/my")
    @Operation(summary = "查询我的笔记分页", description = "查询我的笔记分页", method = "GET")
    public Result<IPage<NoteVo>> getMyPage(NoteDto dto) {
        IPage<NoteVo> page = noteService.getMyPage(dto);
        return Result.success(page);
    }

    /**
     * 查询我的点赞笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    @GetMapping("/my/like")
    @Operation(summary = "查询我的点赞笔记分页", description = "查询我的点赞笔记分页", method = "GET")
    public Result<IPage<NoteVo>> getMyLikePage(NoteDto dto) {
        IPage<NoteVo> page = noteService.getMyLikePage(dto);
        return Result.success(page);
    }

    /**
     * 查询我的收藏笔记分页
     *
     * @param dto 笔记
     * @return 结果
     */
    @GetMapping("/my/favorite")
    @Operation(summary = "查询我的收藏笔记分页", description = "查询我的收藏笔记分页", method = "GET")
    public Result<IPage<NoteVo>> getMyFavoritePage(NoteDto dto) {
        IPage<NoteVo> page = noteService.getMyFavoritePage(dto);
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

    /**
     * 查询笔记
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询笔记", description = "查询笔记", method = "GET")
    public Result<NoteVo> getById(@PathVariable Long id) {
        NoteVo vo = noteService.getOne(NoteDto.builder().id(id).build());
        return Result.success(vo);
    }

    /**
     * 导出笔记
     *
     * @param entity   笔记
     * @param response 响应对象
     */
    @GetMapping("/export")
    @Operation(summary = "导出笔记", description = "导出笔记", method = "GET")
    public void exportExcel(Note entity, HttpServletResponse response) {
        noteService.exportExcel(entity, response);
    }

    /**
     * 置顶或取消置顶笔记
     *
     * @param id 笔记ID
     * @return 结果
     */
    @PutMapping("/top/{id}")
    @Operation(summary = "置顶或取消置顶笔记", description = "置顶或取消置顶笔记", method = "PUT")
    public Result<Void> handleTop(@PathVariable Long id) {
        noteService.handleTop(id);
        return Result.success();
    }

    /**
     * 允许或禁止笔记评论
     *
     * @param id 笔记ID
     * @return 结果
     */
    @PutMapping("/comment/{id}")
    @Operation(summary = "允许或禁止笔记评论", description = "允许或禁止笔记评论", method = "PUT")
    public Result<Void> handleComment(@PathVariable Long id) {
        noteService.handleComment(id);
        return Result.success();
    }

    /**
     * 根据可见性查询我的笔记数量
     *
     * @return 结果
     */
    @GetMapping("/count/my/visible")
    @Operation(summary = "根据可见性查询我的笔记数量", description = "根据可见性查询我的笔记数量", method = "GET")
    public Result<Map<String, Long>> countMyVisible() {
        Map<String, Long> map = noteService.countMyVisible();
        return Result.success(map);
    }
}
