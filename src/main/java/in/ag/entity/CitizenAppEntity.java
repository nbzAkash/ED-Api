package in.ag.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name="CITIZEN-APPS")
@Data
public class CitizenAppEntity {
	
	@Id
	@GeneratedValue
	private Integer appId;
	private String fname;
	private String email;
	private Long mobNo;
	private String gender;
	private LocalDate dob;
	private Long ssn;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	private Integer createdBy;
	private Integer updatedBy;


}
