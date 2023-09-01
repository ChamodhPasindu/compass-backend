package lk.me.compass.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import lk.me.compass.entity.Invoice;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice,Integer>{
    
    final String INVOICE_COUNT = "select count(*) from invoice where status=1";

    @Query(value = INVOICE_COUNT, nativeQuery = true)
    List<Object[]> findInvoiceCount();
}
