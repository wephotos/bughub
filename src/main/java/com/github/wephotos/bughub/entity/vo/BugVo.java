package com.github.wephotos.bughub.entity.vo;

import com.github.wephotos.bughub.entity.Bug;
import com.github.wephotos.bughub.entity.BugLevel;
import com.github.wephotos.bughub.entity.BugState;
import com.github.wephotos.bughub.entity.BugUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * 类描述
 *
 * @author xcloading
 */
@Getter
@Setter
@ToString
public class BugVo extends Bug {

    private String fromUserName;
    /**
     * 归属者
     */
    private String owner;

    private List<BugUser> users = Collections.emptyList();

    public String getStateName(){
        return BugState.resolve(getState()).getName();
    }

    public String getLevelName(){
        return BugLevel.resolve(getLevel()).getName();
    }
}
