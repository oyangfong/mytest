package org.slsale.service.dataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.datadictionary.DataDictionaryMapper;
import org.slsale.pojo.DataDictionary;
import org.springframework.stereotype.Service;
/**
 * DataDictionaryServiceImpl
 * @author bdqn_hl
 * @date 2014-2-24
 */
@Service
public class DataDictionaryServiceImpl implements DataDictionaryService{
	@Resource
	private DataDictionaryMapper mapper;
	public List<DataDictionary> getDataDictionaries(
			DataDictionary dataDictionary) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getDataDictionaries(dataDictionary);
	}

	public int addDataDictionary(DataDictionary dataDictionary)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.addDataDictionary(dataDictionary);
	}

	public int modifyDataDictionary(DataDictionary dataDictionary)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.modifyDataDictionary(dataDictionary);
	}

	public int deleteDataDictionary(DataDictionary dataDictionary)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.deleteDataDictionary(dataDictionary);
	}

	public List<DataDictionary> getDataDictionariesCategory() throws Exception {
		// TODO Auto-generated method stub
		return mapper.getDataDictionariesCategory();
	}

	public List<DataDictionary> getDataDictionariesNotIn(
			DataDictionary dataDictionary) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getDataDictionariesNotIn(dataDictionary);
	}

	public int modifyDataDictionarys(DataDictionary dataDictionary)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.modifyDataDictionarys(dataDictionary);
	}

	public int typeCodeOrValueIdIsExit(DataDictionary dataDictionary) throws Exception {
		// TODO Auto-generated method stub
		return mapper.typeCodeOrValueIdIsExit(dataDictionary);
	}

	@Override
	public int maxValueId(DataDictionary dataDictionary) throws Exception {
		// TODO Auto-generated method stub
		return mapper.maxValueId(dataDictionary);
	}

}
