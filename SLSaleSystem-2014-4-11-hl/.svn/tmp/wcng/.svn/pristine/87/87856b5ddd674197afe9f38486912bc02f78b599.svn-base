package org.slsale.dao.datadictionary;

import java.util.List;

import org.slsale.pojo.DataDictionary;
import org.slsale.pojo.User;

/**
 * DataDictionaryMapper
 * @author bdqn_hl
 * @date 2014-3-3
 */
public interface DataDictionaryMapper {
	
	
	/**
	 * getDataDictionaries
	 * @return dataDictionary
	 */
	public List<DataDictionary> getDataDictionaries(DataDictionary dataDictionary) throws Exception;
	/**
	 * getDataDictionariesNotIn
	 * sql:SELECT * FROM data_dictionary WHERE  typeCode = 'CARD_TYPE' AND typeCode NOT IN (SELECT typecode FROM data_dictionary WHERE typecode = 'INFO_TYPE')
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	public List<DataDictionary> getDataDictionariesNotIn(DataDictionary dataDictionary) throws Exception;
	/**
	 * getDataDictionariesCategory
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	public List<DataDictionary> getDataDictionariesCategory() throws Exception;
	
	
	/**
	 * addDataDictionary
	 * @param dataDictionary
	 * @return int
	 */
	public int addDataDictionary(DataDictionary dataDictionary) throws Exception;
	/**
	 * modifyDataDictionary
	 * @param dataDictionary
	 * @return int
	 */
	public int modifyDataDictionary(DataDictionary dataDictionary) throws Exception;
	/**
	 * modifyDataDictionarys
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	public int modifyDataDictionarys(DataDictionary dataDictionary) throws Exception;
	/**
	 * deleteDataDictionary
	 * @param dataDictionary
	 * @return
	 */
	public int deleteDataDictionary(DataDictionary dataDictionary) throws Exception;
	
	/**
	 * typeCodeOrValueIdIsExit
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	public int typeCodeOrValueIdIsExit(DataDictionary dataDictionary) throws Exception;
	/**
	 * maxValueId
	 * @param dataDictionary
	 * @return
	 * @throws Exception
	 */
	public int maxValueId(DataDictionary dataDictionary) throws Exception;
	
}
