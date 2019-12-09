/**
 * @Title:HealthHouseService.java
 * @Packagecommunity.service
 * @Description: TODO
 * @auther wzw
 * @date 2019年11月27日
 * @version v1.0
 */
package community.service;

import java.util.List;

import community.dao.HealthHouseDao;
import entity.HealthHouse;

/**
 * @ClassName: HealthHouseService
 * @Description: TODO
 * @author wzw
 * @date 2019年11月27日
 *
 */
public class HealthHouseService {

	/**
	 * 
	 *  @title:saveHouse
	 * @Description: 插入
	 * @throws下午9:16:23
	 * returntype:int
	 */
	public int saveHouse(HealthHouse house) {
		return (new HealthHouseDao()).insertHouse(house);
	}
	/**
	 * 
	 *  @title:findHouse
	 * @Description: 查询
	 * @throws下午9:17:37
	 * returntype:List<HealthHouse>
	 */
	public List<HealthHouse> findHouse(){
		return (new HealthHouseDao()).queryHouse();
	}
	/**
	 * 
	 *  @title:delHouse
	 * @Description: 删除
	 * @throws下午9:18:11
	 * returntype:int
	 */
	public int delHouse(int id) {
		return (new HealthHouseDao()).delHouse(id);
	}
}
