package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameOrCourseContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_COURSE);

        List<String> nameKeywords = argMultimap.getValue(PREFIX_NAME)
                .map(value -> Arrays.asList(value.split("\\s+")))
                .orElse(new ArrayList<>());

        List<String> courseKeywords = argMultimap.getValue(PREFIX_COURSE)
                .map(value -> Arrays.asList(value.split("\\s+")))
                .orElse(new ArrayList<>());

        if (nameKeywords.isEmpty() && courseKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new NameOrCourseContainsKeywordsPredicate(nameKeywords, courseKeywords));
    }

}
