package kr.or.ddit.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;


import kr.or.ddit.vo.MemberVO;

public class ValidationUtils {
	private static Validator validator;
	
	static {
		String messageBaseName= "kr.or.ddit.msgs.errorMessage";
		ValidatorFactory factory = Validation.byDefaultProvider()
				.configure()
				.messageInterpolator(
						new ResourceBundleMessageInterpolator(
								new PlatformResourceBundleLocator(messageBaseName)
								)
						)
						.buildValidatorFactory();
		
		
		validator = factory.getValidator();
	}
	
	public static <T> Boolean validate(T target, Map<String, List<String>> errors ,Class<?>...groups) {
		
		boolean valid = true;
		// 결과 에러 페이지
		Set<ConstraintViolation<T>> ConstraintViolations = validator.validate(target, groups);
		
		valid = ConstraintViolations.isEmpty();
		
		if(!valid) {
			ConstraintViolations.stream()
								.forEach(single->{
									String propertyName = single.getPropertyPath().toString(); // (키) 타입이 string 이기때문에
									String errorMessage = single.getMessage(); // (값)
									List<String> errorMessages = Optional.ofNullable(errors.get(propertyName))
																		 .orElse(new ArrayList<>());
									errorMessages.add(errorMessage);
									errors.put(propertyName, errorMessages);
								});
		}
		
		return valid;
	}
}
