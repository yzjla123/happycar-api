package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcExamApply;
import com.happycar.api.vo.HcExamApplyVO;


@Repository
public interface ExamApplyDao extends JpaRepository<HcExamApply, Integer>,JpaSpecificationExecutor<HcExamApply>{

	public HcExamApply findByStatusInAndMemberIdAndAndSubjectTypeAndIsDeleted(List<Integer> statusList, Integer memberId,Integer subjectType,int isDeleted);

	@Query(value="select apply.id,member.name,apply.add_time,status from hc_exam_apply apply,hc_member member where apply.member_id=member.id and member.coach_id=? and apply.subject_type=? and apply.status=0 and apply.is_deleted=0 order by apply.add_time asc",nativeQuery=true)
	public List<Object[]> findNewApplyByCoachIdAndSubjectType(Integer id,Integer subjectType);

	public HcExamApply findByMemberIdAndSubjectTypeAndStatusAndIsDeleted(Integer memberId, int subjectType, int status, int isDeleted);

	
}
