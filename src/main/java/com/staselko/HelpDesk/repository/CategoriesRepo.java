package com.staselko.HelpDesk.repository;

import com.staselko.HelpDesk.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<Category, Long> {
}
