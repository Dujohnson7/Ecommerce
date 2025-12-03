package rw.ac.auca.ecommerce.core.productCategory.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommerce.core.productCategory.repository.IProductCategoryRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements IProductCategoryService {

    private final IProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory registerProductCategory(ProductCategory theProductCategory) {
        return productCategoryRepository.save(theProductCategory);
    }

    @Override
    public ProductCategory updateProductCategory(ProductCategory theProductCategory) {
        ProductCategory found = findByIdAndState(theProductCategory.getId(),theProductCategory.isActive());
        if(Objects.nonNull(found)){
            found.setCategoryName(theProductCategory.getCategoryName());
            found.setCategoryDescription(theProductCategory.getCategoryDescription());
            found.setCategoryIcon(theProductCategory.getCategoryIcon());
            return productCategoryRepository.save(found);
        }
        throw new ObjectNotFoundException(ProductCategory.class, "Product Category Not Found");
    }

    @Override
    public ProductCategory deleteProductCategory(ProductCategory theProductCategory) {
        ProductCategory found = findByIdAndState(theProductCategory.getId(),theProductCategory.isActive());
        if(Objects.nonNull(found)){
            found.setActive(Boolean.FALSE);
            return productCategoryRepository.save(found);
        }
        throw new ObjectNotFoundException(ProductCategory.class, "Product Category Not Found");
    }

    @Override
    public ProductCategory findProductCategoryByCategoryName(String name) {
        ProductCategory theProductCategory = productCategoryRepository.findProductCategoryByCategoryName(name)
                .orElseThrow(() -> new ObjectNotFoundException(ProductCategory.class , "Category Not Found") );
        return  theProductCategory;
    }


    @Override
    public ProductCategory findByIdAndState(UUID id, Boolean state) {
        ProductCategory theProductCategory = productCategoryRepository.findByIdAndActive(id, state)
                .orElseThrow(() -> new ObjectNotFoundException(ProductCategory.class , "Category Not Found") );
        return  theProductCategory;
    }

    @Override
    public List<ProductCategory> findByState(Boolean state) {
        return productCategoryRepository.findAllByActive(state);
    }

    @Override
    public List<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }
}
