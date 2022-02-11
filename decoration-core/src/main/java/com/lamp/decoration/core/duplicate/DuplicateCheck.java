package com.lamp.decoration.core.duplicate;

import com.lamp.decoration.core.duplicate.DuplicateSubmissionHandlerInterceptor.DuplicateSubmissionData;

public interface DuplicateCheck {

	public boolean isDuplicateCheck(DuplicateSubmissionData duplicateSubmissionData);
	
	public void unlock(DuplicateSubmissionData duplicateSubmissionData);
}
