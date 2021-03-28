package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListFavouriteCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.appointmentcommands.AddAppointmentCommand;
import seedu.address.logic.commands.appointmentcommands.DeleteAppointmentCommand;
import seedu.address.logic.commands.appointmentcommands.EditAppointmentCommand;
import seedu.address.logic.commands.appointmentcommands.FindAppointmentCommand;
import seedu.address.logic.commands.appointmentcommands.ListAppointmentCommand;
import seedu.address.logic.commands.appointmentcommands.ViewAppointmentCommand;
import seedu.address.logic.commands.budgetcommands.AddBudgetCommand;
import seedu.address.logic.commands.budgetcommands.DeleteBudgetCommand;
import seedu.address.logic.commands.budgetcommands.EditBudgetCommand;
import seedu.address.logic.commands.budgetcommands.ViewBudgetCommand;
import seedu.address.logic.commands.filtercommands.AddPersonFilterCommand;
import seedu.address.logic.commands.filtercommands.DeletePersonFilterCommand;
import seedu.address.logic.commands.gradecommands.AddGradeCommand;
import seedu.address.logic.commands.gradecommands.DeleteGradeCommand;
import seedu.address.logic.commands.gradecommands.EditGradeCommand;
import seedu.address.logic.commands.gradecommands.ListGradeCommand;
import seedu.address.logic.commands.eventcommands.ViewTimeTableCommand;
import seedu.address.logic.commands.schedulecommands.AddScheduleCommand;
import seedu.address.logic.commands.schedulecommands.DeleteScheduleCommand;
import seedu.address.logic.commands.schedulecommands.EditScheduleCommand;
import seedu.address.logic.commands.schedulecommands.ListScheduleCommand;
import seedu.address.logic.parser.appointmentparser.AddAppointmentCommandParser;
import seedu.address.logic.parser.appointmentparser.DeleteAppointmentCommandParser;
import seedu.address.logic.parser.appointmentparser.EditAppointmentCommandParser;
import seedu.address.logic.parser.appointmentparser.FindAppointmentCommandParser;
import seedu.address.logic.parser.appointmentparser.ViewAppointmentCommandParser;
import seedu.address.logic.parser.budgetparser.AddBudgetCommandParser;
import seedu.address.logic.parser.budgetparser.DeleteBudgetCommandParser;
import seedu.address.logic.parser.budgetparser.EditBudgetCommandParser;
import seedu.address.logic.parser.budgetparser.ViewBudgetCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.filterparser.AddPersonFilterCommandParser;
import seedu.address.logic.parser.filterparser.DeletePersonFilterCommandParser;
import seedu.address.logic.parser.gradeparser.AddGradeCommandParser;
import seedu.address.logic.parser.gradeparser.DeleteGradeCommandParser;
import seedu.address.logic.parser.gradeparser.EditGradeCommandParser;
import seedu.address.logic.parser.scheduleparser.AddScheduleCommandParser;
import seedu.address.logic.parser.scheduleparser.DeleteScheduleCommandParser;
import seedu.address.logic.parser.scheduleparser.EditScheduleCommandParser;

/**
 * Parses user input.
 */
public class TutorTrackerParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case FavouriteCommand.COMMAND_WORD:
            return new FavouriteCommandParser().parse(arguments);

        case UnfavouriteCommand.COMMAND_WORD:
            return new UnfavouriteCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListFavouriteCommand.COMMAND_WORD:
            return new ListFavouriteCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        /* Appointment Commands */
        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case EditAppointmentCommand.COMMAND_WORD:
            return new EditAppointmentCommandParser().parse(arguments);

        case DeleteAppointmentCommand.COMMAND_WORD:
            return new DeleteAppointmentCommandParser().parse(arguments);

        case FindAppointmentCommand.COMMAND_WORD:
            return new FindAppointmentCommandParser().parse(arguments);

        case ViewAppointmentCommand.COMMAND_WORD:
            return new ViewAppointmentCommandParser().parse(arguments);

        case ListAppointmentCommand.COMMAND_WORD:
            return new ListAppointmentCommand();

        /* Grade Commands */
        case AddGradeCommand.COMMAND_WORD:
            return new AddGradeCommandParser().parse(arguments);

        case EditGradeCommand.COMMAND_WORD:
            return new EditGradeCommandParser().parse(arguments);

        case DeleteGradeCommand.COMMAND_WORD:
            return new DeleteGradeCommandParser().parse(arguments);

        case ListGradeCommand.COMMAND_WORD:
            return new ListGradeCommand();

        case AddPersonFilterCommand.COMMAND_WORD:
            return new AddPersonFilterCommandParser().parse(arguments);

        case DeletePersonFilterCommand.COMMAND_WORD:
            return new DeletePersonFilterCommandParser().parse(arguments);

        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);

        case EditBudgetCommand.COMMAND_WORD:
            return new EditBudgetCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);

        case ViewBudgetCommand.COMMAND_WORD:
            return new ViewBudgetCommandParser().parse(arguments);

        /* Schedule Commands */
        case AddScheduleCommand.COMMAND_WORD:
            return new AddScheduleCommandParser().parse(arguments);

        case EditScheduleCommand.COMMAND_WORD:
            return new EditScheduleCommandParser().parse(arguments);

        case DeleteScheduleCommand.COMMAND_WORD:
            return new DeleteScheduleCommandParser().parse(arguments);

        case ListScheduleCommand.COMMAND_WORD:
            return new ListScheduleCommand();

        case ListScheduleCommand.COMMAND_WORD:
            return new ListScheduleCommand();

        case ViewTimeTableCommand.COMMAND_WORD:
            return new ViewTimeTableCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
