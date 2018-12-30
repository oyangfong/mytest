package org.slsale.service.goodspackaffiliated;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.goodspackaffiliated.GoodsPackAffiliatedMapper;
import org.slsale.pojo.GoodsPackAffiliated;
import org.springframework.stereotype.Service;
/**
 * GoodsPackAffiliatedServiceImpl
 * @author bdqn_hl
 * @date 2014-2-28
 */
@Service
public class GoodsPackAffiliatedServiceImpl implements GoodsPackAffiliatedService{
	
	@Resource
	private GoodsPackAffiliatedMapper mapper;

	public List<GoodsPackAffiliated> getGoodsPackAffiliatedListById(
			GoodsPackAffiliated goodsPackAffiliated) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getGoodsPackAffiliatedListById(goodsPackAffiliated);
	}

	public int addGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.addGoodsPackAffiliated(goodsPackAffiliated);
	}

	public int modifyGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated) {
		// TODO Auto-generated method stub
		return mapper.modifyGoodsPackAffiliated(goodsPackAffiliated);
	}

	public int deleteGoodsPackAffiliated(GoodsPackAffiliated goodsPackAffiliated) {
		// TODO Auto-generated method stub
		return mapper.deleteGoodsPackAffiliated(goodsPackAffiliated);
	}


}
