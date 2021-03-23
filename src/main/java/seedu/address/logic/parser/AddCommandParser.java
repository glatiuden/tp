package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.SubjectList;
import seedu.address.model.subject.SubjectRate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                        PREFIX_GENDER, PREFIX_SUBJECT_NAME, PREFIX_RATE, PREFIX_EDUCATION_LEVEL,
                        PREFIX_YEAR, PREFIX_QUALIFICATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_GENDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        SubjectRate cost =
                ParserUtil.parseSubjectRate(argMultimap.getValue(PREFIX_COST).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Gender gender = ParserUtil.parseGender(argMultimap.getValue(PREFIX_GENDER).get());

        SubjectList subjectList = ParserUtil.parseSubjectList(
                argMultimap.getAllValues(PREFIX_SUBJECT_NAME),
                argMultimap.getAllValues(PREFIX_EDUCATION_LEVEL),
                argMultimap.getAllValues(PREFIX_RATE),
                argMultimap.getAllValues(PREFIX_YEAR),
                argMultimap.getAllValues(PREFIX_QUALIFICATION)
        );

        Person person = new Person(name, gender, phone, email, address, subjectList, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
