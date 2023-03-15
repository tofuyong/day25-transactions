package ibf2022.day25.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ibf2022.day25.model.Resv;

@Repository
public class ResvRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SELECT_SQL = "select * from resv";
    private final String INSERT_SQL = "insert into resv (resv_date, full_name) values (?,?)";
    
    // #1 Find all Reservations
    public List<Resv> findAll() {
        return jdbcTemplate.query(SELECT_SQL, BeanPropertyRowMapper.newInstance(Resv.class));
    }

    // #2 Create Reservation
    public Integer createResv(Resv resv) {
        // Create GeneratedKeyHolder object to hold generated ID
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"id"});

                ps.setDate(1, resv.getResvDate());
                ps.setString(2, resv.getFullName());
                return ps;
            }
        };
        int rowsAffected = jdbcTemplate.update(psc, generatedKeyHolder);

        // Get auto-incremented ID
        Integer returnedId = generatedKeyHolder.getKey().intValue();
        return returnedId;
    }


}
