package repository;

import java.util.Arrays;
import java.util.Random;

public class MainMemory {
    private static final int EMPTY_CELL = -1;
    private static final int NUM_THREADS = 3;
    private static final int NUM_OFFSETS = 15;
    private static final int THREAD_DATA_START_INDEX = 15;
    private static final int NUM_THREADS_INDEX = 16;

    private final int[][] memory;
    private final boolean[] mappingTable;
    private final boolean[] context;

    public MainMemory() {
        memory = new int[6][20];
        mappingTable = new boolean[NUM_THREADS];
        context = new boolean[NUM_THREADS];
        initializeMemory();
    }

    private void initializeMemory() {
        for (int[] row : memory) {
            Arrays.fill(row, EMPTY_CELL);
        }
    }

    public void setKernelCode(int[][] kernel) {
        int index = 0;
        for (int[] row : kernel) {
            for (int value : row) {
                memory[2][index++] = value;
            }
        }
        printMemoryContent(2, "Kernel code and data loaded");
    }

    public void setProgram(int[][] program, int num) {
        int index = 0;
        for (int[] row : program) {
            for (int value : row) {
                memory[0][memory[num][index++]] = value;
            }
        }
        printMemoryContent(0, "Program code loaded");
    }

    public void setMappingTable(int[][] program, int num) {
        if (mappingTable[num - NUM_THREADS]) return;
        boolean[] offsetVisited = new boolean[NUM_OFFSETS];
        Random random = new Random();

        for (int[] row : program) {
            for (int value : row) {
                while (true) {
                    int offset = random.nextInt(NUM_OFFSETS);
                    if (!offsetVisited[offset]) {
                        offsetVisited[offset] = true;
                        memory[num][value % NUM_OFFSETS] = offset;
                        break;
                    }
                }
            }
        }
        printMemoryContent(num, "Mapping table created");
        mappingTable[num - NUM_THREADS] = true;
    }

    public void createContext(int num) {
        if (context[num - NUM_THREADS]) return;
        int offset = (num - 2) * NUM_OFFSETS;

        for (int i = THREAD_DATA_START_INDEX; i < memory[num].length; i++) {
            memory[num][i] = offset++;
        }

        System.out.println("PCB created [" + memory[num][THREAD_DATA_START_INDEX] + "]");
        System.out.print("TCB created [ ");
        for (int i = THREAD_DATA_START_INDEX + 1; i < memory[num].length; i++) {
            System.out.print(memory[num][i] + " ");
        }
        System.out.println("]\n");
        context[num - NUM_THREADS] = true;
    }

    public int[] getContext(int num) {
        int[] threadContext = new int[NUM_OFFSETS];

        int index = 0;
        for (int i = THREAD_DATA_START_INDEX; i < memory[num].length; i++) {
            threadContext[index++] = memory[num][i];
        }

        return threadContext;
    }

    public void saveContext(int[] context) {
        if (context[0] == EMPTY_CELL) return;
        int offset = context[0] % NUM_OFFSETS + 2;

        int index = 0;
        for (int i = THREAD_DATA_START_INDEX; i < memory[offset].length; i++) {
            memory[offset][i] = context[index++];
        }
    }

    public void threadContextSwitching(int num) {
        System.out.println("Program running");

        int prevThread = memory[num][NUM_THREADS_INDEX];

        for (int i = 0; i < NUM_THREADS; i++) {
            while (true) {
                int offset = (int) (Math.random() * (memory[num].length - THREAD_DATA_START_INDEX) + THREAD_DATA_START_INDEX);
                if (prevThread != memory[num][offset]) {
                    System.out.println("Context switching: " + prevThread + " -> " + memory[num][offset]);
                    prevThread = memory[num][offset];
                    break;
                }
            }
        }
    }

    private void printMemoryContent(int index, String message) {
        System.out.print(message + " [ ");
        int currentIndex = 0;
        while (memory[index][currentIndex] != EMPTY_CELL) {
            System.out.print(memory[index][currentIndex++] + " ");
        }
        System.out.println("]\n");
    }
}
