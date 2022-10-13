package com.ckb.labs.e_supervisor.validators;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NonNullTextValidator implements FieldValidator, OnValid {
    private String validationError;
    private boolean isValid;
    private int minimalNumOfChars;

    public NonNullTextValidator(int minimalNumOfChars) {
        this.minimalNumOfChars = minimalNumOfChars;
    }

    @Override
    public boolean valid(@NonNull CharSequence charSequence) {
        if (charSequence.length() > minimalNumOfChars) {
            setValid(true);
            return true;
        }
        this.setValidationError("Too short text(" + minimalNumOfChars + " or more characters).");
        setValid(false);
        return false;
    }
}
