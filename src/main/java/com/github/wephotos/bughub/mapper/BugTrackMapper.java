package com.github.wephotos.bughub.mapper;

import com.github.wephotos.bughub.entity.BugTrack;
import com.github.wephotos.bughub.entity.vo.BugTrackVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BUG轨迹持久层接口
 * @author Dell-Aaron
 *
 */
@Mapper
public interface BugTrackMapper extends BaseMapper<BugTrack> {
    /**
     * BUG追踪列表
     */
    List<BugTrackVo> list(String bugId);
}