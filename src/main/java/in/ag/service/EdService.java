package in.ag.service;

import in.ag.binding.EligResponse;

public interface EdService {
	
	public EligResponse determineEligibility(Long caseNum);

}
