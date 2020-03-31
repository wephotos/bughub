package com.github.wephotos.bughub.mapper;

import com.github.wephotos.bughub.entity.Bug;
import com.github.wephotos.bughub.entity.BugStats;
import com.github.wephotos.bughub.entity.vo.BugVo;
import com.github.wephotos.bughub.utils.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BUG持久层接口
 *
 * @author Dell-Aaron
 */
@Mapper
public interface BugMapper extends BaseMapper<Bug> {

    /**
     * bug分页
     *
     * @param pageable pageable
     * @return Page
     */
    List<BugVo> pageList(Pageable pageable);

    /**
     * 总数
     *
     * @param pageable pageable
     * @return Long
     */
    Integer pageCount(Pageable pageable);

    List<BugVo> pageManagerList(Pageable pageable);

    Integer pageManagerCount(Pageable pageable);

    BugVo selectByPrimaryKeyForVo(String id);
    
    /**
     * 统计用户可见项目下非关闭状态BUG
     * @param userId 用户ID
     * @return 统计结果
     */
    List<BugStats> statsNotClosed(@Param("userId") String userId);
    
    /**
     * bug统计
     * @param projectId 项目标识
     * @param userId 用户标识
     * @return 统计结果
     */
    List<BugStats> statistics(@Param("projectId") String projectId, @Param("userId") String userId);

    /**
     * 更新BUG状态
     * @param state
     * @param bugId
     * @return
     */
    int updateState(@Param("state")Integer state, @Param("bugId") String bugId);
}