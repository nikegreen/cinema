package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Seat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcSeatRepository implements SeatRepository {
    private static final Logger LOGGER = Logger.getLogger(JdbcSeatRepository.class);
    private static final String SQL_FIND_BY_ROOM_ID_AND_ROW =
            "SELECT * FROM seats WHERE room_id = ? and pos_row = ? ORDER BY (id, pos_row, cell)";
    private final BasicDataSource pool;

    public JdbcSeatRepository(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<List<Seat>> getByRoomId(int roomId) {
        List<List<Seat>> result = new ArrayList<>();
            for (int row = 1; ; row++) {
               List<Seat> cells = getByRoomIdAndRow(roomId, row);
               if (cells.isEmpty()) {
                   break;
               }
               result.add(cells);
            }
        return result;
    }

    @Override
    public List<Seat> getByRoomIdAndRow(int roomId, int rowIndex) {
        List<Seat> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(SQL_FIND_BY_ROOM_ID_AND_ROW)
        ) {
            ps.setInt(1, roomId);
            ps.setInt(2, rowIndex);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(createSeat(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("find all seat where roomId=" + roomId
                    + " & row=" + rowIndex
                    + ". " + e.getMessage(), e);
        }
        return result;
    }

    private Seat createSeat(ResultSet it) throws SQLException {
        int cell;
        try {
            cell = it.getInt("cell");
        } catch (Exception e) {
            cell = 0;
        }
        return new Seat(
                it.getInt("id"),
                it.getInt("room_id"),
                it.getInt("pos_row"),
                cell,
                false
        );
    }
}
