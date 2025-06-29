package rw.ac.auca.ecommmerce.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rw.ac.auca.ecommmerce.core.product.model.Product;
import rw.ac.auca.ecommmerce.core.product.service.IProductService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/product")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/search/all")
    public String getAllProducts(Model model) {
        model.addAttribute("product", new Product());
        List<Product> products = productService.findProductsByState(Boolean.TRUE);
        model.addAttribute("products", products);
        return "product/productP";
    }

    @GetMapping("/register")
    public String getProductRegistrationPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", List.of());
        return "product/productP";
    }

    @PostMapping("/register")
    public String registerProduct(@ModelAttribute("product") Product theProduct, Model model) {
        try {
            if (theProduct != null) {
                productService.createProduct(theProduct);
                model.addAttribute("message", "Product registered successfully!");
                return "redirect:/product/search/all";
            } else {
                model.addAttribute("error", "Product data is invalid");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error registering product: " + e.getMessage());
        }

        model.addAttribute("products", productService.findProductsByState(Boolean.TRUE));
        return "product/productP";
    }
}