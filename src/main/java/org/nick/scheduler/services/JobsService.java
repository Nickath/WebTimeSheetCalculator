package org.nick.scheduler.services;

import java.util.HashMap;
import java.util.List;


import org.springframework.stereotype.Service;

@Service("jobsService")
public interface JobsService {

	public HashMap<String, String> getBelowBaseUsersMail();
	
	
}
