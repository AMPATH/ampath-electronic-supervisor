package com.ckb.labs.e_supervisor.validators;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameValidator implements FieldValidator, OnValid {

    private String validationError;
    private boolean isValid;

    @Override
    public boolean valid(@NonNull CharSequence charSequence) {
        //Name should at 3 characters
        if (charSequence.length() > 3) {
            setValid(true);
            return true;
        }
        this.setValidationError("Too short name(3 or more characters).");
        setValid(false);
        return false;
    }
}
