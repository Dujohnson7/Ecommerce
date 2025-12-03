package rw.ac.auca.ecommerce.core.product.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.orders.repository.IOrdersRepository;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.repository.IProductRepository;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommerce.core.productCategory.repository.IProductCategoryRepository;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{

    private final IProductRepository productRepository;
    private final IProductCategoryRepository productCategoryRepository;
    private final IOrdersRepository ordersRepository;

    @Override
    public Product registerProduct (Product theProduct) {
        return productRepository.save(theProduct);
    }

    @Override
    public Product updateProduct(Product theProduct) {
        Product found = findProductByIdAndState(theProduct.getId(), Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setProductPrice(theProduct.getProductPrice());
            found.setProductName(theProduct.getProductName());
            found.setProductCategory(theProduct.getProductCategory());
            found.setProductDescription(theProduct.getProductDescription());
            found.setProductPrice(theProduct.getProductPrice());
            found.setStockState(theProduct.getStockState());
            found.setManufacturedDate(theProduct.getManufacturedDate());
            found.setExpirationDate(theProduct.getExpirationDate());
            found.setStockState(theProduct.getStockState());
            return productRepository.save(found);
        }
        throw new ObjectNotFoundException(ProductCategory.class, "Product  Not Found");
    }

    @Override
    public Product deleteProduct(Product theProduct) {

        Product found = findProductByIdAndState(theProduct.getId() , Boolean.TRUE);
        if(Objects.nonNull(found)){
            found.setActive(Boolean.FALSE);
            return productRepository.save(found);
        }
        throw new ObjectNotFoundException(Product.class , "Product Object not found");
    }

    @Override
    public Product findProductByIdAndState(UUID id, Boolean active) {
        Product theProduct = productRepository.findByIdAndActive(id , active)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class , "Product not Found"));
        return theProduct;
    }

    @Override
    public Product findProductByProductCategory_Id(UUID id) {
        Product theProduct = productRepository.findProductByProductCategory_Id(id)
                .orElseThrow(() -> new ObjectNotFoundException(Product.class , "Product not Found"));
        return theProduct;
    }

    @Override
    public List<Product> findProductsByState(Boolean active) {
        return productRepository.findAllByActive(active);
    }

    @Override
    public List<Product> findProductsByStockStateAndState(EStockState stockState, Boolean active) {
        return productRepository.findAllByStockStateAndActive(stockState,active);
    }

    @Override
    public List<Product> findProductsByStockStatesAndState(List<EStockState> stockStates, Boolean active) {
        return productRepository.findALlByStockStateInAndActive(stockStates,active);
    }

    @Override
    public List<ProductCategory> findAllCategories() {
        return productCategoryRepository.findAll();
    }

   /* @Override
    public List<Orders> findAllOrdersByUserId(UUID id) {
        return ordersRepository.findAllOrdersByUsersId(id);
    }*/


}
