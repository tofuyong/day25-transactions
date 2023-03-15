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

import ibf2022.day25.model.ResvDetails;

@Repository
public class ResvDetailsRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SELECT_SQL = "select * from resvdetails";
    private final String INSERT_SQL = "insert into resvdetails (book_id, resv_id) values (?,?)";

    // #1 Find all resv details (tried on my own)
    public List<ResvDetails> findAll() {
        return jdbcTemplate.query(SELECT_SQL, BeanPropertyRowMapper.newInstance(ResvDetails.class));
    }

    // #2 Create resv details
    public Integer createResvDetails (ResvDetails rd) {
        // Create GeneratedKeyHolder object to hold generated ID
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"id"});

                ps.setInt(1, rd.getBook_id());
                ps.setInt(2, rd.getResv_id());
                return ps;
            }
        };
        jdbcTemplate.update(psc, generatedKeyHolder);

        // Get auto-incremented ID
        Integer returnedId = generatedKeyHolder.getKey().intValue();
        return returnedId;
    }
    
}
