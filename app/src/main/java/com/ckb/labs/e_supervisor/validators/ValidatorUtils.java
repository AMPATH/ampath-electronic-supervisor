package com.ckb.labs.e_supervisor.validators;

import android.text.Editable;
import android.text.TextWatcher;

import com.ckb.labs.e_supervisor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ValidatorUtils {

    public static void validate(TextInputEditText inputEditText, TextInputLayout inputLayout, FieldValidator validator) {
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (validator.valid(charSequence)) {
                    inputLayout.setError("");
                    inputLayout.setErrorIconDrawable(R.drawable.ic_baseline_check_circle_24);
                } else {
                    inputLayout.setError(validator.getValidationError());
                    inputLayout.setErrorIconDrawable(R.drawable.ic_baseline_error_24);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
