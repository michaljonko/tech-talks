package pl.coffeepower.functional.legacy;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import lombok.Value;

public final class WorklogService {

    private final Connection connection;

    public WorklogService() throws SQLException {
        connection = DriverManager.getConnection("");
    }

    public File generateReportForTask(Task task) {

        return null;
    }

    public Map<Worker, Pair<Task, Long>> getTeamLogs(Team team) {

        return Collections.emptyMap();
    }

    @Value
    public static final class Team {

        private String name;
        private Collection<Worker> workers;
    }

    @Value
    public static final class Worker {

        private String name;
        private Position position;

        public enum Position {
            DEVELOPER, TESTER, LEADER, LOAFER
        }
    }

    @Value
    public static final class Task {

        private String name;
    }

    @Value
    public static final class Worklog {

        private Date date;
        private Worker worker;
        private Task task;
        private long timeSpentInSeconds;
    }

    @Value
    public static final class Pair<T1, T2> {

        private T1 left;
        private T2 right;
    }
}
