/**
 * @Title:AttentionService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月11日
 * @version v1.0
 */
package community.service;

import java.util.List;

import community.dao.AttentionDao;
import entity.AttentionBean;
import entity.ParentUserInfo;

/**
 * @ClassName: AttentionService
 * @Description: TODO
 * @author wzw
 * @date 2019年12月11日
 *
 */
public class AttentionService {

	public int saveAttention(AttentionBean attention) {
		return (new AttentionDao()).insertAttention(attention);
	}
	
	public int ifAttention(String myPhone,String oppositePhone) {
		return (new AttentionDao()).selectIfAttention(myPhone,oppositePhone);
	}
	
	public List<AttentionBean> findAttention(String personId){
		return (new AttentionDao()).queryAttention(personId);
	}
	//查询粉丝
	public List<AttentionBean> findFuns(String personId){
		return (new AttentionDao()).queryFuns(personId);
	}
	//查询关注人数量
	public int findAttentionCount(String personId) {
		return (new AttentionDao()).queryAttentionCount(personId);
	}
	//查询粉丝数量
	public int findFunsCount(String attentionPersonId) {
		return (new AttentionDao()).queryFunsCount(attentionPersonId);
	}
	
	public int delAttention(int id) {
		return (new AttentionDao()).delAttention(id);
	}
	
	public int delAttention(String personId,String attentionPersonId) {
		return (new AttentionDao()).delAttention(personId,attentionPersonId);
	}
}
