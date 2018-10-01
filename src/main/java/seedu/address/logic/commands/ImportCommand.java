//@@author chantca95
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "i";

    public static final String MESSAGE_SUCCESS = "Import successful!";
    public static final String MESSAGE_DUPLICATE = " Duplicate contacts not imported.";
    public static final String MESSAGE_FAIL = "No contacts imported.";
    public static final String MESSAGE_USAGE = "Please use the file browser to choose a csv file.";
    public static final String MESSAGE_INVALID_FIELD = " Contacts with invalid fields not imported.";
    public static final String MESSAGE_NAMELESS_CONTACT = " Contacts without at least a name field not imported.";


    private final ArrayList<Person> personsToAdd;
    private final boolean hasContactWithInvalidField;
    private final boolean hasContactWithoutName;

    public ImportCommand(ArrayList<Person> imports, boolean hasContactWithInvalidField, boolean hasContactWithoutName) {
        requireNonNull(imports);
        personsToAdd = imports;
        this.hasContactWithInvalidField = hasContactWithInvalidField;
        this.hasContactWithoutName = hasContactWithoutName;
    }
    
    public CommandResult execute(Model model, CommandHistory history) {
        
        boolean hasAddedNew = false;
        boolean hasDuplicate = false;
        
        for(int i = 0; i < personsToAdd.size(); i++) {
            if (model.hasPerson(personsToAdd.get(i))) {
                hasDuplicate = true;
                continue;
            }
            model.addPerson(personsToAdd.get(i));
            hasAddedNew = true;
        }
        if (hasAddedNew) {
            model.commitAddressBook();
        }
        
        //Format the final message acccordingly
        StringBuilder finalReportMessage = new StringBuilder();
        if (hasAddedNew) { 
            finalReportMessage.append(MESSAGE_SUCCESS);
        } else {
            finalReportMessage.append(MESSAGE_FAIL);
        }
        if (hasDuplicate) {
            finalReportMessage.append(MESSAGE_DUPLICATE);
        }
        if (hasContactWithInvalidField) {
            finalReportMessage.append(MESSAGE_INVALID_FIELD);
        }
        if (hasContactWithoutName) {
            finalReportMessage.append(MESSAGE_NAMELESS_CONTACT);
        }
        return new CommandResult(finalReportMessage.toString());
    }
}