package jeff.command;

import jeff.storage.Storage;

import jeff.task.TaskList;

import jeff.ui.Ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FindCommand class is a Command that contains instructions
 * to run when user wants to find tasks with matching keywords.
 */
public class FindCommand extends Command {

    private String Description;

    /**
     * Constructor of MarkCommand that stores the input string to be checked into Description.
     *
     * @param body String to be found, input from user.
     */
    public FindCommand(String body) {
        this.Description = body;
    }

    /**
     * Check through all the Tasks in the TaskList and check if they
     * match with the keyword, if so set them up to print.
     *
     * @param tasks TaskList containing all the tasks.
     * @param ui Ui class for invoking user feedback.
     * @param storage Storage class used to save files.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.isEmpty()) {
            ui.showEmptyListFound();
        } else {
            StringBuilder taskStrings = new StringBuilder();
            int currCounter = 1;

            // Store string the user want to find in the list.
            String toBeFound = Description;
            Pattern pattern = Pattern.compile(toBeFound);

            for (int n = 0; n < tasks.size(); n++) {

                // Store description of this task.
                String toBeChecked = tasks.getAt(n).getDescription();

                // Check if the string to be found is found in the description to be checked.
                Matcher matcher = pattern.matcher(toBeChecked);
                boolean isFound = matcher.find();

                // If found, store to print it out, else skip.
                if (isFound) {
                    String currTaskString = currCounter + "." + tasks.getString(n) + "\n" ;
                    taskStrings.append(Ui.addPrefix(currTaskString));
                    currCounter++;
                }
            }
            if (taskStrings.length() == 0) {
                ui.showNoneFound(toBeFound);
            } else {
                ui.showFoundList(taskStrings.toString());
            }
        }
    }

    /**
     * Used to exit the Jeff program.
     *
     * @return false to keep running.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
