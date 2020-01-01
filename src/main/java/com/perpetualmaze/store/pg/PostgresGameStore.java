package com.perpetualmaze.store.pg;

import com.perpetualmaze.core.Board;
import com.perpetualmaze.core.Game;
import com.perpetualmaze.core.Piece;
import com.perpetualmaze.store.GameStore;

import javax.sql.DataSource;
import javax.ws.rs.WebApplicationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.util.Optional.*;

public class PostgresGameStore implements GameStore {
    private final DataSource dataSource;

    public PostgresGameStore(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Optional<Game> get(String id) {
        String sql = "SELECT * FROM games WHERE id = ?";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int score = rs.getInt("score");
                    Board board = new Board(rs.getString("board"));
                    Piece piece1 = new Piece(rs.getString("piece1"));
                    Piece piece2 = new Piece(rs.getString("piece2"));
                    Piece piece3 = new Piece(rs.getString("piece3"));
                    Game game = new Game(id, score, board, piece1, piece2, piece3);
                    return of(game);
                }
            }
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to retrieve game", sqlException);
        }
        return empty();
    }

    @Override
    public void save(Game game) {
        String sql = "INSERT INTO games (id, score, board, piece1, piece2, piece3) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, game.getId());
            ps.setInt(2, game.getScore());
            ps.setString(3, game.getBoard().toString());
            ps.setString(4, ofNullable(game.getPiece1()).map(Piece::serialize).orElse(null));
            ps.setString(5, ofNullable(game.getPiece2()).map(Piece::serialize).orElse(null));
            ps.setString(6, ofNullable(game.getPiece3()).map(Piece::serialize).orElse(null));
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to save game", sqlException);
        }
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM games WHERE id = ?";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to delete game", sqlException);
        }
    }
}
