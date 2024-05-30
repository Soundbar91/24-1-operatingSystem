package repository;

import java.util.Arrays;

public class CPU {

    private final int[] register;
    private static final int CONTEXT_START_INDEX = 45;
    private static final int CONTEXT_LENGTH = 5;

    public CPU() {
        this.register = new int[50];
        Arrays.fill(register, -1);
    }

    public void loadProgram(int[][] programCode, int[] context) {
        loadProgramCode(programCode);
        loadContext(context);
    }

    private void loadProgramCode(int[][] programCode) {
        int index = 0;
        for (int[] row : programCode) {
            for (int value : row) {
                register[index++] = value;
            }
        }
    }

    private void loadContext(int[] context) {
        int index = 0;
        for (int i = CONTEXT_START_INDEX; i < CONTEXT_START_INDEX + CONTEXT_LENGTH; i++) {
            register[i] = context[index++];
        }
    }

    public int[] performContextSwitching(int[][] programCode, int[] context) {
        if (register[CONTEXT_START_INDEX] == -1) {
            System.out.println("Initial process context switching: -1 -> " + (char) ('A' + context[0] / 15 - 1));
        } else if (register[CONTEXT_START_INDEX] == context[0]) {
            System.out.println("The same program is currently running.");
            return null;
        } else {
            char previousProgram = (char) ('A' + register[CONTEXT_START_INDEX] / 15 - 1);
            char currentProgram = (char) ('A' + context[0] / 15 - 1);
            System.out.println("Process context switching: " + previousProgram + " -> " + currentProgram);
        }
        int[] previousContext = Arrays.copyOfRange(register, CONTEXT_START_INDEX, CONTEXT_START_INDEX + CONTEXT_LENGTH);
        loadContext(context);
        loadProgramCode(programCode);
        return previousContext;
    }
}
