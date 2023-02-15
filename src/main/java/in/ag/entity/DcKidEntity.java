package in.ag.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import lombok.Data;

@Entity
@Table(name="DC_KIDS")
@Data
public class DcKidEntity {
	
	private Integer kidId;
	private String kidName;
	private LocalDate kidDob;
	private Long kidSsn;
	private String kidGender;
	
	private Long caseNum;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	private Integer createdBy;
	private Integer updatedBy;

}
