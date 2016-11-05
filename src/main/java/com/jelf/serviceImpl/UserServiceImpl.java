package com.jelf.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jelf.dao.Dao;
import com.jelf.entity.DataResult;
import com.jelf.entity.User;
import com.jelf.service.AppService;
import com.jelf.service.UserService;
import com.jelf.util.Util;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private Dao dao;
	private DataResult dataResult = new DataResult();

	@Autowired
	private AppService appService;
	@Autowired
	private HttpSession httpSession;

	@Override
	public DataResult register(User user) {
		user.setPassword(Util.MD5(user.getPassword()));
		dao.saveOrUpdate(user);
		dataResult.setMessage("保存成功");
		dataResult.setState(0);
		return dataResult;
	}

	/**
	 * 登录
	 */
	@Override
	public DataResult login(User user) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("username", user.getUsername()));
		criterions
				.add(Restrictions.eq("password", Util.MD5(user.getPassword())));
		User findUser = dao.findOne(User.class, criterions);
		if (findUser.getId() != null) {
			httpSession.setAttribute("userId", findUser.getId());
			httpSession.setAttribute("nickname", findUser.getNickname());
		}
		dataResult.setMessage("login");
		dataResult.setState(0);
		return dataResult;
	}

}