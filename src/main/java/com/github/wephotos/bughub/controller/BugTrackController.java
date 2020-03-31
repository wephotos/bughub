package com.github.wephotos.bughub.controller;

import com.github.wephotos.bughub.entity.BugState;
import com.github.wephotos.bughub.entity.BugTrack;
import com.github.wephotos.bughub.entity.HubUser;
import com.github.wephotos.bughub.entity.vo.BugTrackVo;
import com.github.wephotos.bughub.service.BugTrackService;
import com.github.wephotos.bughub.utils.BugUtils;
import com.github.wephotos.bughub.utils.RestObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * BUG追踪管理Web接口
 *
 * @author xcloading
 */
@RestController
@RequestMapping("/bug-track")
public class BugTrackController {
    @Resource
    private BugTrackService bugTrackService;

    /**
     * 追踪列表
     *
     * @param bugId bug标识
     * @return RestObject
     */
    @GetMapping("/list/{bugId}")
    public RestObject list(@PathVariable String bugId) {
        List<BugTrackVo> list = bugTrackService.list(bugId);
        return RestObject.builder().data(list).build();
    }

    /**
     * 追加
     *
     * @param bugTrack bugTrack
     * @return RestObject
     */
    @PostMapping("/append")
    public RestObject append(@RequestBody BugTrack bugTrack) {
        HubUser hubUser = BugUtils.getAuthenticationUser();
        bugTrack.setState(BugState.OPEN.getValue());
        String id = bugTrackService.add(bugTrack, hubUser);
        return RestObject.builder().data(id).build();
    }
    
    /**
     * 解决
     *
     * @param bugTrack bugTrack
     * @return RestObject
     */
    @PostMapping("/fixed")
    public RestObject fixed(@RequestBody BugTrack bugTrack) {
        HubUser hubUser = BugUtils.getAuthenticationUser();
        bugTrack.setState(BugState.FIXED.getValue());
        String id = bugTrackService.add(bugTrack,hubUser);
        return RestObject.builder().data(id).build();
    }
    
    /**
     * 驳回
     *
     * @param bugTrack bugTrack
     * @return RestObject
     */
    @PostMapping("/reject")
    public RestObject reject(@RequestBody BugTrack bugTrack) {
        HubUser hubUser = BugUtils.getAuthenticationUser();
        bugTrack.setState(BugState.REJECT.getValue());
        String id = bugTrackService.add(bugTrack,hubUser);
        return RestObject.builder().data(id).build();
    }
    
    /**
     * 关闭
     *
     * @param bugTrack bugTrack
     * @return RestObject
     */
    @PostMapping("/closed")
    public RestObject closed(@RequestBody BugTrack bugTrack) {
        HubUser hubUser = BugUtils.getAuthenticationUser();
        bugTrack.setState(BugState.CLOSED.getValue());
        String id = bugTrackService.add(bugTrack, hubUser);
        return RestObject.builder().data(id).build();
    }
    
    /**
     * 移交
     * @param track
     * @return
     */
    @PostMapping("/hand-over")
    public RestObject handOver(@RequestBody BugTrack track) {
    	HubUser hubUser = BugUtils.getAuthenticationUser();
    	track.setState(BugState.OPEN.getValue());
    	String id = bugTrackService.handOver(track, hubUser);
        return RestObject.builder().data(id).build();
    }
}
