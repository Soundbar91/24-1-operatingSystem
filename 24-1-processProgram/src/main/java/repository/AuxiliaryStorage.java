package repository;

import java.util.Arrays;

public class AuxiliaryStorage {
    private final int[][] storage;
    private static final int PROGRAM_SIZE = 3;
    private static final int PROGRAM_LENGTH = 5;
    private static final int KERNEL_INDEX = 9;

    public AuxiliaryStorage() {
        this.storage = new int[10][5];
        setProgram('A', 0);
        setProgram('B', 15);
        setProgram('C', 30);
        setKernel(45);
    }

    private void setProgram(char programName, int startValue) {
        int rowIndex = (programName - 'A') * PROGRAM_SIZE;

        for (int i = 0; i < PROGRAM_SIZE; i++) {
            for (int j = 0; j < PROGRAM_LENGTH; j++) {
                storage[rowIndex + i][j] = startValue++;
            }
        }
    }

    private void setKernel(int startValue) {
        for (int j = 0; j < PROGRAM_LENGTH; j++) {
            storage[KERNEL_INDEX][j] = startValue++;
        }
    }

    public int[][] getKernel() {
        return new int[][]{Arrays.copyOf(storage[KERNEL_INDEX], PROGRAM_LENGTH)};
    }

    public int[][] getProgram(int num) {
        int startRow = (num - 1) * PROGRAM_SIZE;
        return Arrays.copyOfRange(storage, startRow, startRow + PROGRAM_SIZE);
    }
}
