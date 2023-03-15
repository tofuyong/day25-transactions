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

import ibf2022.day25.model.Book;

@Repository
public class BookRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SELECT_SQL = "select * from book";
    private final String INSERT_SQL = "insert into book (title, quantity) values (?,?)";
    private final String UPDATE_SQL = "update book set quantity = quantity - 1 where id = ?"; //this assumes user borrows 1 book of that title

    // #1 Find all books
    public List<Book> findAll() {
        return jdbcTemplate.query(SELECT_SQL, BeanPropertyRowMapper.newInstance(Book.class));
    }
    
    // #2 Create book
    public Integer createBook(Book book) {
        // Create GeneratedKeyHolder object to hold generated ID
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(INSERT_SQL, new String[] {"id"});

                ps.setString(1, book.getTitle());
                ps.setInt(2, book.getQuantity());
                return ps;
            }
        };
        int rowsAffected = jdbcTemplate.update(psc, generatedKeyHolder);

        // Get auto-incremented ID
        Integer returnedId = generatedKeyHolder.getKey().intValue();
        return returnedId;
    }

    // #3 Update book
    public int update(Integer id) {
        return jdbcTemplate.update(UPDATE_SQL, id);
    }
}
