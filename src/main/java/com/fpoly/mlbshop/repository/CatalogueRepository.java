package com.fpoly.mlbshop.repository;

import com.fpoly.mlbshop.entity.Catalogue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CatalogueRepository extends JpaRepository<Catalogue,String>{

    List<Catalogue> findCatalogueByNameCatalogue(String name);

    @Query("SELECT c FROM Catalogue c WHERE c.IdCatalogue = ?1")
    Catalogue findCatalogueByIdCatalogue(String id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE Catalogue d SET d.quantity = (SELECT COUNT(sp) FROM Product sp WHERE sp.catalogues.IdCatalogue = d.IdCatalogue)")
    void updateQuantity();

    @Query("SELECT u FROM Catalogue u WHERE u.IdCatalogue like 'DM%' ORDER BY u.IdCatalogue DESC LIMIT 1")
    Catalogue findLastIdProduct();

    @Query("SELECT COUNT(c) FROM Catalogue c")
    String countCategory();
}
