package seedu.address.logic.commands.favouritecommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.tutorcommands.EditCommand.EditTutorDescriptor;
import static seedu.address.logic.commands.tutorcommands.EditCommand.createEditedTutor;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.tutorcommands.EditCommand;
import seedu.address.model.Model;
import seedu.address.model.tutor.Tutor;
import seedu.address.model.tutor.ViewTutorPredicate;

/**
 * Removes favourite from a Tutor
 */
public class UnfavouriteCommand extends Command {

    public static final String COMMAND_WORD = "unfavourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " ID";

    public static final String MESSAGE_SUCCESS = "Unfavourite tutor: %s";

    public static final String MESSAGE_INVALID_INDEX = "Invalid index %d";

    private static final String MESSAGE_AlREADY_UNFAVOURITE = "Tutor is already not a favourite";

    private final Index targetIndex;

    private final EditTutorDescriptor editTutorDescriptor;

    /**
     * @param targetIndex of Tutor to be unfavourite
     * @param editTutorDescriptor with an unfavourite descriptor
     */
    public UnfavouriteCommand(Index targetIndex, EditCommand.EditTutorDescriptor editTutorDescriptor) {
        this.targetIndex = targetIndex;
        this.editTutorDescriptor = editTutorDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutor> tutorList = model.getFilteredTutorList();

        ArrayList<Tutor> tutorPredicateList = new ArrayList<>(tutorList);

        if (targetIndex.getZeroBased() >= tutorList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, targetIndex.getZeroBased()));
        }

        Tutor tutor = tutorList.get(targetIndex.getZeroBased());
        if (!tutor.isFavourite()) {
            throw new CommandException(MESSAGE_AlREADY_UNFAVOURITE);
        }

        Tutor editedTutor = createEditedTutor(tutor, editTutorDescriptor);

        model.setTutor(tutor, editedTutor);
        model.updateFilteredTutorList(new ViewTutorPredicate(tutorPredicateList));

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutor.getName()));
    }
}
