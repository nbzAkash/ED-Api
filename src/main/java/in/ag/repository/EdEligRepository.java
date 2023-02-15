package in.ag.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.EdEligDtlsEntity;

public interface EdEligRepository extends JpaRepository<EdEligDtlsEntity, Serializable> {

}
