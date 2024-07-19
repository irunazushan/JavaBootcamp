package ex04;

public interface UserList {
    public void addUserToList(User user);
    public User getUserById(int id);
    public User getUserByIndex(int ind);
    public int getNumberOfUsers();

}
