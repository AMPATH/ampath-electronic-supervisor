package com.ckb.labs.e_supervisor.ui.supervisions;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ckb.labs.e_supervisor.ESupervisorConstants;
import com.ckb.labs.e_supervisor.R;
import com.ckb.labs.e_supervisor.model.Program;
import com.ckb.labs.e_supervisor.model.Supervision;
import com.ckb.labs.e_supervisor.ui.viewmodels.SupervisionViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CheckInFragment extends Fragment {

    private static final String TAG = CheckInFragment.class.getSimpleName();

    private HashMap<String, Program> programHashMap;
    private SupervisionViewModel viewModel;
    private TextInputEditText purposeEdt;
    private AppCompatAutoCompleteTextView programAutoComplete;
    private Program selectedProgram;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        this.programHashMap = new HashMap<>();
        this.viewModel = new ViewModelProvider(this).get(SupervisionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        programAutoComplete = requireView().findViewById(R.id.tv_program_dropdown);
        purposeEdt = requireView().findViewById(R.id.edt_purpose);

        ArrayList<String> programs = new ArrayList<>();
        this.viewModel.getPrograms().observe(getViewLifecycleOwner(), programList -> {
            for (Program program : programList) {
                programs.add(program.getName());
                this.programHashMap.put(program.getName(), program);
            }
            programAutoComplete.setAdapter(createSpinnerDropDownAdapter(requireContext(), programs));
        });

        programAutoComplete.setOnItemClickListener((k, v, w, id) -> selectedProgram = programHashMap.get(programs.get(w)));

        MaterialButton closeBtn = requireView().findViewById(R.id.btn_close);
        closeBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());

        MaterialButton startSupervision = requireView().findViewById(R.id.btn_start_supervision);
        startSupervision.setOnClickListener(b -> {
            if (validate()) {
                Supervision supervision = new Supervision();
                supervision.setProgram(selectedProgram);
                supervision.setStatus(Supervision.Status.ACTIVE);
                supervision.setStartTimestamp(new Date());
                supervision.setPurposeOfSupervision(purposeEdt.getEditableText().toString());

                Log.i(TAG, "Supervision started: " + supervision);
                Snackbar.make(requireView(), "Supervision started successful!", 10).show();

                //Supervision
                Bundle supervisionBundle = new Bundle();
                supervisionBundle.putParcelable(ESupervisorConstants.SUPERVISION, supervision);
                Navigation.findNavController(requireView()).navigate(R.id.action_checkInFragment_to_activeSupervisionFragment, supervisionBundle);
            }
        });

    }

    private boolean validate() {
        TextInputLayout programLabel = requireView().findViewById(R.id.til_program);
        TextInputLayout purposeLabel = requireView().findViewById(R.id.til_purpose);

        if (Objects.isNull(purposeEdt.getEditableText()) || purposeEdt.getEditableText().toString().isEmpty()) {
            purposeLabel.setError("Please jot down the purpose of this supervision!");
            if (selectedProgram == null) {
                programLabel.setError("Select program/area to supervise");
                return false;
            } else {
                programLabel.setError("");
            }
            return false;
        } else {
            purposeLabel.setError("");
        }
        return true;
    }

    private ArrayAdapter<String> createSpinnerDropDownAdapter(Context context, ArrayList<String> stringArrayList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, stringArrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        return dataAdapter;
    }
}