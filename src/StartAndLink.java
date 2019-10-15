import java.util.ArrayList;
import java.util.Scanner;

// https://www.acmicpc.net/problem/14889
// 스타트와 링크
// 실수한 점: 1.visit 복사 한 것을 사용안하고 기존 visit 사용해버림
//         2. 문제를 잘못 이해햐여 쓸데 없이 더 고민 -> 정확한 이해 필요
// N : 짝수


public class StartAndLink {
    static int N;
    static int[][] map;

    static int diff = Integer.MAX_VALUE;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        int[] visit = new int[N];
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        dfs(visit, 0, 0);

        System.out.println(diff);
    }

    static int getPoint(int i, int j) {
        return map[i][j] + map[j][i];
    }


    static void dfs(int[] visit, int index, int count) {
        if (index >= N) {
            return;
        }

        int[] newVisit = new int[N];
        for (int i = 0; i < visit.length; i++) {
            newVisit[i] = visit[i];
        }


        if (count == N / 2) {
            int startSum = 0;
            int linkSum = 0;
            ArrayList<Integer> startList = new ArrayList<>();
            ArrayList<Integer> linkList = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                if (visit[i] == 0) startList.add(i);
                else linkList.add(i);
            }

            for (int i = 0; i < startList.size() - 1; i++) {
                for (int j = i + 1; j < startList.size(); j++) {
                    startSum += getPoint(startList.get(i), startList.get(j));
                }
            }

            for (int k = 0; k < linkList.size() - 1; k++) {
                for (int h = k + 1; h < linkList.size(); h++) {
                    linkSum += getPoint(linkList.get(k), linkList.get(h));

//
//                    System.out.println("linkSum : " + linkSum);
//                    System.out.println("startSum : " + startSum);
                }
            }

            diff = Math.min(diff, Math.abs(startSum - linkSum));


        }

        newVisit[index] = 1;
        dfs(newVisit, index + 1, count + 1);

        newVisit[index] = 0;
        dfs(newVisit, index + 1, count);
    }

}
