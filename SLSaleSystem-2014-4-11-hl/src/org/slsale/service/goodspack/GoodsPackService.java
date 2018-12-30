package org.slsale.service.goodspack;

import java.util.List;

import org.slsale.pojo.GoodsPack;
import org.slsale.pojo.GoodsPackAffiliated;
/**
 * GoodsPackService
 * @author bdqn_hl
 * @date 2014-2-28
 */
public interface GoodsPackService {
	
	/**
	 * getGoodsInfoList
	 * @return
	 */
	public List<GoodsPack> getGoodsPackList(GoodsPack goodsPack) throws Exception;
	
	/**
	 * count
	 * @param goodsPack
	 * @return
	 * @throws Exception
	 */
	public int count(GoodsPack goodsPack) throws Exception;
	
	/**
	 * addGoodsPack
	 * @param goodsPack
	 * @return
	 */
	public int addGoodsPack(GoodsPack goodsPack) throws Exception;
	/**
	 * hl_addGoodsPack
	 * @param goodsPack
	 * @return
	 * @throws Exception
	 */
	public boolean hl_addGoodsPack(GoodsPack goodsPack,List<GoodsPackAffiliated> apaList) throws Exception;
	
	/**
	 * hl_modifyGoodsPack
	 * @param goodsPack
	 * @return
	 * @throws Exception
	 */
	public boolean hl_modifyGoodsPack(GoodsPack goodsPack,List<GoodsPackAffiliated> apaList) throws Exception;
	
	/**
	 * goodsPackCodeIsExit
	 * @param goodsPack
	 * @return
	 * @throws Exception
	 */
	public int goodsPackCodeIsExit(GoodsPack goodsPack) throws Exception;
	
	/**
	 * getGoodsPackById
	 * @param goodsPack
	 * @return
	 */
	public GoodsPack getGoodsPackById(GoodsPack goodsPack) throws Exception;
	
	
	/**
	 * modifyGoodsPack
	 * @param goodsPack
	 * @return
	 */
	public int modifyGoodsPack(GoodsPack goodsPack);
	
	/**
	 * deleteGoodsPack
	 * @param goodsPack
	 * @return
	 */
	public int deleteGoodsPack(GoodsPack goodsPack);
}
