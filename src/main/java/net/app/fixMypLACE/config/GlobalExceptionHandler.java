package net.app.fixMypLACE.config;

//import net.app.fixMypLACE.exceptions.PlaceNotFound;

import net.app.fixMypLACE.exceptions.PlaceNotFound;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global exception handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(final Throwable throwable) {

        logger.error("Exception during execution of SpringSecurity application", throwable);

        ModelAndView modelAndView = new ModelAndView("/error");
        String errorMessage = (throwable != null ? throwable.toString() : "Unknown error");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }

	@ExceptionHandler(PlaceNotFound.class)
	public ModelAndView handlerPlaceNotFoundException() {
		
		ModelAndView mv = new ModelAndView("/error");
		
		mv.addObject("errorTitle", "Province not found");
		
		mv.addObject("errorDescription", "The province you are looking for is not registered with us right now!");
		
		mv.addObject("title", "Province Unavailable");
		
		return mv;
	}
}
