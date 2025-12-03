package rw.ac.auca.ecommerce.core.productCategory.service;

import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface IProductCategoryService {
    ProductCategory registerProductCategory(ProductCategory theProductCategory);
    ProductCategory updateProductCategory(ProductCategory theProductCategory);
    ProductCategory deleteProductCategory(ProductCategory theProductCategory);
    ProductCategory findProductCategoryByCategoryName(String name);
    ProductCategory findByIdAndState(UUID id, Boolean state);
    List<ProductCategory> findByState(Boolean state);
    List<ProductCategory> findAllProductCategories();
}
