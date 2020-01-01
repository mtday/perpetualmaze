package com.perpetualmaze.store.pg;

import com.perpetualmaze.core.HighScore;
import com.perpetualmaze.store.HighScoreStore;

import javax.sql.DataSource;
import javax.ws.rs.WebApplicationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class PostgresHighScoreStore implements HighScoreStore {
    private final DataSource dataSource;

    public PostgresHighScoreStore(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Optional<HighScore> get() {
        String sql = "SELECT * FROM high_score";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return of(new HighScore(rs.getString("name"), rs.getInt("score")));
            }
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to retrieve high score", sqlException);
        }
        return empty();
    }

    @Override
    public void save(HighScore highScore) {
        String sql = "UPDATE high_score SET name = ?, score = ?";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, highScore.getName());
            ps.setInt(2, highScore.getScore());
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to save high score", sqlException);
        }
    }
}
