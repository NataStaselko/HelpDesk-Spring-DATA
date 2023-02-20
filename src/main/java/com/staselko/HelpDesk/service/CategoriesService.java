package com.staselko.HelpDesk.service;

import com.staselko.HelpDesk.model.entiti.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoriesService{
    List<Category> getAllCategories();

}
