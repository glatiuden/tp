package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDateTime;
import seedu.address.model.appointment.DateViewPredicate;
import seedu.address.model.filter.PersonFilter;
import seedu.address.model.grade.Grade;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final AppointmentBook appointmentBook;
    private final GradeBook gradeBook;

    private final UserPrefs userPrefs;

    private final PersonFilter personFilter;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Appointment> filteredAppointment;
    private final FilteredList<Grade> filteredGrades;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlyAppointmentBook appointmentBook, ReadOnlyGradeBook gradeBook) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.appointmentBook = new AppointmentBook(appointmentBook);
        this.gradeBook = new GradeBook(gradeBook);

        this.userPrefs = new UserPrefs(userPrefs);

        this.personFilter = new PersonFilter();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAppointment = new FilteredList<>(this.appointmentBook.getAppointmentList());
        filteredGrades = new FilteredList<>(this.gradeBook.getGradeList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new AppointmentBook(), new GradeBook());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getAppointmentBookFilePath() {
        return userPrefs.getAppointmentBookFilePath();
    }

    @Override
    public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        userPrefs.setAppointmentBookFilePath(appointmentBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== AppointmentBook=============================================================================

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return appointmentBook;
    }

    public void setAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
        this.appointmentBook.resetData(appointmentBook);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedAppointmentBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return filteredAppointment;
    }

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== AppointmentList ============================================================================

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointment.setPredicate(predicate);
    }

    /**
     * Checks if Appointment exists in appointment list.
     *
     * @param appointment Appointment to check
     * @return True if appointment is already in appointment list
     */
    @Override
    public boolean hasAppointment(Appointment appointment) {
        return appointmentBook.hasAppointment(appointment);
    }

    /**
     * @param appointment Appointment to add (appointment must not already exist)
     */
    @Override
    public void addAppointment(Appointment appointment) {
        appointmentBook.addAppointment(appointment);
    }

    /**
     * Removes appointment from appointment list.
     *
     * @param appointment Appointment to remove must be present
     */
    @Override
    public void removeAppointment(Appointment appointment) {
        appointmentBook.removeAppointment(appointment);
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        appointmentBook.setAppointment(target, editedAppointment);
    }

    /**
     * Method that removes appointment based on index
     *
     * @param indexToRemove
     */
    @Override
    public void removeAppointmentIndex(int indexToRemove) {
        appointmentBook.removeAppointment(indexToRemove);
    }

    /**
     * Checks if {@code AppointmentDateTime} exists in the appointment list.
     *
     * @param appointmentDateTime Appointment DateTime to be checked
     * @return true if Appointment DateTime exists in the appointment list
     */
    @Override
    public boolean hasAppointmentDateTime(AppointmentDateTime appointmentDateTime) {
        return !filteredAppointment.filtered(new DateViewPredicate(appointmentDateTime)).isEmpty();
    }

    //=========== GradeList ============================================================================
    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredGradeList(Predicate<Grade> predicate) {
        requireNonNull(predicate);
        filteredGrades.setPredicate(predicate);
    }

    public ReadOnlyGradeBook getGradeBook() {
        return gradeBook;

    }

    public void setGradeBook(ReadOnlyGradeBook readOnlyGradeBook) {
        this.gradeBook.resetData(readOnlyGradeBook);
    }

    /**
     * @return File path of Grade Book data file
     */
    public Path getGradeBookFilePath() {
        return userPrefs.getGradeBookFilePath();
    }

    /**
     * Sets grade book file path.
     * @param gradeBookFilePath To be supplied by user
     */
    public void setGradeBookFilePath(Path gradeBookFilePath) {
        requireNonNull(gradeBookFilePath);
        userPrefs.setGradeBookFilePath(gradeBookFilePath);
    }

    /**
     * Returns true if a grade with the same identity as {@code grade} exists in the grade book.
     */
    public boolean hasGrade(Grade grade) {
        requireNonNull(grade);
        return gradeBook.hasGrade(grade);
    }

    /**
     * Deletes the given grade.
     * The grade must exist in the grade book.
     */
    public void deleteGrade(Grade target) {
        gradeBook.removeGrade(target);
    }

    /**
     * Adds the given grade.
     * {@code grade} must not already exist in the grade book.
     */
    public void addGrade(Grade grade) {
        gradeBook.addGrade(grade);
    }

    /**
     * Replaces the given grade {@code target} with {@code editedGrade}.
     * {@code target} must exist in the grade book.
     * The grade identity of {@code editedGrade} must not be the same as another existing grade in the grade book.
     */
    public void setGrade(Grade target, Grade editedGrade) {
        requireAllNonNull(target, editedGrade);
        gradeBook.setGrade(target, editedGrade);
    }

    /**
     * Method that removes grade based on index
     *
     * @param indexToRemove
     */
    @Override
    public void removeGradeIndex(int indexToRemove) {
        gradeBook.removeGrade(indexToRemove);
    }

    /**
     * Returns an unmodifiable view of the filtered grade list
     */
    public ObservableList<Grade> getFilteredGradeList() {
        return filteredGrades;
    }

    //=========== PersonFilter =====================================================================
    @Override
    public boolean hasPersonFilter(PersonFilter personFilter) {
        return this.personFilter.has(personFilter);
    }

    @Override
    public void addPersonFilter(PersonFilter personFilter) {
        this.personFilter.add(personFilter);
        this.updateFilteredPersonList(this.personFilter);
    }

    @Override
    public void removePersonFilter(PersonFilter personFilter) {
        this.personFilter.remove(personFilter);
        this.updateFilteredPersonList(this.personFilter);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
