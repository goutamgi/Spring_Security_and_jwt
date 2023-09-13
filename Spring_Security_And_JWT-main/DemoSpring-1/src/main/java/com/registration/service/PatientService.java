package com.registration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.registration.entity.Patient;
import com.registration.repo.PatientRepo;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepo patientRepo;
	
	public Patient savePat(Patient patient) {
		
		Patient saveData = patientRepo.save(patient);
		return saveData;
	}
	
	public List<Patient>getAll(){
		List<Patient> allPat = patientRepo.findAll();
		return allPat;
	}
	

}
