package alfio.repository;

import alfio.model.Blacklist;
import ch.digitalfondue.npjt.Bind;
import ch.digitalfondue.npjt.Query;
import ch.digitalfondue.npjt.QueryRepository;

import java.util.List;

@QueryRepository
public interface AdminBlacklistServiceRepository {

    @Query("insert into blacklist_guests(id, email) values(:id, :email)")
    int insert(@Bind("id") String additionalServiceId, @Bind("email") String email);

    @Query("select id, email from blacklist_guests where email = :email")
    List<Blacklist> findByEmail(@Bind("email") String email);

    @Query("delete from blacklist_guests where email = :email")
    int deleteByEmail(@Bind("email") String email);

    @Query("select id, email from blacklist_guests")
    List<Blacklist> findAll();

    @Query("select id, email from blacklist_guests where id = :id")
    List<Blacklist> findById(@Bind("id") String id);

    @Query("update blacklist_guests set email = :email where id = :id")
    int updateById(String id, String email);
}
