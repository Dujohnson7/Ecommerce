package rw.ac.auca.ecommerce.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.auca.ecommerce.core.product.model.Product;
import rw.ac.auca.ecommerce.core.product.service.IProductService;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@RequestMapping("/product/")
public class ProductController {

    private final IProductService productService;

    @GetMapping({"/","/search/all"})
    public String getAllProducts(Model model){
        model.addAttribute("product", new Product());
        List<Product> products = productService.findProductsByState(Boolean.TRUE);
        model.addAttribute("products", products);

        List<ProductCategory> productCategories = productService.findAllCategories();
        model.addAttribute("productCategories", productCategories);
        return "/product/ProductP";
    }


    @GetMapping("/register")
    public String getProducts(Model model){
        model.addAttribute("product", new Product());
        List<ProductCategory> productCategories = productService.findAllCategories();
        model.addAttribute("productCategories", productCategories);
        return "/product/ProductP";
    }


    @PostMapping("/register")
    public String registerProduct( @ModelAttribute("product") Product theProduct, @RequestParam("file") MultipartFile file, Model model) {

        try {
            if (Objects.nonNull(theProduct)) {
                if (!file.isEmpty()) {
                    String uploadDir = "uploads/";
                    File uploadPath = new File(uploadDir);
                    if (!uploadPath.exists()) {
                        uploadPath.mkdirs();
                    }

                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path filePath = Paths.get(uploadDir + fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                    String productProfilePath = "/" + uploadDir + fileName;
                    theProduct.setProductProfile(productProfilePath);
                }

                productService.registerProduct(theProduct);
                model.addAttribute("message", "Product has been registered successfully");
                return "redirect:/product/";
            } else {
                model.addAttribute("error", "Data is invalid");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error Registering Product: " + e.getMessage());
        }

        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.findProductsByState(Boolean.TRUE));
        model.addAttribute("productCategories", productService.findAllCategories());
        return "/product/ProductP";
    }


    @PostMapping("/update")
    public String updateProduct(@RequestParam("id")  String id , Model model ){
        if(Objects.isNull(id)){
            Product thProduct = productService.findProductByIdAndState(UUID.fromString(id), Boolean.TRUE);
            if(Objects.nonNull(thProduct)){
                model.addAttribute("product", thProduct);
                productService.updateProduct(thProduct);
                return "redirect:/product/";
            }
        }
        model.addAttribute("error","Invalid Product ID");
        return "product/ProductP";
    }

    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id")  String id , Model model ) {
        if(Objects.isNull(id)){
            Product thProduct = new Product();
            thProduct.setId(UUID.fromString(id));
            productService.deleteProduct(thProduct);
        }
        return "redirect:/product/";
    }
}
