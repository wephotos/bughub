package com.github.wephotos.bughub.entity.vo;

import com.github.wephotos.bughub.entity.BugState;
import com.github.wephotos.bughub.entity.BugTrack;
import com.github.wephotos.bughub.entity.HubFile;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;


@Getter
@Setter
public class BugTrackVo extends BugTrack {
	/**
	 * Bug附件
	 */
	private List<HubFile> files = Collections.emptyList();

	public String getStateName(){
		return BugState.resolve(getState()).getName();
	}
}