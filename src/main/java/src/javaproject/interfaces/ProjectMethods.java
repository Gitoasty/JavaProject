package src.javaproject.interfaces;

import org.slf4j.Logger;
import src.javaproject.classes.ProjectAssignment;
import src.javaproject.classes.ProjectDraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface ProjectMethods {

    static List<ProjectAssignment> getProjects(Logger logger, String project) {
        List<ProjectAssignment> outList = new ArrayList<>();
        String query;

        if (project.isEmpty()) {
            query = "SELECT id, name, estimatedTime, tasks, workers FROM projects";
        } else {
            query = STR."SELECT id, name, estimatedTime, tasks, workers FROM projects WHERE id LIKE \"\{project}%\"";
        }

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {

                if (results.getString("workers").isEmpty()) {
                    ProjectDraft tempProject = ProjectDraft.builder()
                            .id(results.getInt("id"))
                            .name(results.getString("name"))
                            .estimatedTimeSetter(results.getInt("estimatedTime"))
                            .taskSetter(Set.of(results.getString("tasks").split(",")))
                            .build();
                    outList.add(new ProjectAssignment(tempProject));
                } else {
                    ProjectAssignment tempProject = ProjectAssignment.builder()
                            .id(results.getInt("id"))
                            .name(results.getString("name"))
                            .workers(Arrays.asList(results.getString("workers").split(",")))
                            .estimatedTimeSetter(results.getInt("estimatedTime"))
                            .taskSetter(Set.of(results.getString("tasks").split(",")))
                            .build();
                    outList.add(tempProject);
                }
            }

            return outList;
        } catch (SQLException _) {
            logger.error("Problem with getting projects");
        }
        return new ArrayList<>();
    }

    static ProjectAssignment getProject(Logger logger, String project) {
        String query = "SELECT * FROM projects WHERE name = ?";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, project);
            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                if (results.getString("workers").isEmpty()) {
                    ProjectDraft tempProject = ProjectDraft.builder()
                            .id(results.getInt("id"))
                            .name(results.getString("name"))
                            .estimatedTimeSetter(results.getInt("estimatedTime"))
                            .taskSetter(Set.of(results.getString("tasks").split(",")))
                            .build();
                    return new ProjectAssignment(tempProject);
                } else {
                    return ProjectAssignment.builder()
                            .id(results.getInt("id"))
                            .name(results.getString("name"))
                            .workers(Arrays.asList(results.getString("workers").split(",")))
                            .estimatedTimeSetter(results.getInt("estimatedTime"))
                            .taskSetter(Set.of(results.getString("tasks").split(",")))
                            .build();
                }
            }
        } catch (SQLException _) {
            logger.error("Problem with getting projects");
        }
        return null;
    }

    static Map<String, List<ProjectAssignment>> getProjectsForWorker(Logger logger, String worker) {
        Map<String, List<ProjectAssignment>> outMap = new HashMap<>();
        outMap.put(worker, new ArrayList<>());
        String query = "SELECT id, name, estimatedTime, tasks, workers FROM projects";

        try (Connection conn = DatabaseUtilities.getConnection(logger);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet results = stmt.executeQuery();

            while (results.next()) {
                String helper = results.getString("workers");
                List<String> helperList = Arrays.asList(helper.split(","));
                if (!helper.isEmpty() && helperList.contains(worker)) {
                    ProjectAssignment tempProject = ProjectAssignment.builder()
                            .id(results.getInt("id"))
                            .name(results.getString("name"))
                            .workers(helperList)
                            .estimatedTimeSetter(results.getInt("estimatedTime"))
                            .taskSetter(Set.of(results.getString("tasks").split(",")))
                            .build();
                    outMap.get(worker).add(tempProject);
                }
            }

            return outMap;
        } catch (SQLException _) {
            logger.error("Problem with getting projects");
        }

        return null;
    }
}
