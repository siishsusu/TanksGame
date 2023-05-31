import java.util.Random;

public class test {
    public static void main(String[] args) {
        int rows = 50;
        int cols = 50;

        Random random = new Random();

        int[][] map = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                map[row][col] = random.nextInt(5);
            }
        }

        // Ensure that 1's are not surrounded by 1's
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                if (map[row][col] == 1 && isSurroundedByOnes(map, row, col)) {
                    map[row][col] = 0;
                }
            }
        }

        // Print the generated map
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                System.out.print(map[row][col] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isSurroundedByOnes(int[][] map, int row, int col) {
        return map[row - 1][col] == 1 && map[row + 1][col] == 1 && map[row][col - 1] == 1 && map[row][col + 1] == 1;
    }
}
