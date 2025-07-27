package rw.ac.auca.ecommmerce.controller.clientSide;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rw.ac.auca.ecommmerce.core.product.model.Product;
import rw.ac.auca.ecommmerce.core.product.service.IProductService;
import rw.ac.auca.ecommmerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommmerce.core.productCategory.service.IProductCategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final IProductService productService;
    private final IProductCategoryService productCategoryService;

    @GetMapping({"/", "/home"})
    public String getAllProducts(Model model){
        // Fetch all active products
        List<Product> products = productService.findProductsByState(Boolean.TRUE);
        model.addAttribute("products", products);

        // Fetch all active product categories
        List<ProductCategory> productCategories = productCategoryService.findByState(Boolean.TRUE);
        model.addAttribute("productCategories", productCategories);

        // Add empty product object for forms if needed
        model.addAttribute("product", new Product());

        return "index";
    }
}