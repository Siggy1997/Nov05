package com.drhome.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
	
	@Autowired 
	private HomeDAO homeDAO;
	
}
