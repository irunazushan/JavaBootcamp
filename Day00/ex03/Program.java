import java.util.*;
class Program {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Map<String, Integer> statistic = new HashMap<>();
        int currentWeek = 0;

        String weekNumber = sc.nextLine();
        while (!(weekNumber.equals("42"))) {
            if (statistic.containsKey(weekNumber) || (extractWeekNum(weekNumber) - currentWeek != 1)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int rate = Arrays.stream(sc.nextLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .min()
                    .orElse(0);
            statistic.put(weekNumber, rate);
            currentWeek++;
            weekNumber = sc.nextLine();
        }
        drawGraph(statistic);
    }

    public static int extractWeekNum(String s) {
        if (s.substring(0, 5).equals("Week ")) {
            return Integer.parseInt(s.substring(5));
        }
        return 0;
    }

    public static void drawGraph(Map<String, Integer> statistic) {
        statistic.entrySet().forEach(entry -> {
            System.out.print(entry.getKey());
            for (int i = 0; i < entry.getValue(); ++i) {
                System.out.print("=");
            }
            System.out.println(">");
        });
    }
}