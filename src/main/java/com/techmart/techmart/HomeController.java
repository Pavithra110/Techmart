package com.techmart.techmart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/products-page")
    public String productsPage(
            @RequestParam(defaultValue = "0") int page,
            String keyword,
            String category,
            String sort,
            String success,
            Model model) {

        List<Product> products;

     // Category Filter
     // Search + Category
        if (keyword != null && !keyword.isEmpty()
                && category != null && !category.isEmpty()) {

            products = productService.searchProductsByCategory(keyword, category);

        }
        else if (keyword != null && !keyword.isEmpty()) {

            products = productService.searchProducts(keyword);

        }
        else if (category != null && !category.isEmpty()) {

            products = productService.getProductsByCategory(category);

        }
        else {

        	Page<Product> productPage = productService.getProductsByPage(page);

            products = productPage.getContent();

            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages());
        }
        // Sort
        if (sort != null) {

            switch (sort) {

            case "priceAsc":
                products = productService.getProductsByPriceAsc();
                break;

            case "priceDesc":
                products = productService.getProductsByPriceDesc();
                break;

            case "nameAsc":
                products = productService.getProductsByNameAsc();
                break;
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("category", category);

        if (success != null) {
            model.addAttribute("success", success);
        }

        model.addAttribute("totalProducts", products.size());

        long mobiles = products.stream()
                .filter(p -> "Mobile".equalsIgnoreCase(p.getCategory()))
                .count();

        long laptops = products.stream()
                .filter(p -> "Laptop".equalsIgnoreCase(p.getCategory()))
                .count();

        long accessories = products.stream()
                .filter(p -> "Accessories".equalsIgnoreCase(p.getCategory()))
                .count();

        model.addAttribute("mobileCount", mobiles);
        model.addAttribute("laptopCount", laptops);
        model.addAttribute("accessoriesCount", accessories);

        return "products";
    }
    
    @GetMapping("/add-product")
    public String addProductPage(Model model) {

        model.addAttribute("product", new Product());

        return "add-product";
    }
    
    @PostMapping("/save-product")
    public String saveProduct(@Valid @ModelAttribute Product product,
                              BindingResult result,
                              Model model) {

        if (result.hasErrors()) {
            model.addAttribute("product", product);
            return "add-product";
        }

        productService.addProduct(product);

        return "redirect:/products-page?success=added";
    }
    
    
    @GetMapping("/edit-product/{id}")
    public String editProductPage(@PathVariable int id, Model model) {

        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "edit-product";
    }
    
    @GetMapping("/product/{id}")
    public String viewProduct(@PathVariable int id, Model model) {

        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "product-details";
    }
    
    @PostMapping("/update-product")
    public String updateProduct(@ModelAttribute Product product) {

        productService.addProduct(product);

        return "redirect:/products-page?success=updated";
    }
    
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable int id) {

        productService.deleteProduct(id);

        return "redirect:/products-page?success=deleted";
    }
}