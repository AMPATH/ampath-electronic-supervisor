package com.ckb.labs.e_supervisor.ui.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ckb.labs.e_supervisor.model.Finding;
import com.ckb.labs.e_supervisor.model.Program;
import com.ckb.labs.e_supervisor.model.Recommendation;
import com.ckb.labs.e_supervisor.model.Supervision;
import com.ckb.labs.e_supervisor.model.Supervisor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SupervisionViewModel extends ViewModel {

    public LiveData<List<Program>> getPrograms() {
        MutableLiveData<List<Program>> programsMutable = new MutableLiveData<>();
        programsMutable.postValue(Arrays.asList(
                new Program("MCH Program", "This is the program description. A placeholder message for this example"),
                new Program("KESSES WKD", ""),
                new Program("Aboloi MCH D", "")
        ));
        return programsMutable;
    }

    public LiveData<List<Finding>> getFindings(Supervision supervision) {
        MutableLiveData<List<Finding>> findingsMutable = new MutableLiveData<>();

        findingsMutable.postValue(Arrays.asList(
                new Finding("Finding One", new Date()),
                new Finding("Finding two", new Date()),
                new Finding("Finding three", new Date())
        ));

        return findingsMutable;
    }

    public LiveData<List<Recommendation>> getRecommendations(Supervision supervision) {
        MutableLiveData<List<Recommendation>> recommendationsMutable = new MutableLiveData<>();

        recommendationsMutable.postValue(Arrays.asList(
                new Recommendation("This recommendation is based on this supervision for the MCH program areas","", new Date()),
                new Recommendation("This recommendation is based on this supervision for the MCH program areas", "", new Date())
        ));

        return recommendationsMutable;
    }

    public LiveData<List<Supervisor>> getSupervisors(Supervision currentSupervision) {
        MutableLiveData<List<Supervisor>> supervisorMutable = new MutableLiveData<>();

        supervisorMutable.postValue(Arrays.asList(
                new Supervisor("James", "Wangui", "Wafula", "AMPATH", "Nurse")
        ));

        return supervisorMutable;
    }
}