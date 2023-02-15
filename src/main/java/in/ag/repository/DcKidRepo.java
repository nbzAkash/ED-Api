package in.ag.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.DcKidEntity;

public interface DcKidRepo extends JpaRepository<DcKidEntity, Serializable> {
	
	public List<DcKidEntity> findByCaseNum(Long caseNum);
	

}
