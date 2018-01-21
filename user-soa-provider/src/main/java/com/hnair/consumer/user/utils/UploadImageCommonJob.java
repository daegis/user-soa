package com.hnair.consumer.user.utils;

import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnair.consumer.system.api.ICdnUploadApi;
import com.hnair.consumer.system.enums.CdnFileSourceEnum;
import com.hnair.consumer.utils.ImageUtils;

/**
 * 
 * Description: 上传图片job All Rights Reserved.
 * 
 * @version 1.0 2015年1月10日 上午10:04:21 by 许文轩（xuwenxuan@dangdang.com）创建
 */
public class UploadImageCommonJob implements Callable<UploadImageJobCommonResult> {

	private static final Logger logger = LoggerFactory.getLogger(UploadImageCommonJob.class);

	private String source;
	private byte[] imageBytes;
	private ICdnUploadApi cdnUploadApi;

	private CdnFileSourceEnum cdnFileSource;
	private static final String WX_IMAGE_URL = "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=";

	public UploadImageCommonJob(String source, byte[] imageBytes, ICdnUploadApi cdnUploadApi,
			CdnFileSourceEnum cdnFileSource) {
		this.source = source;
		this.imageBytes = imageBytes;
		this.cdnUploadApi = cdnUploadApi;
		this.cdnFileSource = cdnFileSource;
	}

	@Override
	public UploadImageJobCommonResult call() {
		UploadImageJobCommonResult result = new UploadImageJobCommonResult();
		String cdnPath = null;
		String originalSource = source;
		result.setSource(originalSource);
		try {
			String ext = "jpg";
			if (StringUtils.isNotBlank(source)) {
				if (source.startsWith("//")) {
					source = "http:" + source;
				}

				if (source.indexOf(".png") > 0) {
					ext = "png";
				} else if (source.indexOf(".gif") > 0) {
					ext = "gif";
				} else if (source.indexOf(".jpeg") > 0) {
					ext = "jpeg";
				}
				// 如果是微信公众号图片链接，绕过防盗链
				if (source.indexOf("mmbiz.qpic.cn") > 0) {
					if (source.indexOf("mmbiz_jpg") > 0 || source.indexOf("wx_fmt=jpeg") > 0) {
						ext = "jpeg";
					} else if (source.indexOf("mmbiz_gif") > 0 || source.indexOf("wx_fmt=gif") > 0) {
						ext = "gif";
					} else if (source.indexOf("mmbiz_png") > 0 || source.indexOf("wx_fmt=png") > 0) {
						ext = "png";
					}
					source = WX_IMAGE_URL + source;
				}
				
			}
			if (imageBytes == null) {
				imageBytes = HttpUtils.getBytes(source, false, 5000, 5000);
				
			}
			if (imageBytes == null) {
				result.setResult(false);
			} else {
				// 增加fileType判断
				String fileType = ImageUtils.getFileType(imageBytes);
				if (StringUtils.isNotBlank(fileType)) {
					if (fileType.equals("gif")) {
						ext = "gif";
					} else if (fileType.equals("png")) {
						ext = "png";
					} else if (fileType.equals("jpg")) {
						ext = "jpg";
					}
				}
				cdnPath = cdnUploadApi.upload(imageBytes, null, cdnFileSource.getType(), ext);
				if (cdnPath.indexOf("/hnair/" + cdnFileSource.getType()) > -1) {
					cdnPath = cdnPath.substring(cdnPath.indexOf("/hnair/" + cdnFileSource.getType()));
					result.setResult(true);
				} else {
					logger.error("上传图片返回路径错误,cdnPath:" + cdnPath + ",source:" + source);
					result.setResult(false);
				}
			}
		} catch (Exception e) {
			result.setResult(false);
			logger.info("上传图片失败", e);
		}
		if (!result.getResult()) {
			cdnPath = originalSource;
		}
		result.setCdnPath(cdnPath);

		return result;
	}

}
