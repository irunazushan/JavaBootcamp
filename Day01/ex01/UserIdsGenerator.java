package ex01;

public class UserIdsGenerator {
    private static int id = 0;
    private static UserIdsGenerator instance = null;

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }

        return instance;
    }

    public int generateId() {
        return ++id;
    }
}
