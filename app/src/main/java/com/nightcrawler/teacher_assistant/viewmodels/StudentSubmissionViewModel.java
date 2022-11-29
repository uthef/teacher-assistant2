package com.nightcrawler.teacher_assistant.viewmodels;

import android.app.Application;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Student;
import com.nightcrawler.teacher_assistant.database.Subgroup;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StudentSubmissionViewModel extends AndroidViewModel {
    public StudentSubmissionViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public Student validateFields(long groupId,
                                  TextInputLayout firstNameLayout,
                                  TextInputLayout middleNameLayout,
                                  TextInputLayout lastNameLayout,
                                  RadioGroup radioGroup,
                                  TextInputLayout variantInput,
                                  TextInputLayout kpInput) {

        String firstName = Objects.requireNonNull(firstNameLayout.getEditText()).getText().toString(),
                middleName = Objects.requireNonNull(middleNameLayout.getEditText()).getText().toString(),
                lastName = Objects.requireNonNull(lastNameLayout.getEditText()).getText().toString();

        String reqField = getApplication().getString(R.string.required_filed);

        int variant = 0, kp = 0;
        RadioButton firstChild = (RadioButton) radioGroup.getChildAt(0);
        Subgroup subgroup = firstChild.isChecked() ? Subgroup.First : Subgroup.Second;
        boolean valid = true;

        if (firstName.trim().length() == 0) {
            valid = false;
            firstNameLayout.setError(reqField);
        }
        else firstNameLayout.setError(null);

        if (middleName.trim().length() == 0) {
            valid = false;
            middleNameLayout.setError(reqField);
        }
        else middleNameLayout.setError(null);

        if (lastName.trim().length() == 0) {
            valid = false;
            lastNameLayout.setError(reqField);
        }
        else lastNameLayout.setError(null);

        if (variantInput.getEditText() == null || variantInput.getEditText().length() == 0) {
            valid = false;
            variantInput.setError(reqField);
        }
        else variantInput.setError(null);

        if (kpInput.getEditText() == null || kpInput.getEditText().length() == 0) {
            valid = false;
            kpInput.setError(reqField);
        }
        else kpInput.setError(null);

        if (valid)
            return new Student(groupId, firstName, middleName, lastName, subgroup, variant, kp);

        return null;

    }
}
