package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.Catalogue;
import com.fpoly.mlbshop.repository.CatalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CatalogueRepository catalogueRepository;

    public List<Catalogue> findAll() {
        return catalogueRepository.findAll();
    }

    public Page<Catalogue> findAll(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 7);
        return catalogueRepository.findAll(pageable);
    }

    public List<Catalogue> searchProduct(String keyword) {
        return catalogueRepository.findCatalogueByNameCatalogue(keyword);
    }

    public Page<Catalogue> searchProduct(String keyword, int PageNo) {
        Pageable pageable = PageRequest.of(PageNo-1, 7);
        List<Catalogue> list = catalogueRepository.findCatalogueByNameCatalogue(keyword);
        int start = (int) pageable.getOffset();
        int end = (pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<Catalogue>(list, pageable, list.size());
    }

    public Catalogue save(Catalogue catalogue) {
        return catalogueRepository.save(catalogue);
    }

    public Catalogue update(Catalogue catalogue) {
        return catalogueRepository.save(catalogue);
    }

    public void delete(String id) {
        catalogueRepository.deleteById(id);
    }

    public Catalogue findById(String id) {
        return catalogueRepository.findCatalogueByIdCatalogue(id);
    }

    public void updateQuantity() {
        catalogueRepository.updateQuantity();
    }

    public String autoIncreaseIdCatalogue() {
        Catalogue lastCata = catalogueRepository.findLastIdProduct();
        String lastId = lastCata.getIdCatalogue();
        String[] indexCut = lastId.split("(?<=\\D)(?=\\d)");
        return indexCut[0] + String.format("%0"+indexCut[1].length()+"d", Integer.parseInt(indexCut[1])+1);
    }

    public String countCategory() {
        return catalogueRepository.countCategory();
    }
}
