package com.ckb.labs.e_supervisor.ui.supervisions;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckb.labs.e_supervisor.ESupervisorConstants;
import com.ckb.labs.e_supervisor.R;
import com.ckb.labs.e_supervisor.adapters.SimpleItemAdapter;
import com.ckb.labs.e_supervisor.model.Finding;
import com.ckb.labs.e_supervisor.model.Program;
import com.ckb.labs.e_supervisor.model.Recommendation;
import com.ckb.labs.e_supervisor.model.Supervision;
import com.ckb.labs.e_supervisor.model.Supervisor;
import com.ckb.labs.e_supervisor.ui.viewmodels.SupervisionViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ActiveSupervisionFragment extends Fragment {

    private SupervisionViewModel viewModel;
    private Supervision currentSupervision;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.currentSupervision = getArguments().getParcelable(ESupervisorConstants.SUPERVISION);
        }
        this.viewModel = new ViewModelProvider(this).get(SupervisionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_active_supervision, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialTextView programTitleTv = requireView().findViewById(R.id.program_title);
        MaterialTextView programDescriptionTv = requireView().findViewById(R.id.program_desc);
        MaterialTextView programPurposeTv = requireView().findViewById(R.id.purpose);

        if (Objects.nonNull(currentSupervision)) {
            Program program = currentSupervision.getProgram();
            if (Objects.nonNull(program)) {
                programTitleTv.setText(program.getName());
                programDescriptionTv.setText(program.getDescription());
            }
            programPurposeTv.setText(String.format("Purpose -:%s", currentSupervision.getPurposeOfSupervision()));
        }

        //Findings
        RecyclerView findingsRv = requireView().findViewById(R.id.rv_findings);
        MaterialTextView noFindings = requireView().findViewById(R.id.no_findings);
        findingsRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        findingsRv.setHasFixedSize(true);
        findingsRv.setItemAnimator(new DefaultItemAnimator());
        SimpleItemAdapter findingAdapter = new SimpleItemAdapter();
        List<String> findings = new ArrayList<>();
        findingAdapter.setItems(findings);
        this.viewModel.getFindings(new Supervision()).observe(getViewLifecycleOwner(), findingList -> {
            for (Finding finding : findingList) {
                findings.add(finding.getFinding());
            }
            if (findings.size() == 0) {
                noFindings.setVisibility(View.VISIBLE);
            } else {
                noFindings.setVisibility(View.GONE);
            }
            findingAdapter.notifyDataSetChanged();
        });

        findingsRv.setAdapter(findingAdapter);

        //Recommendations
        RecyclerView recommendationRv = requireView().findViewById(R.id.rv_recommendations);
        MaterialTextView noRecommendations = requireView().findViewById(R.id.no_recommendations);
        recommendationRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        recommendationRv.setHasFixedSize(true);
        recommendationRv.setItemAnimator(new DefaultItemAnimator());
        SimpleItemAdapter recommendationAdapter = new SimpleItemAdapter();
        List<String> recommendations = new ArrayList<>();
        recommendationAdapter.setItems(recommendations);
        this.viewModel.getRecommendations(new Supervision()).observe(getViewLifecycleOwner(), recommendationList -> {
            for (Recommendation recommendation : recommendationList) {
                recommendations.add(recommendation.getRecommendation());
            }
            if (recommendations.size() > 0) {
                noRecommendations.setVisibility(View.GONE);
            } else {
                noRecommendations.setVisibility(View.VISIBLE);
            }
            recommendationAdapter.notifyDataSetChanged();
        });
        recommendationRv.setAdapter(recommendationAdapter);


        //Supervisors
        RecyclerView supervisorsRV = requireView().findViewById(R.id.rv_supervisors);
        MaterialTextView noSupervisors = requireView().findViewById(R.id.no_supervisors);
        supervisorsRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        supervisorsRV.setHasFixedSize(true);
        supervisorsRV.setItemAnimator(new DefaultItemAnimator());
        SimpleItemAdapter supervisorAdapter = new SimpleItemAdapter();
        List<String> supervisors = new ArrayList<>();
        supervisorAdapter.setItems(supervisors);
        this.viewModel.getSupervisors(currentSupervision).observe(getViewLifecycleOwner(), supervisorList -> {
            for (Supervisor supervisor : supervisorList) {
                supervisors.add(supervisor.getFirstName() + " " + supervisor.getSurname() + " "
                        + supervisor.getLastName() + " -: "
                        + supervisor.getDesignation() + "Org: " + supervisor.getOrganisation());
            }
            if (supervisors.size() > 0) {
                noSupervisors.setVisibility(View.GONE);
            } else {
                noSupervisors.setVisibility(View.VISIBLE);
            }
            supervisorAdapter.notifyDataSetChanged();
        });
        supervisorsRV.setAdapter(supervisorAdapter);

        //Add Findings
        MaterialButton addFindingBtn = requireView().findViewById(R.id.btn_add_finding);
        addFindingBtn.setOnClickListener(v -> {
            handleFindingSelection();
        });

        //Add recommendations
        MaterialButton addRecommendationBtn = requireView().findViewById(R.id.btn_add_recommendation);
        addRecommendationBtn.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_activeSupervisionFragment_to_addRecommendationFragment);
        });

        //Add supervisors
        MaterialButton addSupervisorsBtn = requireView().findViewById(R.id.btn_add_supervisor);
        addSupervisorsBtn.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.action_activeSupervisionFragment_to_addSupervisorFragment);
        });

    }

    private void handleFindingSelection() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());
        builder.setTitle("Select Findings");
        builder.setCancelable(false);

        ArrayList<Integer> findingIndexList = new ArrayList<>();
        List<Finding> findings = new ArrayList<>();
        this.viewModel.getFindings(new Supervision()).observe(getViewLifecycleOwner(), findings::addAll);

        CharSequence [] myFindings = {"Finding One", "Two", "Three"};
        for (Finding finding : findings) {
            //myFindings.add(finding.getFinding());
        }

        boolean[] selectedFinding = new boolean[myFindings.length];
        builder.setMultiChoiceItems(myFindings, selectedFinding, (dialogInterface, i, b) -> {
            // check condition
            if (b) {
                // when checkbox selected
                // Add position  in lang list
                findingIndexList.add(i);
                // Sort array list
                Collections.sort(findingIndexList);
            } else {
                // when checkbox unselected
                // Remove position from langList
                findingIndexList.remove(Integer.valueOf(i));
            }
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            for (int j = 0; j < findingIndexList.size(); j++) {
                // TODO
            }
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            // dismiss dialog
            dialogInterface.dismiss();
        });
        builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
            for (int j = 0; j < selectedFinding.length; j++) {
                // remove all selection
                selectedFinding[j] = false;
                // clear index list
                findingIndexList.clear();
            }
        });
        // show dialog
        builder.show();
    }
}