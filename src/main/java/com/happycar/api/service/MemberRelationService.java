package com.happycar.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happycar.api.dao.MemberDao;
import com.happycar.api.dao.MemberRelationDao;
import com.happycar.api.model.HcMember;
import com.happycar.api.model.HcMemberRelation;

@Service
public class MemberRelationService {
	
	private Logger logger = Logger.getLogger(MemberRelationService.class);
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberRelationDao relationDao;

	/**
	 * 增加关系表
	 * @param memberId 当前用户id
	 * @param refereePhone 推荐人手机号
	 */
	@Transactional
	public void add(int memberId,String refereePhone){
		//删除旧的学员与推荐人的关系
		relationDao.deleteByCid(memberId);
		List<HcMember> list = memberDao.findByPhoneAndIsDeleted(refereePhone, 0);
		if(list.size()==0){
			logger.info("未找到推荐人的学员信息,推荐手机号："+refereePhone);
			return;
		}
		List<HcMemberRelation> relations = new ArrayList<>();
		HcMember refereeMember = list.get(0);
		if(refereeMember.getPhone().equals(refereeMember)){
			logger.info("推荐手机号不能与当前用户一样,推荐手机号："+refereePhone);
			return;
		}
		//生成一级成员关系
		HcMemberRelation relation1  = new HcMemberRelation();
		relation1.setPid(refereeMember.getId());
		relation1.setCid(memberId);
		relation1.setLevel(1);
		relation1.setAddTime(new Date());
		relation1.setUpdateTime(new Date());
		relations.add(relation1);
		//查找推荐人的一级变成当前用户的二级
		HcMemberRelation refereeRelation1 = relationDao.findByCidAndLevel(refereeMember.getId(),1);
		if(refereeRelation1!=null){
			HcMemberRelation relation2  = new HcMemberRelation();
			relation2.setPid(refereeRelation1.getPid());
			relation2.setCid(memberId);
			relation2.setLevel(2);
			relation2.setAddTime(new Date());
			relation2.setUpdateTime(new Date());
			relations.add(relation2);
		}
		//查找推荐人的二级变成当前用户的三级
		HcMemberRelation refereeRelation2 = relationDao.findByCidAndLevel(refereeMember.getId(),2);
		if(refereeRelation2!=null){
			HcMemberRelation relation3  = new HcMemberRelation();
			relation3.setPid(refereeRelation2.getPid());
			relation3.setCid(memberId);
			relation3.setLevel(3);
			relation3.setAddTime(new Date());
			relation3.setUpdateTime(new Date());
			relations.add(relation3);
		}
		relationDao.save(relations);
	}
}
