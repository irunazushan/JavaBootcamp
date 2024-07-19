import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] students = new String[10];
        String[] timetable = new String[10];
        String[] attendance = new String[2700];

        int numOfStudents = 0;
        int numOfSClasses = 0;
        int numOfSAttendance = 0;

        int operationStage = 0;
        while (operationStage < 3) {
            if (operationStage == 0) {
                numOfStudents = setListOfStudents(students, sc);
                operationStage++;
            } else if (operationStage == 1) {
                numOfSClasses = populateTimetable(timetable, sc);
                operationStage++;
            } else if (operationStage == 2) {
                numOfSAttendance = setListOfAttendance(attendance, sc);
                operationStage++;
            }
        }

        displayTimetable(Arrays.copyOf(students, numOfStudents), Arrays.copyOf(timetable, numOfSClasses), Arrays.copyOf(attendance, numOfSAttendance));
    }

    public static int setListOfStudents(String[] students, Scanner sc) {
        String student = sc.nextLine();
        int numOfStudents = 0;
        while (!student.equals(".") && numOfStudents < 2700) {
            if (student.length() > 2700) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
            students[numOfStudents++] = student;
            student = sc.nextLine();
        }

        return numOfStudents;
    }

    public static int populateTimetable(String[] timetable, Scanner sc) {
        String theClass = sc.nextLine();
        int numOfClasses = 0;
        while (!theClass.equals(".") && numOfClasses < 10) {
            if ((Character.getNumericValue(theClass.charAt(0)) < 1 || Character.getNumericValue(theClass.charAt(0)) > 6) || !isCorrectWeekDay(theClass)) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
            timetable[numOfClasses++] = theClass;
            theClass = sc.nextLine();
        }

        return numOfClasses;
    }

    private static final String[] daysOfWeek = {"MO", "TU", "WE", "TH", "FR", "SA", "SU"};

    private static boolean isCorrectWeekDay(String theClass) {
        String dayName = theClass.substring(2);
        return doesArrContainEl(daysOfWeek, dayName);
    }

    private static boolean doesArrContainEl(String[] strArr, String str) {
        for (String el : strArr) {
            if (el.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static int returnIndexOfEl(String[] arr, String str) {
        int ind = 0;
        for (String el : arr) {
            if (el.equals(str)) {
                break;
            }
            ind++;
        }
        return ind;
    }

    public static int setListOfAttendance(String[] attendance, Scanner sc) {
        String theAttendance = sc.nextLine();
        int numOfAttendance = 0;
        while (!theAttendance.equals(".") && numOfAttendance < 10) {
            if (isValidAttendance(theAttendance)) {
                attendance[numOfAttendance++] = theAttendance;
            } else {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
            theAttendance = sc.nextLine();
        }

        return numOfAttendance;
    }

    private static boolean isValidAttendance(String attendance) {
        String[] parts = attendance.split(" ");
        if (parts.length != 4) {
            return false;
        }
        String name = parts[0];
        String time = parts[1];
        String date = parts[2];
        String status = parts[3];
        if (name.length() > 10 || name.contains(" ")) {
            return false;
        }
        try {
            int timeInt = Integer.parseInt(time);
            int dateInt = Integer.parseInt(date);
            if (timeInt < 1 || timeInt > 6 || dateInt < 1 || dateInt > 30) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        if (!status.equals("HERE") && !status.equals("NOT_HERE")) {
            return false;
        }
        return true;
    }

    public static void displayTimetable(String[] students, String[] timetable, String[] attendance) {
        String[] fullTimetable = new String[45];
        int totalClasses = displayCalendar(timetable, fullTimetable);
        displayStudents(students, attendance, fullTimetable, totalClasses);
    }

    private static int displayCalendar(String[] timetable, String[] fullTimetable) {
        int days = 1;
        int currDayOfWeek = 2;
        int totalClasses = 0;
        System.out.printf("%10s", " ");
        while (days < 31) {
            for (int i = 0; i < timetable.length; ++i) {
                char time = timetable[i].charAt(0);
                String dayOfWeek = timetable[i].substring(2);
                if (returnIndexOfEl(daysOfWeek, dayOfWeek) + 1 < currDayOfWeek) continue;
                else {
                    while (returnIndexOfEl(daysOfWeek, dayOfWeek) + 1 > currDayOfWeek) {
                        currDayOfWeek++;
                        days++;
                    }
                }
                System.out.printf("%c:00 %s %2d|", time, dayOfWeek, days);
                fullTimetable[totalClasses++] = time + " " + days;
            }
            if (currDayOfWeek < 7) {
                days += 8 - currDayOfWeek;
            }
            currDayOfWeek = 1;
        }
        System.out.println();
        return totalClasses;
    }

    private static void displayStudents(String[] students, String[] attendance, String[] fullTimetable, int totalClasses) {
        for (String student : students) {
            if (student == null) continue;
            System.out.printf("%-10s", student);
            for (int i = 0; i < totalClasses; ++i) {
                String status = " ";
                for (String record : attendance) {
                    if (record == null) continue;
                    String[] parts = record.split(" ");
                    String name = parts[0];
                    String time = parts[1];
                    String date = parts[2];
                    String attStatus = parts[3];

                    if (name.equals(student) && fullTimetable[i].equals((time + " " + date))) {
                        status = attStatus.equals("HERE") ? "1" : "-1";
                        break;
                    }
                }
                System.out.printf(" %9s|", status);
            }
            System.out.println();
        }
    }
}
