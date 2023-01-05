import java.util.ArrayDeque;
import java.util.Queue;

public class RemoteCommandQueue {
    Queue<Command> commands;
    Command undoCommand;//record the previous command

    public RemoteCommandQueue() {
        commands = new ArrayDeque<>();
    }

    /**
     * only add command but not execute
     *
     * @param command
     */
    public void buttonPressed(Command command) {
        //todo: complete
        commands.add(command);
    }

    /**
     * execute the command in the queue by first-in-first-out principle
     * if there is no command in the queue display "no command"
     */
    public void commandExecute() {
        // todo: compelte
        if (commands.size() > 0){
            Command command = commands.poll();
            undoCommand = command;
            command.execute();
        } else {
            System.out.println("no command");
        }
    }

    /**
     * undo the previous command
     */
    public void commandUndo() {
        if (undoCommand != null)
            undoCommand.undo();
        else
            System.out.println("No previous command");
    }
}
