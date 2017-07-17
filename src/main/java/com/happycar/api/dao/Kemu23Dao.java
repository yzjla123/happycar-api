package com.happycar.api.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcKemu23;


@Repository
public interface Kemu23Dao extends JpaRepository<HcKemu23, Integer>,JpaSpecificationExecutor<HcKemu23>{

	Page<HcKemu23> findBySubjectIdAndIsDeleted(Integer subjectId, int isDeleted, Pageable page);

}
