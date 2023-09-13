package com.registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.entity.Patient;
import com.registration.service.PatientService;

@RestController
@RequestMapping("/api/pat")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/save")
	public ResponseEntity<Patient>save(@RequestBody Patient patient){
		
		Patient savePat = patientService.savePat(patient);
		return new ResponseEntity<>(savePat,HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Patient>>getAllAData(){
		List<Patient> all = patientService.getAll();
		return ResponseEntity.ok(all);
	}
	

}
