package com.ckb.labs.e_supervisor.validators;

import androidx.annotation.NonNull;

public interface FieldValidator {

    /**
     * Validates the charSequence
     * @param charSequence charSequence to be validate
     * @return True/false if valid or not
     */
    boolean valid(@NonNull CharSequence charSequence);

    /**
     * Gets the validation error
     * @return {@link String}
     */
    String getValidationError();
}
