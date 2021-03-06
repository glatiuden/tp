package seedu.address.model.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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

public class TutorFilterTest {
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
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorFilter(null,
                null, null, null, null, null, null, null, null, null));
    }

    @Test
    public void test_emptyFilter_identity() {
        TutorFilter tutorFilter = new TutorFilter();

        ObservableList<Tutor> typicalTutors = FXCollections.unmodifiableObservableList(
            FXCollections.observableArrayList(TypicalTutors.getTypicalPersons()));
        FilteredList<Tutor> filteredTypicalTutors = typicalTutors.filtered(tutorFilter);

        assertEquals(typicalTutors, filteredTypicalTutors);
    }

    @Test
    public void test_nameFilter_success() {
        Tutor amy = TypicalTutors.AMY;
        Tutor bob = TypicalTutors.BOB;

        nameFilters.add(new NameFilter(amy.getName().fullName));
        TutorFilter tutorFilter = new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters);

        assertTrue(tutorFilter.test(amy));
        assertFalse(tutorFilter.test(bob));
        assertFalse(tutorFilter.test(null));
    }

    @Test
    public void hasAny_personFilter_success() {
        Tutor amy = TypicalTutors.AMY;
        Tutor bob = TypicalTutors.BOB;

        nameFilters.add(new NameFilter(amy.getName().fullName));
        TutorFilter tutorFilterOne = new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters);

        nameFilters = new LinkedHashSet<Predicate<Name>>();
        nameFilters.add(new NameFilter(amy.getName().fullName));
        nameFilters.add(new NameFilter(bob.getName().fullName));
        TutorFilter tutorFilterTwo = new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters);

        assertTrue(tutorFilterTwo.hasAny(tutorFilterOne));
        assertFalse(tutorFilterTwo.hasAny(new TutorFilter()));
    }

    @Test
    public void hasAny_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorFilter().hasAny(null));
    }

    @Test
    public void add_personFilter_success() {
        TutorFilter tutorFilter = new TutorFilter();

        Tutor amy = TypicalTutors.AMY;
        Tutor bob = TypicalTutors.BOB;

        nameFilters.add(new NameFilter(amy.getName().fullName));
        tutorFilter.add(new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters));

        assertTrue(tutorFilter.test(amy));
        assertFalse(tutorFilter.test(bob));

        nameFilters.add(new NameFilter(bob.getName().fullName));
        tutorFilter.add(new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters));

        assertTrue(tutorFilter.test(amy));
        assertTrue(tutorFilter.test(bob));
    }

    @Test
    public void add_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorFilter().add(null));
    }

    @Test
    public void remove_personFilter_success() {
        Tutor amy = TypicalTutors.AMY;
        Tutor bob = TypicalTutors.BOB;

        nameFilters.add(new NameFilter(amy.getName().fullName));
        nameFilters.add(new NameFilter(bob.getName().fullName));
        TutorFilter tutorFilter = new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters);

        assertTrue(tutorFilter.test(amy));
        assertTrue(tutorFilter.test(bob));

        nameFilters = new LinkedHashSet<Predicate<Name>>();
        nameFilters.add(new NameFilter(bob.getName().fullName));
        tutorFilter.remove(new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters));

        assertTrue(tutorFilter.test(amy));
        assertFalse(tutorFilter.test(bob));

        nameFilters = new LinkedHashSet<Predicate<Name>>();
        nameFilters.add(new NameFilter(amy.getName().fullName));
        tutorFilter.remove(new TutorFilter(nameFilters,
                genderFilters, phoneFilters, emailFilters, addressFilters,
                subjectNameFilters, subjectLevelFilters, subjectRateFilters,
                subjectExperienceFilters, subjectQualificationFilters));

        assertTrue(tutorFilter.test(amy));
        assertTrue(tutorFilter.test(bob));
    }

    @Test
    public void remove_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorFilter().remove(null));
    }
}
