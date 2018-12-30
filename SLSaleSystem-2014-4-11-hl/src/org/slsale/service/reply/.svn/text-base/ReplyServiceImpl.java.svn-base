package org.slsale.service.reply;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.reply.ReplyMapper;
import org.slsale.pojo.Reply;
import org.springframework.stereotype.Service;
/**
 * ReplyServiceImpl
 * @author bdqn_hl
 * @date 2014-2-25
 */
@Service
public class ReplyServiceImpl implements ReplyService{

	@Resource
	private ReplyMapper mapper;
	public List<Reply> getReplyList(Reply replay) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getReplyList(replay);
	}

	public int count(Reply replay) throws Exception {
		// TODO Auto-generated method stub
		return mapper.count(replay);
	}

	public int delete(Reply replay) throws Exception {
		// TODO Auto-generated method stub
		return mapper.delete(replay);
	}

	public int addReply(Reply reply) throws Exception {
		// TODO Auto-generated method stub
		return mapper.addReply(reply);
	}

}
