package seedu.address.logic.commands.filtercommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalGrades.getTypicalGradeBook;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderTracker;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleTracker;
import static seedu.address.testutil.TypicalTutors.getTypicalTutorBook;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.filter.NameFilter;
import seedu.address.model.filter.TutorFilter;
import seedu.address.model.subject.SubjectExperience;
import seedu.address.model.subject.SubjectLevel;
import seedu.address.model.subject.SubjectName;
import seedu.address.model.subject.SubjectQualification;
import seedu.address.model.subject.SubjectRate;
import seedu.address.model.tutor.Address;
import seedu.address.model.tutor.Email;
import seedu.address.model.tutor.Gender;
import seedu.address.model.tutor.Name;
import seedu.address.model.tutor.Phone;
import seedu.address.model.tutor.Tutor;
import seedu.address.testutil.TypicalTutors;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddPersonFilterCommand.
 */
public class AddTutorFilterCommandTest {
    private Model model;
    private Model expectedModel;

    private Set<Predicate<Name>> nameFilters;
    private Set<Predicate<Gender>> genderFilters;
    private Set<Predicate<Phone>> phoneFilters;
    private Set<Predicate<Email>> emailFilters;
    private Set<Predicate<Address>> addressFilters;

    private Set<Predicate<SubjectName>> subjectNameFilters;
    private Set<Predicate<SubjectLevel>> subjectLevelFilters;
    private Set<Predicate<SubjectRate>> subjectRateFilters;
    private Set<Predicate<SubjectExperience>> subjectExperienceFilters;
    private Set<Predicate<SubjectQualification>> subjectQualificationFilters;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTutorBook(), new UserPrefs(),
                getTypicalAppointmentBook(), new BudgetBook(), getTypicalGradeBook(),
                getTypicalScheduleTracker(), getTypicalReminderTracker());

        expectedModel = new ModelManager(getTypicalTutorBook(), new UserPrefs(),
                getTypicalAppointmentBook(), new BudgetBook(), getTypicalGradeBook(),
                getTypicalScheduleTracker(), getTypicalReminderTracker());

        this.nameFilters = new LinkedHashSet<>();
        this.genderFilters = new LinkedHashSet<>();
        this.phoneFilters = new LinkedHashSet<>();
        this.emailFilters = new LinkedHashSet<>();
        this.addressFilters = new LinkedHashSet<>();

        this.subjectNameFilters = new LinkedHashSet<>();
        this.subjectLevelFilters = new LinkedHashSet<>();
        this.subjectRateFilters = new LinkedHashSet<>();
        this.subjectExperienceFilters = new LinkedHashSet<>();
        this.subjectQualificationFilters = new LinkedHashSet<>();
    }

    @Test
    public void execute_allFieldsSpecified_success() {
        Tutor alice = TypicalTutors.ALICE;

        nameFilters.add(new NameFilter(alice.getName().fullName));
        TutorFilter tutorFilter = new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters);

        AddPersonFilterCommand addPersonFilterCommand = new AddPersonFilterCommand(tutorFilter);

        expectedModel.addTutorFilter(tutorFilter);
        String expectedMessage = String.format(AddPersonFilterCommand.MESSAGE_SUCCESS, tutorFilter);

        assertCommandSuccess(addPersonFilterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Tutor alice = TypicalTutors.ALICE;

        nameFilters.add(new NameFilter(alice.getName().fullName));
        TutorFilter tutorFilter = new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters);

        AddPersonFilterCommand addPersonFilterCommand = new AddPersonFilterCommand(tutorFilter);

        expectedModel.addTutorFilter(tutorFilter);
        String expectedMessage = String.format(AddPersonFilterCommand.MESSAGE_SUCCESS, tutorFilter);

        assertCommandSuccess(addPersonFilterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        TutorFilter tutorFilter = new TutorFilter();

        AddPersonFilterCommand addPersonFilterCommand = new AddPersonFilterCommand(tutorFilter);

        String expectedMessage = String.format(AddPersonFilterCommand.MESSAGE_SUCCESS, tutorFilter);

        assertCommandSuccess(addPersonFilterCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonFilter_success() {
        Tutor alice = TypicalTutors.ALICE;

        nameFilters.add(new NameFilter(alice.getName().fullName));
        TutorFilter tutorFilter = new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters);

        AddPersonFilterCommand addPersonFilterCommand = new AddPersonFilterCommand(tutorFilter);

        expectedModel.addTutorFilter(tutorFilter);
        String expectedMessage = String.format(AddPersonFilterCommand.MESSAGE_SUCCESS, tutorFilter);
        assertCommandSuccess(addPersonFilterCommand, model, expectedMessage, expectedModel);

        expectedMessage = AddPersonFilterCommand.MESSAGE_DUPLICATE;
        assertCommandFailure(addPersonFilterCommand, model, expectedMessage);
    }
}
