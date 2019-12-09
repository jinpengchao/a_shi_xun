/**
 * @Title:CollectionService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther wzw
 * @date 2019年12月7日
 * @version v1.0
 */
package community.service;

import java.util.List;

import community.dao.CollectionDao;
import entity.CollectionBean;

/**
 * @ClassName: CollectionService
 * @Description: TODO
 * @author wzw
 * @date 2019年12月7日
 *
 */
public class CollectionService {

	public int saveCollection(CollectionBean collection) {
		return (new CollectionDao()).insertCollection(collection);
	}
	/**
	 * 
	 *  @title:findCollection
	 * @Description: 查询所有个人的收藏
	 * @throws下午3:54:19
	 * returntype:List<CollectionBean>
	 */
	public List<CollectionBean> findCollection(String personId){
		return (new CollectionDao()).queryCollection(personId);
	}
	/**
	 * 
	 *  @title:findCollecion
	 * @Description: 查询某一个帖子是否收藏过
	 * @throws下午3:55:46
	 * returntype:String
	 */
	public String findCollecion(CollectionBean collection) {
		return (new CollectionDao()).queryCollection(collection);
	}
	public int delCollection(int id) {
		return (new CollectionDao()).delCollecion(id);
	}
	
}
