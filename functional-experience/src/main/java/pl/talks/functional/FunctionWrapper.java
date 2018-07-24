package pl.talks.functional;

import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.control.Either;
import io.vavr.control.Try;

public class FunctionWrapper {

    private UserService userService;
    private CheckedFunction1<User, User> createUserFunction = userService::createUser;
    private Function1<Long, User> findUserFunction = userService::findUser;

    public void createOrUpdateUser(User user) {
        CheckedFunction1.liftTry(createUserFunction)
                .apply(user)
                .toEither().
        Either<Throwable, User> createdUser = CheckedFunction1.liftTry((User user) -> {
            userService.createUser(user);
            return user;
        }).apply(user).toEither();
        System.out.println(createdUser);
    }

    public static void main(String[] args) {
        FunctionWrapper functionWrapper = new FunctionWrapper();
        User newUser = new User("Jarząbek Wacław");
        functionWrapper.createOrUpdateUser(newUser);
    }
}

interface UserService {

    // Bo po co używać wyjątków runtimowych, niech wszyscy wiedzą jakie problemy mogę stworzyc oraz ja nurkuje w szambie to niech inni tez dadzą tam nura!
    User createUser(User user) throws DealWithMeBussinessException;

    User findUser(Long id);
}

final class User {
    String name;

    public User(String name) {
        this.name = name;
    }
}

final class DealWithMeBussinessException extends Exception { }
final class UserNotFindException extends RuntimeException { }
final class RepositoryException extends RuntimeException { }



