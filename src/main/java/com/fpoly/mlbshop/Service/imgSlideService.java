package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.imgSlide;
import com.fpoly.mlbshop.repository.imgSlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class imgSlideService {
    @Autowired
    private imgSlideRepository imgSlideRepository;

    public List<imgSlide> findAll() {
        return imgSlideRepository.findAll();
    }

    public void saveAll(List<imgSlide> imgSlides) {
        imgSlideRepository.saveAll(imgSlides);
    }

    public List<imgSlide> findByIdProduct(String id) {
        return imgSlideRepository.findByIdProduct(id);
    }
}
