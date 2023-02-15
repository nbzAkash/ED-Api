package in.ag.entity;

import java.time.LocalDate;
import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import lombok.Data;

@Entity
@Table(name="DC_EDUCATION")
@Data
public class DcEducationEntity {
	
	@Id
	@GeneratedValue
	private Integer educationId;
	private String highestDegree;
	private String universityName;
	private Integer graduationYear;
	
	private Long caseNum;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	private Integer createdBy;
	private Integer updatedBy;

}
