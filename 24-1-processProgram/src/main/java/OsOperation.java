import repository.AuxiliaryStorage;
import repository.CPU;
import repository.MainMemory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OsOperation {
    private final AuxiliaryStorage auxStorage;
    private final MainMemory mainMemory;
    private final CPU cpu;
    private static final int NUM_PROGRAMS = 3;
    private static final int EXIT_OPTION = 4;

    public OsOperation() {
        System.out.println("시스템 부팅");
        auxStorage = new AuxiliaryStorage();
        mainMemory = new MainMemory();
        mainMemory.setKernelCode(auxStorage.getKernel());
        cpu = new CPU();
    }

    public static void main(String[] args) {
        OsOperation os = new OsOperation();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("1. A, 2. B, 3. C, 4. 종료");
                System.out.print("실행하실 프로그램의 번호를 입력하세요 : ");

                int programNumber = Integer.parseInt(br.readLine());
                if (programNumber == EXIT_OPTION) {
                    System.out.println("시스템을 종료합니다.");
                    break;
                } else if (programNumber >= 1 && programNumber <= NUM_PROGRAMS) {
                    os.executeProgram(programNumber);
                } else {
                    System.out.println("잘못된 입력입니다. 다시 시도하세요.");
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("입력 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void executeProgram(int programNumber) {
        int[][] program = auxStorage.getProgram(programNumber);

        int memoryIndex = programNumber + 2;
        mainMemory.setMappingTable(program, memoryIndex);
        mainMemory.setProgram(program, memoryIndex);
        mainMemory.createContext(memoryIndex);

        int[] context = mainMemory.getContext(memoryIndex);
        int[] preContext = cpu.performContextSwitching(program, context);
        if (preContext != null) {
            mainMemory.saveContext(preContext);
            mainMemory.threadContextSwitching(memoryIndex);
        }
    }
}
