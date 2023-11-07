package com.example.wearVillage.DAOImpl;

import com.example.wearVillage.DAO.AskPostDAO;
import com.example.wearVillage.Entity.AskObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Repository
public class AskPostDAOImpl implements AskPostDAO {


  private final NamedParameterJdbcTemplate template;


  // 개별조회   (  INPUT : 게시글 ID  /  OUTPUT  :  개별게시글  )
  @Override
  public AskObject askFind(String askpostid) {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT ASKPOSTID,ID,TITLE,BODY,TIMELOG,ANSWERBODY,GUBUN,EMAIL ");
    sql.append(" FROM ASKPOST  ");
    sql.append(" WHERE ASKPOSTID = :ASKPOSTID ");
    AskRowMapper askRowMapper = new AskRowMapper();

    MapSqlParameterSource param = new MapSqlParameterSource("ASKPOSTID", askpostid);

    try {
      return template.queryForObject(sql.toString(), param, askRowMapper);
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  // 전체조회    ( INPUT : 유저 ID   /   OUTPUT : 전체게시글 )
  @Override
  public List<AskObject> askUserFindAll(String id) {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT ASKPOSTID,ID,TITLE,BODY,TIMELOG,ANSWERBODY,GUBUN,EMAIL ");
    sql.append(" FROM ASKPOST  ");
    sql.append(" WHERE ID = :ID ");
    AskRowMapper askRowMapper = new AskRowMapper();
    MapSqlParameterSource param = new MapSqlParameterSource("ID", id);

    try {
      return template.query(sql.toString(), param, askRowMapper);
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  // 삭제  (  INPUT : ASKPOSTID  /   OUTPUT : boolean  )
  @Override
  public boolean askDelete(String askpostid) {
    StringBuffer sql = new StringBuffer();
    sql.append("  DELETE FROM ASKPOST    ");
    sql.append("  WHERE ASKPOSTID = :ASKPOSTID   ");

    MapSqlParameterSource param = new MapSqlParameterSource("ASKPOSTID", askpostid);

    try {
      int deletedRows = template.update(sql.toString(), param);
      return deletedRows != 0;
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  // 수정      ( INPUT :  ASKPOSTID  /   OUTPUT :  수정된 게시글 정보  )
  @Override
  public boolean askUpdate(String askpostid) {
    StringBuffer sql = new StringBuffer();
    sql.append(" * ");
    sql.append(" * ");
    MapSqlParameterSource param = new MapSqlParameterSource("ASKPOSTID", askpostid);
    try {
      int updatedRows = template.update(sql.toString(), param);
      return updatedRows != 0;
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }
  // 쓰기   ---------   웹에서 보낼 데이터 : userID, title, body, gubun, email
  @Override
  public Long askWrite(AskObject askObject) {
    StringBuffer sql = new StringBuffer();
    sql.append(" INSERT INTO ASKPOST (ASKPOSTID, ID, TITLE, BODY, GUBUN, EMAIL, TIMELOG) ");
    sql.append(" VALUES (ASKPOST_PK_SEQ.NEXTVAL, :ID, :TITLE, :BODY, :GUBUN, :EMAIL, CURRENT_TIMESTAMP) ");

    MapSqlParameterSource param = new MapSqlParameterSource();
    param.addValue("ID", askObject.getId());
    param.addValue("TITLE", askObject.getTitle());
    param.addValue("BODY", askObject.getBody());
    param.addValue("GUBUN", askObject.getGubun());
    param.addValue("EMAIL", askObject.getEmail());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    try {
      int updatedRows = template.update(sql.toString(), param, keyHolder, new String[]{"askpostid"});
      long key = keyHolder.getKey().longValue();
      return key;
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }

  // 전체목록
  @Override
  public List<AskObject> askFindAll() {
    StringBuffer sql = new StringBuffer();
    sql.append(" SELECT ASKPOSTID,ID,TITLE,BODY,TIMELOG,ANSWERBODY,GUBUN,EMAIL ");
    sql.append(" FROM ASKPOST  ");

    AskRowMapper askRowMapper = new AskRowMapper();

    try {
      return template.query(sql.toString(), askRowMapper);
    } catch (DataAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
