package com.zhuanyi.vjwealth.wallet.mobile.common.dto;

import java.util.Map;

public class CommonVariableDTO {
	private Long oldvariableVersion; // 旧的版本同步
	private Long newVariableVersion; // 最新的版本同步

	private Boolean needSync; // 是否需要同步 true 是需要 false 不需要

	private Map<String, Object> variableMap; // 若需要同步 变量组出现

	public Long getOldvariableVersion() {
		return oldvariableVersion;
	}

	public void setOldvariableVersion(Long oldvariableVersion) {
		this.oldvariableVersion = oldvariableVersion;
	}

	public Long getNewVariableVersion() {
		return newVariableVersion;
	}

	public void setNewVariableVersion(Long newVariableVersion) {
		this.newVariableVersion = newVariableVersion;
	}

	public Map<String, Object> getVariableMap() {
		return variableMap;
	}

	public void setVariableMap(Map<String, Object> variableMap) {
		this.variableMap = variableMap;
	}

	public Boolean getNeedSync() {
		return needSync;
	}

	public void setNeedSync(Boolean needSync) {
		this.needSync = needSync;
	}

}
