package com.hnair.consumer.user.api;

import java.util.List;
import java.util.Map;

import com.hnair.consumer.user.enums.AttentionTypeEnum;
import com.hnair.consumer.user.model.Attention;
import com.hnair.consumer.user.vo.UserMessageVo;


/**
 * Description: 关注接口
 * All Rights Reserved.
 * @version 1.0  2016年11月17日 下午2:16:52  by 李超（li-ch3@hnair.com）创建
 */
public interface IAttentionApi {
	/**
	 * Description: 关注
	 * @Version1.0 2016年11月17日 下午2:17:48 by 李超（li-ch3@hnair.com）创建
	 * @param attention
	 * @return
	 */
	public UserMessageVo<Attention> attention(Attention attention);
	/**
	 * Description: 取消关注
	 * @Version1.0 2016年11月17日 下午2:21:20 by 李超（li-ch3@hnair.com）创建
	 * @param attention
	 * @return
	 */
	public UserMessageVo<Attention> cancelAttention(Attention attention);
	/**
	 * Description: 查询关注信息(是否关注,关注数量)
	 * @Version1.0 2016年11月17日 下午2:52:06 by 李超（li-ch3@hnair.com）创建
	 * @return isAttention(是否关注,0:否,1:是), myCount粉丝数量,attentionCount关注数,目标用户关注数
	 */
	public Map<String,Object> queryAttentionInfo(Long userId,Long targetUserId);
	/**
	 * Description:查询关注列表 
	 * @Version1.0 2016年11月21日 下午6:14:55 by 李超（li-ch3@hnair.com）创建
	 * @param createTime
	 * @param userId
	 * @param type {@link AttentionTypeEnum}
	 * @param pageSize
	 * @return
	 */
	public Map<String,Object> queryAttentionList(String createTime,Long userId,Integer type,Integer pageSize);
	/**
	 * Description: 查询关注关系
	 * @Version1.0 2016年12月17日 下午6:02:58 by 李超（li-ch3@hnair.com）创建
	 * @param userId
	 * @param targetIds
	 * @return
	 */
	public Map<Long,AttentionTypeEnum> queryAttentionRelation(Long userId,List<Long> targetUserIds);
}
