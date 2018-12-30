package org.slsale.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.user.UserMapper;
import org.slsale.pojo.User;
import org.slsale.service.user.UserService;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 * @author bdqn_hl
 * @date 2014-2-21
 */

@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserMapper mapper;
	
	public List<User> getUserList(User user) throws Exception{
		// TODO Auto-generated method stub
		return mapper.getUserList(user);
	}

	public User getLoginUser(User user) throws Exception{
		// TODO Auto-generated method stub
		return mapper.getLoginUser(user);
	}

	public int addUser(User user) throws Exception{
		// TODO Auto-generated method stub
		return mapper.addUser(user);
	}

	public int deleteUser(User user) throws Exception{
		// TODO Auto-generated method stub
		return mapper.deleteUser(user);
	}

	public int modifyUser(User user) throws Exception{
		// TODO Auto-generated method stub
		return mapper.modifyUser(user);
	}

	public int count(User user) throws Exception {
		// TODO Auto-generated method stub
		return mapper.count(user);
	}

	public User getUserById(User user) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getUserById(user);
	}

	public int delUserPic(User user) throws Exception {
		// TODO Auto-generated method stub
		return mapper.delUserPic(user);
	}

	public int loginCodeIsExit(User user) throws Exception {
		// TODO Auto-generated method stub
		return mapper.loginCodeIsExit(user);
	}

	public List<User> getUserListBySearch(User user) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getUserListBySearch(user);
	}

}
