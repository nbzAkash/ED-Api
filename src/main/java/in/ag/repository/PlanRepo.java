package in.ag.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ag.entity.PlanEntity;

public interface PlanRepo extends JpaRepository<PlanEntity, Serializable> {

}
