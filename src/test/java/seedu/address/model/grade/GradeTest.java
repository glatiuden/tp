package seedu.address.model.grade;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.GradeBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_NAME_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADED_ITEM_SCIENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_SCIENCE;
import static seedu.address.testutil.TypicalGrades.MATHS_GRADE;
import static seedu.address.testutil.TypicalGrades.SCIENCE_GRADE;

public class GradeTest {

    @Test
    public void isSameGrade() {
        // same object -> returns true
        assertTrue(MATHS_GRADE.isSameGrade(MATHS_GRADE));

        // null -> returns false
        assertFalse(MATHS_GRADE.isSameGrade(null));

        // same subject name and graded item, different grade -> returns true
        Grade editedMaths = new GradeBuilder(MATHS_GRADE)
                .withGrade(VALID_GRADE_SCIENCE).build();
        assertTrue(MATHS_GRADE.isSameGrade(editedMaths));

        // different subject name, all other attributes same -> returns false
        editedMaths = new GradeBuilder(MATHS_GRADE)
                .withSubject(VALID_SUBJECT_NAME_SCIENCE).build();
        assertFalse(MATHS_GRADE.isSameGrade(editedMaths));

        // different graded item, all other attributes same -> returns false
        editedMaths = new GradeBuilder(MATHS_GRADE)
                .withGradedItem(VALID_GRADED_ITEM_SCIENCE).build();
        assertFalse(MATHS_GRADE.isSameGrade(editedMaths));

        // subject differs in case, all other attributes same -> returns true
        Grade editedScience = new GradeBuilder(SCIENCE_GRADE)
                .withSubject(VALID_SUBJECT_NAME_SCIENCE.toLowerCase()).build();
        assertTrue(SCIENCE_GRADE.isSameGrade(editedScience));

        // graded item differs in case, all other attributes same -> returns true
        editedScience = new GradeBuilder(SCIENCE_GRADE)
                .withGradedItem(VALID_GRADED_ITEM_SCIENCE.toLowerCase()).build();
        assertTrue(SCIENCE_GRADE.isSameGrade(editedScience));

        // subject has trailing spaces, all other attributes same -> returns false
        String subjectWithTrailingSpaces = VALID_SUBJECT_NAME_SCIENCE + " ";
        editedScience = new GradeBuilder(SCIENCE_GRADE)
                .withSubject(subjectWithTrailingSpaces).build();
        assertFalse(SCIENCE_GRADE.isSameGrade(editedScience));

        // graded item has trailing spaces, all other attributes same -> returns false
        String itemWithTrailingSpaces = VALID_GRADED_ITEM_SCIENCE + " ";
        editedScience = new GradeBuilder(SCIENCE_GRADE)
                .withGradedItem(itemWithTrailingSpaces).build();
        assertFalse(SCIENCE_GRADE.isSameGrade(editedScience));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Grade mathsCopy = new GradeBuilder(MATHS_GRADE).build();
        assertTrue(MATHS_GRADE.equals(mathsCopy));

        // same object -> returns true
        assertTrue(MATHS_GRADE.equals(MATHS_GRADE));

        // null -> returns false
        assertFalse(MATHS_GRADE.equals(null));

        // different type -> returns false
        assertFalse(MATHS_GRADE.equals(5));

        // different person -> returns false
        assertFalse(MATHS_GRADE.equals(SCIENCE_GRADE));

        // different subject name -> returns false
        Grade editedMaths = new GradeBuilder(MATHS_GRADE)
                .withSubject(VALID_SUBJECT_NAME_SCIENCE).build();
        assertFalse(MATHS_GRADE.equals(editedMaths));

        // different graded item -> returns false
        editedMaths = new GradeBuilder(MATHS_GRADE)
                .withGradedItem(VALID_GRADED_ITEM_SCIENCE).build();
        assertFalse(MATHS_GRADE.equals(editedMaths));

        // different grade -> returns false
        editedMaths = new GradeBuilder(MATHS_GRADE)
                .withGrade(VALID_GRADE_SCIENCE).build();
        assertFalse(MATHS_GRADE.equals(editedMaths));
    }
}
