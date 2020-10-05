package net.app.fixMypLACE.validators;

import net.app.fixMypLACE.dto.Event;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
 

public class EventValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Event.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Event event = (Event) target;
//		if(event.getFile() == null || event.getFile().getOriginalFilename().equals("")) {
//			errors.rejectValue("file", null, "Please select a file to upload!");
//			return;
//		}
//		if(! (event.getFile().getContentType().equals("image/jpeg") || 
//				event.getFile().getContentType().equals("image/png")) ||
//				event.getFile().getContentType().equals("image/gif")
//			 )
//			{
//				errors.rejectValue("file", null, "Please select an image file to upload!");
//				return;	
//			}
//
	}

}
