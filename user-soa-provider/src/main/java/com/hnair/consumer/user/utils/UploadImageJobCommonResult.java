package com.hnair.consumer.user.utils;

import java.io.Serializable;

/**
 * 
 * @author 许文轩
 * @comment 上传图片任务结果
 * @date 2017年3月9日 上午9:12:34
 *
 */
public class UploadImageJobCommonResult implements Serializable {

	private static final long serialVersionUID = -2082526973082458049L;

	private String cdnPath;
	private String source;

	private boolean result = false;

	public String getCdnPath() {
		return cdnPath;
	}

	public void setCdnPath(String cdnPath) {
		this.cdnPath = cdnPath;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
