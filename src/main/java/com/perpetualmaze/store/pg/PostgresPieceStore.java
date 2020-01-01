package com.perpetualmaze.store.pg;

import com.perpetualmaze.core.Piece;
import com.perpetualmaze.store.PieceStore;

import javax.sql.DataSource;
import javax.ws.rs.WebApplicationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static com.google.common.base.Suppliers.memoizeWithExpiration;
import static java.util.concurrent.TimeUnit.MINUTES;

public class PostgresPieceStore implements PieceStore {
    private final Random random = new Random();
    private final DataSource dataSource;

    private final Supplier<List<Piece>> pieceSupplier = memoizeWithExpiration(() -> {
        String sql = "SELECT * FROM pieces";
        List<Piece> pieces = new ArrayList<>();
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pieces.add(new Piece(rs.getString("piece")));
            }
        } catch (SQLException sqlException) {
            throw new WebApplicationException("Failed to retrieve high score", sqlException);
        }
        return pieces;
    }, 5, MINUTES);

    public PostgresPieceStore(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public List<Piece> getAll() {
        return pieceSupplier.get();
    }

    @Override
    public Piece random() {
        List<Piece> pieces = pieceSupplier.get();
        return pieces.get(random.nextInt(pieces.size()));
    }
}
