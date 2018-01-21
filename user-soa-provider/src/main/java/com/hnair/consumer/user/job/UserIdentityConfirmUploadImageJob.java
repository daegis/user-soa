package com.hnair.consumer.user.job;

import java.util.concurrent.Callable;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.system.api.ICdnUploadApi;
import com.hnair.consumer.system.enums.CdnFileSourceEnum;
import com.hnair.consumer.user.model.UserIdentityConfirm;
import com.hnair.consumer.user.utils.HttpUtils;
import com.hnair.consumer.user.utils.UploadImageJobCommonResult;
import com.hnair.consumer.utils.ImageUtils;

/**
 * 实名认证上传图片
 * @author 张加娄2017年11月9日 18:47:45
 *
 */
public class UserIdentityConfirmUploadImageJob implements Callable<UploadImageJobCommonResult> {

	private static final Logger logger = LoggerFactory.getLogger(UserIdentityConfirmUploadImageJob.class);

	private String photo;
	private Long userConfirmId;
	private ICdnUploadApi cdnUploadApi;
	private ICommonService ucenterCommonService;

	private CdnFileSourceEnum cdnFileSource;
	private static final String WX_IMAGE_URL = "http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl=";

	public UserIdentityConfirmUploadImageJob(String photo, Long userConfirmId, ICdnUploadApi cdnUploadApi,
			CdnFileSourceEnum cdnFileSource,ICommonService ucenterCommonService) {
		this.photo = photo;
		this.userConfirmId = userConfirmId;
		this.cdnUploadApi = cdnUploadApi;
		this.cdnFileSource = cdnFileSource;
		this.ucenterCommonService = ucenterCommonService;
	}

	@Override
	public UploadImageJobCommonResult call() {
		UploadImageJobCommonResult result = new UploadImageJobCommonResult();
		String cdnPath = null;
		try {
			String ext = "jpg";
			org.apache.commons.codec.binary.Base64 decoder = new Base64();
			byte[] imageBytes = decoder.decode(photo);
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
					logger.error("上传图片返回路径错误,cdnPath:" + cdnPath );
					result.setResult(false);
				}
			}
			result.setCdnPath(cdnPath);
			UserIdentityConfirm userIDConfirm = new UserIdentityConfirm();
			userIDConfirm.setId(userConfirmId);
			userIDConfirm.setPhotoUrl(cdnPath);
			ucenterCommonService.update(userIDConfirm);
		} catch (Exception e) {
			result.setResult(false);
			logger.info("上传图片失败", e);
		}
		
		return result;
	}

}
