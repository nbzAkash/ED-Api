package in.ag.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.CoTgrEntity;

public interface CoTriggerRepository extends JpaRepository<CoTgrEntity, Serializable> {

}
