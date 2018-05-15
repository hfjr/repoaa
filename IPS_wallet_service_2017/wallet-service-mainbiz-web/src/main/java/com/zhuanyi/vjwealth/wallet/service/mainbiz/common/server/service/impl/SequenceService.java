package com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fab.server.sequence.mapper.ICommonSequenceMapper;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.common.server.service.ISequenceService;

/**
 * 获取sequence 序列 公共service
 * 
 * @author Speed J
 *
 */
@Service
public class SequenceService implements ISequenceService {

	@Autowired
	private ICommonSequenceMapper commonSequenceMapper;

	public Long getCurrentLongValue() {
		return this.getCurrentLongValue(ISequenceService.SEQ_COMMON);
	}

	public Long getCurrentLongValue(String sequenceName) {
		return new Long(this.getCurrentStringValue(sequenceName));
	}

	public String getCurrentStringValue() {
		return this.getCurrentStringValue(ISequenceService.SEQ_COMMON);
	}

	public String getCurrentStringValue(String sequenceName) {
		return this.getCurrentStringValue(sequenceName, ISequenceService.DEFAULT_ADDED_TO_LENGTH);
	}

	public String getCurrentStringValue(String sequenceName, int addedToLength) {
		return this.getCurrentStringValue(sequenceName, addedToLength, ISequenceService.DEFAULT_ADDED_CHARACTER);
	}

	public String getCurrentStringValue(String sequenceName, int addedToLength, char addedCharacter) {
		Long currentValue = commonSequenceMapper.getCurrentValue(sequenceName);
		if (currentValue == null) {
			throw new RuntimeException("获取当前sequence[" + sequenceName + "]不存在.");
		}
		return addedCharAndLength(currentValue, addedToLength, addedCharacter);
	}
	
	public String getBussinessCurrentStringValue(String bussiessType,String sequenceName, int allLength){
		int addedToLength = allLength - bussiessType.length();
		return this.getCurrentStringValue(sequenceName,	addedToLength);
	}

	public Long getNextLongValue() {
		return this.getNextLongValue(ISequenceService.SEQ_COMMON);
	}

	public Long getNextLongValue(String sequenceName) {
		return new Long(this.getNextStringValue(sequenceName));
	}

	public String getNextStringValue() {
		return this.getNextStringValue(ISequenceService.SEQ_COMMON);
	}

	public String getNextStringValue(String sequenceName) {
		return this.getNextStringValue(sequenceName, ISequenceService.DEFAULT_ADDED_TO_LENGTH);
	}

	public String getNextStringValue(String sequenceName, int addedToLength) {
		return this.getNextStringValue(sequenceName, addedToLength, ISequenceService.DEFAULT_ADDED_CHARACTER);
	}

	public String getNextStringValue(String sequenceName, int addedToLength, char addedCharacter) {
		Long nextValue = commonSequenceMapper.getNextValue(sequenceName);
		if (nextValue == null) {
			throw new RuntimeException("获取下一个sequence[" + sequenceName + "]不存在.");
		}
		return addedCharAndLength(nextValue, addedToLength, addedCharacter);
	}

	// 补齐方法
	private String addedCharAndLength(Long value, int addedToLength, char addedCharacter) {
		StringBuffer sb = new StringBuffer();

		// 大于数字自身长度
		if (addedToLength > 0 && addedToLength > Long.toString(value).length()) {
			int addLengthCount = addedToLength - Long.toString(value).length();
			for (int i = 0; i < addLengthCount; i++) {
				sb.append(addedCharacter);
			}
		}
		return sb.append(value).toString();
	}

}
