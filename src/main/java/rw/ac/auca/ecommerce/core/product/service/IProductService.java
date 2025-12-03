package rw.ac.auca.ecommerce.core.product.service;

import rw.ac.auca.ecommerce.core.orders.model.Orders;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommerce.core.util.product.EStockState;

import java.util.List;
import java.util.UUID;


public interface IProductService {
    Product registerProduct (Product theProduct);
    Product updateProduct(Product theProduct);
    Product deleteProduct(Product theProduct);
    Product findProductByIdAndState(UUID id , Boolean active);
    Product findProductByProductCategory_Id(UUID id);
    List<Product> findProductsByState(Boolean active);
    List<Product> findProductsByStockStateAndState(EStockState stockState , Boolean active);
    List<Product> findProductsByStockStatesAndState(List<EStockState> stockStates , Boolean active);
    List<ProductCategory> findAllCategories();
    //List<Orders> findAllOrdersByUserId(UUID id);
    //List<ProductCategory> findAllProductCategories();


}
