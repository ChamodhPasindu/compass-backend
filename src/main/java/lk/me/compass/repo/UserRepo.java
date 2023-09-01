package lk.me.compass.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByVenderNo(String venderNo);

    User findFirstById(Integer id);

    final String FIND_ALL_VENDERS = "select  sap_vender_no,sap_vender_name from user where role_id=1 and status=1";

    @Query(value = FIND_ALL_VENDERS, nativeQuery = true)
    List<Object[]> findAllVenders();

    final String FILTER_VENDERS_BY_NAME_AND_ID = "select  sap_vender_no,sap_vender_name from user where concat(sap_vender_no,sap_vender_name) like %:search% and role_id=1 limit :offset,:limit";

    @Query(value = FILTER_VENDERS_BY_NAME_AND_ID, nativeQuery = true)
    List<Object[]> filterVenderByNameAndId(String search,Integer limit,Integer offset);

    final String COUNT_VENDERS_BY_NAME_AND_ID = "select  count(*) from user where concat(sap_vender_no,sap_vender_name) like %:search% and role_id=1;";

    @Query(value = COUNT_VENDERS_BY_NAME_AND_ID, nativeQuery = true)
    List<Object[]> countVenderByNameAndId(String search);

    final String VENDOR_COUNT = "select count(*) from user where not role_id=2 and status=1";

    @Query(value = VENDOR_COUNT, nativeQuery = true)
    List<Object[]> findVendorCount();

    final String FIND_ALL_ARTIST = "select address,alternative_name,contact_number,email, nic,username,sap_vender_name,sap_vender_no from user where not role_id=2 and status=1 limit :offset,:limit";

    @Query(value = FIND_ALL_ARTIST, nativeQuery = true)
    List<Object[]> findAllArtist(Integer limit,Integer offset);

}
