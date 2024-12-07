package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Subsubcategory;
import com.cgpi.cgpi.repository.SubsubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubsubcategoryService {

    @Autowired
    private SubsubcategoryRepository subsubcategoryRepository;

    public List<Subsubcategory> findAll() {
        return subsubcategoryRepository.findAll();
    }

    public Subsubcategory findById(Long id) {
        return subsubcategoryRepository.findById(id).orElse(null);
    }

    public Subsubcategory create(Subsubcategory subsubcategory) {
        return subsubcategoryRepository.save(subsubcategory);
    }

    public Subsubcategory update(Long id, Subsubcategory subsubcategoryDetails) {
        Subsubcategory subsubcategory = findById(id);
        if (subsubcategory != null) {
            subsubcategory.setName(subsubcategoryDetails.getName());
            subsubcategory.setImage(subsubcategoryDetails.getImage());
            subsubcategory.setSubcategoryId(subsubcategoryDetails.getSubcategoryId());
            return subsubcategoryRepository.save(subsubcategory);
        }
        return null;
    }

    public void delete(Long id) {
        subsubcategoryRepository.deleteById(id);
    }
    public List<Subsubcategory> findBySubcategoryId(Long subcategoryId) {
        return subsubcategoryRepository.findBySubcategoryId(subcategoryId);
    }

}
