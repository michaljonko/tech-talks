package pl.talks.functional.either;

import io.vavr.CheckedFunction1;
import io.vavr.Function1;

public class LegacyErrorHandling {

    private final ExternalUserService userService;
    private Function1<String, String> getLastVisitedPageWithRuntime;
    private CheckedFunction1<String, String> getLastVisitedPageWithChecked;

    public LegacyErrorHandling(ExternalUserService userService) {
        this.userService = userService;
        getLastVisitedPageWithRuntime = (login) -> userService.getLastVisitedPageWithRuntime(login);
        getLastVisitedPageWithChecked = (login) -> userService.getLastVisitedPageWithChecked(login);
    }

    public void whenWeDoNotCareAboutErrorsButOnlyAboutResultsRuntime() {
        String userLogin = "someLogin";
        String startingPage = Function1.lift(getLastVisitedPageWithRuntime)
                .apply(userLogin)
                .getOrElse(returnDefaultPage());
    }

    public void whenWeDoNotCareAboutErrorsButOnlyAboutResultsChecked() {
        String userLogin = "someLogin";
        String startingPage = CheckedFunction1.lift(getLastVisitedPageWithChecked)
                .apply(userLogin)
                .getOrElse(returnDefaultPage());
    }

    private String returnDefaultPage() {
        return "default page";
    }



    interface ExternalUserService {
        String getLastVisitedPageWithRuntime(String login) throws NoResultFoundRuntimeException;
        String getLastVisitedPageWithChecked(String login) throws NoResultFoundCheckedException;
    }

    private static class NoResultFoundRuntimeException extends RuntimeException {
    }

    private static class NoResultFoundCheckedException extends Exception {
    }
}
