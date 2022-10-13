package com.ckb.labs.e_supervisor.ui.supervisions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckb.labs.e_supervisor.R;
import com.ckb.labs.e_supervisor.model.Supervisor;
import com.ckb.labs.e_supervisor.validators.NameValidator;
import com.ckb.labs.e_supervisor.validators.ValidatorUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddSupervisorFragment extends Fragment {

    private static final String TAG = AddSupervisorFragment.class.getSimpleName();
    TextInputEditText firstNameEdt;
    TextInputEditText surnameEdt;
    TextInputEditText lastNameEdt;
    TextInputEditText designationEdt;
    TextInputEditText organisationEdt;

    TextInputLayout firstNameLabel;
    TextInputLayout surnameLabel;
    TextInputLayout lastNameLabel;
    TextInputLayout designationLabel;
    TextInputLayout organisationLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_supervisor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firstNameEdt = requireView().findViewById(R.id.edt_first_name);
        firstNameLabel = requireView().findViewById(R.id.label_first_name);
        ValidatorUtils.validate(firstNameEdt, firstNameLabel, new NameValidator());

        surnameEdt = requireView().findViewById(R.id.edt_surname);
        surnameLabel = requireView().findViewById(R.id.label_surname);
        ValidatorUtils.validate(surnameEdt, surnameLabel, new NameValidator());

        lastNameEdt = requireView().findViewById(R.id.edt_last_name);
        lastNameLabel = requireView().findViewById(R.id.label_last_name);
        ValidatorUtils.validate(lastNameEdt, lastNameLabel, new NameValidator());

        designationEdt = requireView().findViewById(R.id.edt_designation);
        designationLabel = requireView().findViewById(R.id.label_designation);
        ValidatorUtils.validate(designationEdt, designationLabel, new NameValidator());

        organisationEdt = requireView().findViewById(R.id.edt_organisation);
        organisationLabel = requireView().findViewById(R.id.label_organisation);
        ValidatorUtils.validate(organisationEdt, organisationLabel, new NameValidator());


        MaterialButton closeBtn = requireView().findViewById(R.id.btn_close);
        closeBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());

        MaterialButton createSupervisorButton = requireView().findViewById(R.id.btn_add_supervisor);
        createSupervisorButton.setOnClickListener(v -> createSupervisor());

    }

    private void createSupervisor() {
        Editable firstNameEditable = firstNameEdt.getEditableText();
        Editable surnameNameEditable = firstNameEdt.getEditableText();
        Editable lastNameEditable = firstNameEdt.getEditableText();
        Editable designationEditable = firstNameEdt.getEditableText();
        Editable organisationEditable = firstNameEdt.getEditableText();

        if (Objects.isNull(firstNameEditable) || firstNameEditable.toString().isEmpty()
                || Objects.isNull(surnameNameEditable) || surnameNameEditable.toString().isEmpty()
                || Objects.isNull(lastNameEditable) || lastNameEditable.toString().isEmpty()
                || Objects.isNull(designationEditable) || designationEditable.toString().isEmpty()
                || Objects.isNull(organisationEditable) || organisationEditable.toString().isEmpty()) {
            return;
        }
        Supervisor supervisor = new Supervisor();
        supervisor.setFirstName(firstNameEditable.toString());
        supervisor.setSurname(surnameNameEditable.toString());
        supervisor.setLastName(lastNameEditable.toString());
        supervisor.setOrganisation(organisationEditable.toString());
        supervisor.setDesignation(designationEditable.toString());

        Log.i(TAG, "createSupervisor: " + supervisor);
        //TODO post supervisor
        Navigation.findNavController(requireView()).popBackStack();
    }
}
