package com.staselko.HelpDesk.service.Impl;

import com.staselko.HelpDesk.model.entiti.Category;
import com.staselko.HelpDesk.repository.CategoriesRepo;
import com.staselko.HelpDesk.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepo categoriesRepo;

    @Override
    public List<Category> getAllCategories() {
        return categoriesRepo.findAll();
    }
}