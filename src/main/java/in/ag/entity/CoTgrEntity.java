package in.ag.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="CO_TRIGGERS")
@Data
public class CoTgrEntity {
	
	@Id
	@GeneratedValue
	private  Integer coTgrId;
	
	private Long caseNum;
	
	private byte[] pdf;
	
	private String tgrStatus;

}
