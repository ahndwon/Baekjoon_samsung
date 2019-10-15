import java.util.Scanner;

// 계속 막힌 이유 : 인덱스 설정, 조건 자꾸 실수함
// -> 꼼꼼하게 해야댐

public class Slope {

    static int N;
    static int L;

    static int[][] map;

    static int[][] slopeCheck;

    static int answer = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        L = sc.nextInt();
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                map[i][j] = sc.nextInt();

            }
        }

        for (int i = 0; i < N; i++) {
            slopeCheck = new int[N][N];
//            System.out.println("column : " + i);
            checkColumnRoute(0, i, map[0][i]);
//            System.out.println();
        }

        for (int i = 0; i < N; i++) {
            slopeCheck = new int[N][N];
            checkRowRoute(i, 0, map[i][0]);
        }


        System.out.println(answer);
    }

    static void checkColumnRoute(int row, int column, int beforeHeight) {

        if (row >= N) {
//            System.out.println("answer column : " + column);
            answer++;
            return;
        }

        int diff = Math.abs(map[row][column] - beforeHeight);
//        System.out.println("diff : " + diff);

        if (diff > 1) {
            return;
        }

//        System.out.println(row);

        if (diff == 1) {
            // diff : 1 -> 오른쪽이 낮 (그전꺼 검사)
            if (map[row][column] - beforeHeight < 0) {


//                System.out.println("-");

                if (row + L > N) return;

                int height = map[row][column];

                for (int i = row; i < row + L; i++) {
                    if (map[i][column] != height) return;
                    if (slopeCheck[i][column] == 1) return;
                }

                for (int i = row; i < row + L; i++) {
                    slopeCheck[i][column] = 1;
                }

                checkColumnRoute(row + L, column, map[row][column]);

            }
            // diff : -1 -> 오른쪽이 낮음 (그이 검사)
            else if (map[row][column] - beforeHeight > 0) {
//                System.out.println("+");

                if (row - L < 0) return;

                for (int i = row - 1; i >= row - L; i--) {
                    if (map[i][column] != beforeHeight) return;
                    if (slopeCheck[i][column] == 1) return;
                }

                for (int i = row - 1; i >= row - L; i--) {
                    slopeCheck[i][column] = 1;
                }


                checkColumnRoute(row + 1, column, map[row][column]);
            }

//            System.out.println("diff else");
        } else {
//            System.out.println("=");
            checkColumnRoute(row + 1, column, map[row][column]);
        }
    }

    static void checkRowRoute(int row, int column, int beforeHeight) {
//        System.out.println("c : " + column);
        if (column >= N) {
//            System.out.println("answer row : " + row);
            answer++;
            return;
        }

        int diff = Math.abs(map[row][column] - beforeHeight);
//        System.out.println("diff : " + diff);

        if (diff > 1) {
            return;
        }

        if (diff == 1) {
            // diff : 1 -> 오른쪽이 높음 (그전꺼 검사)
            if (map[row][column] - beforeHeight > 0) {
//                System.out.println("+");
                if (column - L < 0) return;

                for (int i = column - 1; i >= column - L; i--) {
                    if (map[row][i] != beforeHeight) return;
                    if (slopeCheck[row][i] == 1) return;
                }

                for (int i = column - 1; i >= column - L; i--) {
                    slopeCheck[row][i] = 1;
                }


                checkRowRoute(row, column + 1, map[row][column]);
            }
            // diff : -1 -> 오른쪽이 높음 (그전꺼 검사)
            else if (map[row][column] - beforeHeight < 0) {
//                System.out.println("-");
                if (column + L > N) return;

                int height = map[row][column];

                for (int i = column; i < column + L; i++) {
                    if (map[row][i] != height) return;
                    if (slopeCheck[row][i] == 1) return;
                }

                for (int i = column; i < column + L; i++) {
                    slopeCheck[row][i] = 1;
                }

                checkRowRoute(row, column + L, map[row][column]);

            }
        } else {
//            System.out.println("=");

            checkRowRoute(row, column + 1, map[row][column]);
        }
    }
}