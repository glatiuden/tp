package seedu.address.model.tutor;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.tag.Filterable;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender implements Filterable {

    public static final String MESSAGE_CONSTRAINTS =
            "Genders should be male or female";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String personGender;

    /**
     * Constructs a {@code Name}.
     *
     * @param gender A valid gender.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        personGender = gender;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidGender(String s) {
        String uppercaseString = s.toUpperCase();
        return uppercaseString.equals("MALE") || uppercaseString.equals("FEMALE");
    }


    @Override
    public String toString() {
        return personGender;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && personGender.equals(((Gender) other).personGender)); // state check
    }

    @Override
    public int hashCode() {
        return personGender.hashCode();
    }

    @Override
    public boolean filter(String s) {
        return personGender.contains(s);
    }
}
