package in.ag.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.DcCaseEntity;

public interface DcCaseRepo extends JpaRepository<DcCaseEntity, Serializable> {

}
