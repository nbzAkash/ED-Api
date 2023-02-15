package in.ag.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ag.binding.EligResponse;
import in.ag.entity.CitizenAppEntity;
import in.ag.entity.CoTgrEntity;
import in.ag.entity.DcCaseEntity;
import in.ag.entity.DcEducationEntity;
import in.ag.entity.DcIncomeEntity;
import in.ag.entity.DcKidEntity;
import in.ag.entity.EdEligDtlsEntity;
import in.ag.entity.PlanEntity;
import in.ag.repository.CitizenAppRepo;
import in.ag.repository.CoTriggerRepository;
import in.ag.repository.DcCaseRepo;
import in.ag.repository.DcEducationRepo;
import in.ag.repository.DcIncomeRepo;
import in.ag.repository.DcKidRepo;
import in.ag.repository.EdEligRepository;
import in.ag.repository.PlanRepo;

@Service
public class EdServiceImpl implements EdService {

	@Autowired
	private DcCaseRepo dcCaseRepo;

	@Autowired
	private PlanRepo planRepo;

	@Autowired
	private DcIncomeRepo dcIncomeRepo;

	@Autowired
	private DcKidRepo dcKidRepo;
	
	@Autowired
	private CitizenAppRepo appRepository;
	
	@Autowired
	private DcEducationRepo dcEducationRepo;
	
	@Autowired
	private CoTriggerRepository coTgrRepo;
	
	@Autowired
	private EdEligRepository edEligibilityRepo;
	
	boolean noKids = false;
	boolean ageFlag = true;

	@Override
	public EligResponse determineEligibility(Long caseNum) {

		Integer planId = null;
		String planName = null;
		Integer appId = null;

		EligResponse response = new EligResponse();

		Optional<DcCaseEntity> dcCaseEntity = dcCaseRepo.findById(caseNum);
		if (dcCaseEntity.isPresent()) {
			planId = dcCaseEntity.get().getPlanId();
			appId = dcCaseEntity.get().getAppId();
		}

		Optional<PlanEntity> planEntity = planRepo.findById(planId);
		if (planEntity.isPresent()) {
			planName = planEntity.get().getPlanName();
		}
		
		Optional<CitizenAppEntity> citizenAppEntity = appRepository.findById(appId);
		CitizenAppEntity citizenApp = citizenAppEntity.get();

		DcIncomeEntity income = dcIncomeRepo.findByCaseNum(caseNum);
		List<DcKidEntity> kids = dcKidRepo.findByCaseNum(caseNum);
		
		if("SNAP".equals(planName)) {
			
			if(income.getSalIncome() > 300) {
				response.setDenialReason("High Income");
			}
			
		}else if("CCAP".equals(planName)) {
			
			
			if(!kids.isEmpty()) {
				if(!kids.isEmpty()) {
					kids.forEach(kid -> {
						LocalDate dob = kid.getKidDob();
						LocalDate today = LocalDate.now();
						Period p = Period.between(dob, today);
						int year = p.getYears();
						if(year > 16 ) {
							ageFlag = false;
						}
					});
				}else {
						response.setDenialReason("No Kids Available");
						noKids = true;
					}
					if(income.getSalIncome() > 300 ) {
						response.setDenialReason("High Income");
					}
					if(noKids && income.getSalIncome() > 300 ) {
						response.setDenialReason("High Income + No Kids");
					}
					if(!ageFlag) {
						response.setDenialReason("Kid Age > 16");
					}
					if(income.getSalIncome() > 300 && !ageFlag) {
						response.setDenialReason("High Income + Kid Age > 16");
					}
				}
			
		}else if("Medicaid".equals(planName)) {
			
			Double salaryIncome = income.getSalIncome();
			Double rentIncome = income.getRentIncome();
			Double propertyIncome = income.getPropertyIncome();
			
			if(salaryIncome > 300) {
				response.setDenialReason("High Salary Income");
			}
			if(rentIncome > 0) {
				response.setDenialReason("Rental Income Available");
			}
			if(propertyIncome > 0 ) {
				response.setDenialReason("Property Income Available");
			}
			if(rentIncome > 0  && propertyIncome > 0){
				response.setDenialReason("Rental + Property Income Available");
			}
			if(salaryIncome > 300 && rentIncome > 0 && propertyIncome > 0) {
				response.setDenialReason("Salary + Property + Rental Income Available");
			}
			
		}else if("Medicare".equals(planName)) {
			
			LocalDate dob = citizenApp.getDob();
			LocalDate today = LocalDate.now();
			
			Period period = Period.between(dob, today);
			int years = period.getYears();
			
			if(years < 65) {
				response.setDenialReason("Age < 65 years");
			}
			
		}else if("RIW".equals(planName)) {
			DcEducationEntity educationEntity = dcEducationRepo.findByCaseNum(caseNum);
			Integer graduationYear = educationEntity.getGraduationYear();
			if(graduationYear <= 0) {
				response.setDenialReason("Not Graduated");
			}
			if(income.getSalIncome() > 0) {
				response.setDenialReason("Already Employee");
			}
		
		}
		
		response.setPlanName(planName);
		if(response.getDenialReason() != null) {
			response.setPlanStatus("DENIED");
		}else {
			response.setPlanStatus("APPROVED");
			response.setPlanStartDate(LocalDate.now().plusDays(1));
			response.setPlanEndDate(LocalDate.now().plusMonths(3));
			response.setBenefitAmt(450.45);
		}
		
		EdEligDtlsEntity edEntity = new EdEligDtlsEntity();
		BeanUtils.copyProperties(response, edEntity);
		edEligibilityRepo.save(edEntity);
		
		CoTgrEntity coEntity = new CoTgrEntity();
		coEntity.setCaseNum(caseNum);
		coEntity.setTgrStatus("PENDING");
		
		coTgrRepo.save(coEntity);
		
		return response;

	}

}
