package repository;

import model.Postings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostingsRepository extends JpaRepository<Postings, Long> {
    @Query("SELECT p FROM Postings p WHERE p.docDate >= :startDate AND p.docDate <= :endDate AND (:authorizedDel IS NULL OR p.authorizedDel = :authorizedDel)")
    List<Postings> findPostingsByPeriodAndAuthorizedDel(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("authorizedDel") Boolean authorizedDel);
}