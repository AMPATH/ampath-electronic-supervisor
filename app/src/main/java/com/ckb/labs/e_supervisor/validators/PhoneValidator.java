package com.ckb.labs.e_supervisor.validators;

import android.util.Patterns;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhoneValidator implements FieldValidator {

    private String validationError;

    @Override
    public boolean valid(@NonNull CharSequence charSequence) {
        boolean isValid = (charSequence.length() > 0 && Patterns.PHONE.matcher(charSequence).matches());
        if (!isValid) {
            setValidationError("Invalid email address!");
        }
        return isValid;
    }
}
