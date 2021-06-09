public class User {
    private static String UserName;
    private static int userID;
    private static String userNick;

    public User() {

    }

    public User(String UserName, int userID, String userNick){
        this.userID=userID;
        this.UserName=UserName;
        this.userNick=userNick;
    }

    public static String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        User.userNick = userNick;
    }

    public static int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public static String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
