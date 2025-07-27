package rw.ac.auca.ecommmerce.core.productCategory.service;

import org.springframework.data.repository.query.Param;
import rw.ac.auca.ecommmerce.core.productCategory.model.ProductCategory;

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
