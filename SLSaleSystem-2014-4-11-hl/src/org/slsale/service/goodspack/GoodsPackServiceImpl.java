package org.slsale.service.goodspack;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.goodspack.GoodsPackMapper;
import org.slsale.dao.goodspackaffiliated.GoodsPackAffiliatedMapper;
import org.slsale.pojo.GoodsPack;
import org.slsale.pojo.GoodsPackAffiliated;
import org.springframework.stereotype.Service;
/**
 * GoodsPackServiceImpl
 * @author bdqn_hl
 * @date 2014-2-28
 */
@Service
public class GoodsPackServiceImpl implements GoodsPackService{
	@Resource
	private GoodsPackMapper mapper;
	@Resource
	private GoodsPackAffiliatedMapper gpaMapper;

	public List<GoodsPack> getGoodsPackList(GoodsPack goodsPack)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.getGoodsPackList(goodsPack);
	}

	public int count(GoodsPack goodsPack) throws Exception {
		// TODO Auto-generated method stub
		return mapper.count(goodsPack);
	}

	public int addGoodsPack(GoodsPack goodsPack) throws Exception {
		// TODO Auto-generated method stub
		return mapper.addGoodsPack(goodsPack);
	}

	public int goodsPackCodeIsExit(GoodsPack goodsPack) throws Exception {
		// TODO Auto-generated method stub
		return mapper.goodsPackCodeIsExit(goodsPack);
	}

	public GoodsPack getGoodsPackById(GoodsPack goodsPack) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getGoodsPackById(goodsPack);
	}

	public int modifyGoodsPack(GoodsPack goodsPack) {
		// TODO Auto-generated method stub
		return mapper.modifyGoodsPack(goodsPack);
	}

	public int deleteGoodsPack(GoodsPack goodsPack) {
		// TODO Auto-generated method stub
		return mapper.deleteGoodsPack(goodsPack);
	}

	public boolean hl_addGoodsPack(GoodsPack goodsPack,List<GoodsPackAffiliated> apaList) throws Exception {
		// TODO Auto-generated method stub
		int addGoodsPackId = 0;
		mapper.addGoodsPack(goodsPack);
		addGoodsPackId = mapper.getAddGoodsPackId();
		if(null != apaList && apaList.size() > 0 && addGoodsPackId != 0){
			for(int i = 0; i <  apaList.size(); i++){
				if(null != apaList.get(i)){
					apaList.get(i).setGoodsPackId(addGoodsPackId);
					gpaMapper.addGoodsPackAffiliated(apaList.get(i));
				}
			}
		}
		return true;
	}

	public boolean hl_modifyGoodsPack(GoodsPack goodsPack,List<GoodsPackAffiliated> apaList) throws Exception {
		// TODO Auto-generated method stub
		mapper.modifyGoodsPack(goodsPack);
		int goodsPackId = goodsPack.getId();
		GoodsPackAffiliated goodsPackAffiliated = new GoodsPackAffiliated();
		goodsPackAffiliated.setGoodsPackId(goodsPackId);
		gpaMapper.deleteGoodsPackAffiliated(goodsPackAffiliated);
		if(null != apaList){
			for(int i = 0; i < apaList.size(); i++){
				if(null != apaList.get(i)){
					apaList.get(i).setGoodsPackId(goodsPackId);
					gpaMapper.addGoodsPackAffiliated(apaList.get(i));
				}
			}
		}
		return true;
	}

}
