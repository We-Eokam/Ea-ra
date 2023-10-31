package com.eokam.proof.application.service;

import java.util.List;

import com.eokam.proof.application.dto.ProofDto;

public interface ProofService {
	List<ProofDto> getMyProofList(String jwt, Integer page, Integer size);
}
