package com.test.demo.common.startUp;
import com.test.demo.User.Users;
import com.test.demo.User.service.UserService;
import com.test.demo.common.thread.IntervalSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component
@Order(value = 1)
public class SheduleCommandLineRunner implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    IntervalSubject intervalSubject;
    @Override
    public void run(String... args) throws Exception {
        userService.createSubAccount(createAdminWhenFirstStartBackend());
        intervalSubject.firstRegisterOberver();
    }
    public Users createAdminWhenFirstStartBackend(){
        Users users = new Users();
        users.setAccount("admin");
        users.setAuthority(0);
        users.setPassword("123456");
        return users;
    }
}
