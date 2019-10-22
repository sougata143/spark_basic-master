package app.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {

	public static Map users = new HashMap<>();
    private static final AtomicInteger count = new AtomicInteger(0);
 
    public User findById(String id) {
        return (User) users.get(id);
    }
 
    public User add(String name, String email) {
        int currentId = count.incrementAndGet();
        User user = new User(currentId, name, email);
        users.put(String.valueOf(currentId), user);
        return user;
    }
 
    public User update(String id, String name, String email) {
 
        User user = (User) users.get(id);
        if (name != null) {
            user.setName(name);
        }
 
        if (email != null) {
            user.setEmail(email);
        }
        users.put(id, user);
 
        return user;
 
    }
 
 
    public void delete(String id) {
        users.remove(id);
    }
 
    public List findAll() {
        return new ArrayList<>(users.values());
    }
 
    public void UserService() {
    }

}
