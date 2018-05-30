package pl.coffeepower.functional.legacy;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import lombok.Value;

public final class WorklogService {

    private static final String JDBC_URL = "jdbc:mysql://host/worklog";
    private static final String REPORTS_ROOT = "z:\\reports\\";
    private final Connection connection;

    /*
    1. Raport z dnia dla pracownika
    2. Raport zespolu zapisany do pliku
     */

    public WorklogService() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL);
    }

    public long getWorkerSummaryTimeForADay(Worker worker, Date date) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT ....");
        long summary;
        ...
        return summary;
    }

    public File saveTeamWorklog(Team team, Date from, Date to) {
        File outputFile =
                new File(REPORTS_ROOT + team.getName() + "-" + from.toString() + "-" + to.toString() + ".csv");
        PrintWriter writer = new PrintWriter(outputFile);
        ...
        return outputFile;
    }

    @Value
    public static final class Team {

        private String name;
        private Collection<Worker> workers;
    }

    @Value
    public static final class Worker {

        private String name;
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
}
