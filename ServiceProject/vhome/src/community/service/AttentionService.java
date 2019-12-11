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
	
	public List<AttentionBean> findAttention(String personId){
		return (new AttentionDao()).queryAttention(personId);
	}
	
	public int findAttentionCount(String personId) {
		return (new AttentionDao()).queryAttentionCount(personId);
	}
	
	public int delAttention(int id) {
		return (new AttentionDao()).delAttention(id);
	}
	
	public int delAttention(String personId,String attentionPersonId) {
		return (new AttentionDao()).delAttention(personId,attentionPersonId);
	}
}
