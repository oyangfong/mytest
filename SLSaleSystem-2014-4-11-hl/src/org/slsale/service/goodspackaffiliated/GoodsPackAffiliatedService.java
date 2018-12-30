package org.slsale.service.goodspackaffiliated;

import java.util.List;
import org.slsale.pojo.GoodsPackAffiliated;
/**
 * GoodsPackAffiliatedService
 * @author bdqn_hl
 * @date 2014-2-28
 */
public interface GoodsPackAffiliatedService {
	/**
	 * getGoodsPackAffiliatedListById
	 * @return
	 */
	public List<GoodsPackAffiliated> getGoodsPackAffiliatedListById(GoodsPackAffiliated goodsPackAffiliated) throws Exception;
	
	
	/**
	 * addGoodsPackAffiliated
	 * @param goodsPackAffiliated
	 * @return
	 */
	public int addGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated) throws Exception;
	
	/**
	 * modifyGoodsPackAffiliated
	 * @param goodsPackAffiliated
	 * @return
	 */
	public int modifyGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated);
	
	/**
	 * GoodsPackAffiliated
	 * @param goodsPackAffiliated
	 * @return
	 */
	public int deleteGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated);
}
