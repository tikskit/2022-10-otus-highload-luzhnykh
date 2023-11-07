package ru.luzhnykh.socialnet.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.luzhnykh.socialnet.dto.AccountDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class AccountDaoJdbc implements AccountDao {

    private final JdbcOperations jdbc;

    @Override
    public void addAccount(AccountDto account) {
        jdbc.update("insert into socnet.accounts(userid, passhash) values(?, ?)", account.userId(), account.passHash());
    }

    @Override
    public boolean match(AccountDto account) {
        try {
            jdbc.queryForObject("select userid, passhash from socnet.accounts where login=? and passhash=?",
                    new AccountMapper(), account.userId(), account.passHash());
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) {
                return false;
            } else {
                throw e;
            }
        }
    }

    private static class AccountMapper implements RowMapper<AccountDto> {
        @Override
        public AccountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new AccountDto(
                    rs.getString("userid"),
                    rs.getInt("passhash")
            );
        }
    }
}
