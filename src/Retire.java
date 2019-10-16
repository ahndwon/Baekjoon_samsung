import java.util.Arrays;
import java.util.Scanner;

// 어려웠던 점 -> 인덱스 관리를 확실하게 해야됨
// 1. 0번째를 더미로 넣어줘야 했고
// 2. N + 1 번쨰에 끝나는 일도 포함시켜줘야 했음 (구현의 차이)

public class Retire {
    static int N;
    static int max = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        sc.nextLine();

        int[] times = new int[N + 1];
        int[] pays = new int[N + 1];

        times[0] = 0;
        pays[0] = 0;
        for (int i = 1; i <= N; i++) {
            String[] line = sc.nextLine().split(" ");

            times[i] = Integer.parseInt(line[0]);
            pays[i] = Integer.parseInt(line[1]);
        }

        System.out.println(Arrays.toString(times));
        System.out.println(Arrays.toString(pays));


        recursive(1, 0,0, times, pays);

        System.out.println();
        System.out.println("max : " + max);
    }

    public static void recursive(int index, int beforeMoney, int money, int[]times, int[] pays) {
        System.out.println("index : " + index + ", money : " + money);

        if (index == N + 1) {
            System.out.println(money);
            System.out.println();
            max = Math.max(money, max);
        }

        if (index > N) {
            System.out.println(beforeMoney);
            System.out.println(money - beforeMoney);
            System.out.println();
            max = Math.max(money - beforeMoney, max);
            return;
        }

        for (int i = index; i <= N; i++) {
//            if (index + times[i] >= N - 1) {
//                max = Math.max(money - pays[beforeIndex], max);
//
//            }
            System.out.println("i : " + i + ", money : " + money);

            recursive(i + times[i], pays[i], money + pays[i], times, pays);
        }
    }
}
