package ex04;

public class UsersArrayList implements UserList {
    private User[] usersList = new User[10];
    private int numberOfUsers = 0;


    @Override
    public void addUserToList(User user) {
        if (numberOfUsers == usersList.length) {
            refreshArray();
        }
        usersList[numberOfUsers] = user;
        ++numberOfUsers;
    }

    @Override
    public User getUserById(int id) {
        User result = null;
        for (int i = 0; i < numberOfUsers; ++i) {
            if (usersList[i].getIdentifier() == id) {
                result = usersList[i];
                break;
            }
        }
        if (result == null) {
            throw new UserNotFoundException();
        }
        return result;
    }

    @Override
    public User getUserByIndex(int ind) {
        if (ind > numberOfUsers) {
            throw new UserNotFoundException();
        }
         return usersList[ind];
    }

    @Override
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    private void refreshArray() {
        User[] updateList = new User[usersList.length + usersList.length / 2];
        for (int i = 0; i < usersList.length; ++i) {
            updateList[i] = usersList[i];
        }
        usersList = updateList;
    }

}