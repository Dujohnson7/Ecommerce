package rw.ac.auca.ecommerce.core.productCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
    @Query("SELECT pr FROM ProductCategory pr WHERE pr.id =: id AND pr.active =: active")
    Optional<ProductCategory> findProductCategoriesByIdAndNamedQuery(@Param("id") UUID id, @Param("active") Boolean active );
    Optional<ProductCategory> findProductCategoriesByCategoryName(@Param("categoryName") String categoryName);
    Optional<ProductCategory> findByIdAndActive(@Param("id") UUID id, @Param("active") Boolean active);
    List<ProductCategory> findAllByActive(Boolean active);
    Optional<ProductCategory> findProductCategoryByCategoryName(String categoryName);
    //List<ProductCategory> findAllProductCategories();
}
