package cc.endmc.server.controller.other;

import cc.endmc.common.annotation.Log;
import cc.endmc.common.core.controller.BaseController;
import cc.endmc.common.core.domain.AjaxResult;
import cc.endmc.common.core.page.TableDataInfo;
import cc.endmc.common.enums.BusinessType;
import cc.endmc.common.utils.poi.ExcelUtil;
import cc.endmc.server.domain.other.HistoryCommand;
import cc.endmc.server.service.other.IHistoryCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 历史命令Controller
 *
 * @author ruoyi
 * @date 2025-02-11
 */
@RestController
@RequestMapping("/history/command")
public class HistoryCommandController extends BaseController {
    @Autowired
    private IHistoryCommandService historyCommandService;

    /**
     * 查询历史命令列表
     */
    //  @PreAuthorize("@ss.hasPermi('history:command:list')")
    @GetMapping("/list")
    public TableDataInfo list(HistoryCommand historyCommand) {
        startPage();
        List<HistoryCommand> list = historyCommandService.selectHistoryCommandList(historyCommand);
        return getDataTable(list);
    }

    /**
     * 导出历史命令列表
     */
    @PreAuthorize("@ss.hasPermi('history:command:export')")
    @Log(title = "历史命令", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HistoryCommand historyCommand) {
        List<HistoryCommand> list = historyCommandService.selectHistoryCommandList(historyCommand);
        ExcelUtil<HistoryCommand> util = new ExcelUtil<HistoryCommand>(HistoryCommand.class);
        util.exportExcel(response, list, "历史命令数据");
    }

    /**
     * 获取历史命令详细信息
     */
    @PreAuthorize("@ss.hasPermi('history:command:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(historyCommandService.selectHistoryCommandById(id));
    }

    /**
     * 删除历史命令
     */
    @PreAuthorize("@ss.hasPermi('history:command:remove')")
    @Log(title = "历史命令", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(historyCommandService.deleteHistoryCommandByIds(ids));
    }
}
