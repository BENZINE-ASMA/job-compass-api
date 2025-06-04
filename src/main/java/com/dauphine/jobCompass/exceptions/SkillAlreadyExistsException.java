package com.dauphine.jobCompass.exceptions;

public class SkillAlreadyExistsException extends RuntimeException {
    public SkillAlreadyExistsException(String skillName) {
        super("Une compétence avec le nom '" + skillName + "' existe déjà");
    }
}
