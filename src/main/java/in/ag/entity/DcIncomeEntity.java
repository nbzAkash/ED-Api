package in.ag.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import lombok.Data;

@Entity
@Table(name="DC_INCOME")
@Data
public class DcIncomeEntity {
	
	@Id
	@GeneratedValue
	private Integer incomeId;
	private Double salIncome;
	private Double rentIncome;
	private Double propertyIncome;
	
	private Integer caseNum;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	private Integer createdBy;
	private Integer updatedBy;
	


}
