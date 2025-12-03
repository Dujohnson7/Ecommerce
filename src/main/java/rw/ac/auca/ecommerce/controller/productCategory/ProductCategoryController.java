package rw.ac.auca.ecommerce.controller.productCategory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.ecommerce.core.productCategory.model.ProductCategory;
import rw.ac.auca.ecommerce.core.productCategory.service.IProductCategoryService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/productCategory/")
public class ProductCategoryController {
    private final IProductCategoryService  productCategoryService;

    @GetMapping({"/","/search/all"})
    public String getAllProductCategories(Model model){
        model.addAttribute("productCategory", new ProductCategory());
        List<ProductCategory> productCategories = productCategoryService.findByState(Boolean.TRUE);
        model.addAttribute("productCategories", productCategories);
        return "product/categoryP";
    }


    @GetMapping("/register")
    public  String getProductCategoryRegisterPage(Model model){
        model.addAttribute("productCategory", new ProductCategory());
        return "product/categoryP";
    }

    @PostMapping("/register")
    public  String registerProductCategory(@ModelAttribute("productCategory") ProductCategory theProductCategory , Model model){
        try {
            if (Objects.nonNull(theProductCategory)) {
                productCategoryService.registerProductCategory(theProductCategory);
                model.addAttribute("message", "Product Category has been registered successfully");
                return "redirect:/productCategory/search/all";
            } else {
                model.addAttribute("error", "Data is invalid");
            }
        }catch (Exception e){
            model.addAttribute("error", "Error Registering Product Category: " + e.getMessage());
        }
        model.addAttribute("productCategory", productCategoryService.findByState(Boolean.TRUE));
        return "product/categoryP";
    }


    @PostMapping("/delete")
    public String deleteProductCategory(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            ProductCategory theProductCategory = new ProductCategory();
            theProductCategory.setId(UUID.fromString(id));
            productCategoryService.deleteProductCategory(theProductCategory);
        }
        return "redirect:/product/search/all";
    }

    @PostMapping("/update")
    public String updateProductCategory(@RequestParam("id") String id, Model model){
        if(Objects.nonNull(id)){
            ProductCategory theProductCategory = productCategoryService.findByIdAndState(UUID.fromString(id) , Boolean.TRUE);
            if(Objects.nonNull(theProductCategory)){
                model.addAttribute("productCategory" , theProductCategory);
                 return "redirect:/productCategory/search/all";
            }
        }
        model.addAttribute("error" , "Wrong Information");
        return "product/categoryP";
    }

    @PostMapping("/updateProductCategory")
    public String updateProductCategory(@ModelAttribute("productCategory") ProductCategory theProductCategory,  Model model){
        if(Objects.nonNull(theProductCategory)){
            System.out.println("The productCategory: "+theProductCategory);
            productCategoryService.updateProductCategory(theProductCategory);
        }
        return "redirect:/productCategory/search/all";
    }


}
