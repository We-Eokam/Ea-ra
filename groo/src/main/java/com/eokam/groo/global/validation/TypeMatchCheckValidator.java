package com.eokam.groo.global.validation;

import java.lang.reflect.Field;

import com.eokam.groo.global.constant.ActivityType;
import com.eokam.groo.global.constant.SavingType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TypeMatchCheckValidator implements ConstraintValidator<TypeMatchCheck, Object> {

	private String message;
	private String savingType;
	private String activityType;

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean flag = true;
		ActivityType targetActivityType = getActivityType(value, activityType);
		SavingType targetSavingType = getSavingType(value, savingType);
		if (!targetSavingType.equals(targetActivityType.getSavingType())){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message)
				.addPropertyNode(activityType)
				.addConstraintViolation();
			flag = false;
		}
		return flag;
	}

	@Override
	public void initialize(TypeMatchCheck constraintAnnotation) {
		message = constraintAnnotation.message();
		savingType = constraintAnnotation.savingType();
		activityType = constraintAnnotation.activityType();
	}

	private ActivityType getActivityType(Object object, String filedName) {
		Class<?> objectClass = object.getClass();
		Field field;
		try {
			field = objectClass.getDeclaredField(filedName);
			field.setAccessible(true);
			Object target = field.get(object);
			if (!(target instanceof ActivityType)) {
				throw new ClassCastException("casting exception");
			}
			return (ActivityType) target;
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private SavingType getSavingType(Object object, String filedName) {
		Class<?> objectClass = object.getClass();
		Field field;
		try {
			field = objectClass.getDeclaredField(filedName);
			field.setAccessible(true);
			Object target = field.get(object);
			if (!(target instanceof SavingType)) {
				throw new ClassCastException("casting exception");
			}
			return (SavingType) target;
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
