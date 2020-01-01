package com.perpetualmaze.store.jdbc;

import com.perpetualmaze.model.Maze;
import com.perpetualmaze.model.MazeId;
import com.perpetualmaze.store.MazeStore;

import javax.sql.DataSource;
import javax.ws.rs.WebApplicationException;
import java.sql.*;
import java.util.Optional;

public class JdbcMazeStore implements MazeStore {
    private final DataSource dataSource;

    public JdbcMazeStore(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Optional<Maze> get(MazeId id) {
        String sql = "SELECT * FROM mazes WHERE level = ? AND width = ? AND height = ?";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id.getLevel());
            ps.setInt(2, id.getWidth());
            ps.setInt(3, id.getHeight());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Maze maze = new Maze()
                            .setId(id)
                            .setSerialized(rs.getString("serialized"));
                    return Optional.of(maze);
                }
            }
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to retrieve maze: " + sqlException.getMessage(), sqlException);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Maze maze) {
        String sql = "INSERT INTO mazes (level, width, height, serialized) VALUES (?, ?, ?, ?)";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maze.getId().getLevel());
            ps.setInt(2, maze.getId().getWidth());
            ps.setInt(3, maze.getId().getHeight());
            ps.setString(4, maze.getSerialized());
            return ps.executeUpdate() == 1;
        } catch (SQLIntegrityConstraintViolationException constraintViolation) {
            // this maze already exists in the db, ignore exception and return false
            return false;
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to save maze: " + sqlException.getMessage(), sqlException);
        }
    }

    @Override
    public boolean delete(MazeId id) {
        String sql = "DELETE FROM mazes WHERE level = ? AND width = ? AND height = ?";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id.getLevel());
            ps.setInt(2, id.getWidth());
            ps.setInt(3, id.getHeight());
            return ps.executeUpdate() == 1;
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to delete maze: " + sqlException.getMessage(), sqlException);
        }
    }
}
