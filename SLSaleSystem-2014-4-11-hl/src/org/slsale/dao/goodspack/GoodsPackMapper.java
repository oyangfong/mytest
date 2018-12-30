package org.slsale.dao.goodspack;

import java.util.List;

import org.slsale.pojo.GoodsPack;

/**
 * GoodsPackMapper
 * @author bdqn_hl
 * @date 2014-3-2
 */
public interface GoodsPackMapper {
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
	
	/**
	 * getAddGoodsPackId
	 * @return
	 */
	public int getAddGoodsPackId() throws Exception;
}
