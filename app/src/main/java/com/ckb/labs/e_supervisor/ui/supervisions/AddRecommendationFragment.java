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
import com.ckb.labs.e_supervisor.model.Recommendation;
import com.ckb.labs.e_supervisor.validators.NameValidator;
import com.ckb.labs.e_supervisor.validators.ValidatorUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.Objects;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AddRecommendationFragment extends Fragment {

    private static final String TAG = AddSupervisorFragment.class.getSimpleName();
    TextInputEditText recommendationEdt;
    TextInputEditText recommenderNameEdt;

    TextInputLayout recommendationLabel;
    TextInputLayout recommenderNameLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recommendation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recommendationEdt = requireView().findViewById(R.id.edt_recommendation);
        recommendationLabel = requireView().findViewById(R.id.til_recommendation);
        ValidatorUtils.validate(recommendationEdt, recommendationLabel, new NameValidator());

        recommenderNameEdt = requireView().findViewById(R.id.edt_recommender);
        recommenderNameLabel = requireView().findViewById(R.id.label_recommender);
        ValidatorUtils.validate(recommenderNameEdt, recommenderNameLabel, new NameValidator());

        MaterialButton closeBtn = requireView().findViewById(R.id.btn_close);
        closeBtn.setOnClickListener(v -> Navigation.findNavController(requireView()).popBackStack());

        MaterialButton createSupervisorButton = requireView().findViewById(R.id.btn_submit);
        createSupervisorButton.setOnClickListener(v -> createRecommendation());
    }

    private void createRecommendation() {
        Editable recommendationEditable = recommendationEdt.getEditableText();
        Editable recommenderNameEditable = recommenderNameEdt.getEditableText();

        if (Objects.isNull(recommendationEditable) || recommendationEditable.toString().isEmpty()
                || Objects.isNull(recommenderNameEditable) || recommenderNameEditable.toString().isEmpty()) {
            return;
        }

        Recommendation recommendation =  new Recommendation();
        recommendation.setCreatedAt(new Date());
        recommendation.setRecommenderName(recommenderNameEditable.toString());
        recommendation.setRecommendation(recommendationEditable.toString());

        Log.i(TAG, "create recommendation: " + recommendation);
        //TODO post recommendation
        Navigation.findNavController(requireView()).popBackStack();
    }
}