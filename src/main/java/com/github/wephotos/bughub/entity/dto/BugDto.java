package com.github.wephotos.bughub.entity.dto;

import java.util.Collections;
import java.util.List;

import com.github.wephotos.bughub.entity.Bug;
import com.github.wephotos.bughub.entity.BugUser;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 类描述
 *
 * @author xcloading
 */
@Getter
@Setter
@ToString
public class BugDto extends Bug {
	private List<BugUser> users = Collections.emptyList();
}