package com.happycar.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.happycar.api.model.HcAdvertisement;

@Repository
public interface AdvertisementDao extends JpaRepository<HcAdvertisement, Integer>,JpaSpecificationExecutor<HcAdvertisement>{

	@Modifying
	@Query("update HcAdvertisement set isDeleted=1 where id=?")
	public int deleteById(Integer id);

	public List<HcAdvertisement> findByTypeAndIsDeleted(int type,int isDeleted);
	
}
