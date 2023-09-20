package com.example.pr33.Controllers;

import com.example.pr33.models.*;
import com.example.pr33.repositories.*;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {
    private final CategoryRepository categoryRepository;
    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final SupplierRepository supplierRepository;

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    private final RatingRepository ratingRepository;

    @Autowired
    public MainController(CategoryRepository categoryRepository, AddressRepository addressRepository,ProductRepository productRepository,
                          SupplierRepository supplierRepository,UserRepository userRepository, ReviewRepository reviewRepository, RatingRepository ratingRepository){
        this.categoryRepository = categoryRepository;
        this.addressRepository = addressRepository;
        this.supplierRepository = supplierRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.ratingRepository = ratingRepository;
    }


    @GetMapping("/product")
    public String product(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        Iterable<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        Iterable<Rating> ratings = ratingRepository.findAll();
        model.addAttribute("ratings", ratings);
        return "product";
    }


    @GetMapping("/new/product")
    public String showSignUpFormPr(@ModelAttribute("product") Product product, Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Iterable<Address> addresses = addressRepository.findAll();
        model.addAttribute("addresses", addresses);
        Iterable<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("suppliers", suppliers);
        return "add-product";
    }

    @GetMapping("/searchPr")
    public String searchProducts(@RequestParam("name") String productName, Model model) {
        List<Product> products = productRepository.findByProductName(productName);
        model.addAttribute("products", products);
        return "searchPr";
    }


    @GetMapping("/edit/{id}/product")
    public String showUpdateFormPr(@PathVariable(value = "id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        Iterable<Address> addresses = addressRepository.findAll();
        model.addAttribute("addresses", addresses);
        Iterable<Supplier> suppliers = supplierRepository.findAll();
        model.addAttribute("suppliers", suppliers);
        return "update-product";
    }

    @GetMapping("/delete/{id}/product")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        productRepository.delete(product);
        model.addAttribute("products", productRepository.findAll());
        return "product";
    }


    @PostMapping("/addproduct")
    public String addProduct(@Valid Product product, BindingResult result,
                             @RequestParam String name, @RequestParam String street, @RequestParam String sname, Model model) {
        if (result.hasErrors()) {
            Iterable<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            Iterable<Address> addresses = addressRepository.findAll();
            model.addAttribute("addresses", addresses);
            Iterable<Supplier> suppliers = supplierRepository.findAll();
            model.addAttribute("suppliers", suppliers);
            return "add-product";
        }
        product.setCategory(categoryRepository.findByName(name));
        product.setAddress(addressRepository.findByStreet(street));
        product.setSupplier(supplierRepository.findBySname(sname));
        productRepository.save(product);

        return "redirect:/product";
    }

    @PostMapping("/update/{id}/product")
    public String updateProduct(@ModelAttribute("product")@Valid Product product,@RequestParam String name, @RequestParam String street, @RequestParam String sname, Model model, BindingResult result,@PathVariable(value = "id") long id) {
        if(result.hasErrors()) {
            Iterable<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            Iterable<Address> addresses = addressRepository.findAll();
            model.addAttribute("addresses", addresses);
            Iterable<Supplier> suppliers = supplierRepository.findAll();
            model.addAttribute("suppliers", suppliers);
            return "update-product";
        }
        product.setCategory(categoryRepository.findByName(name));
        product.setAddress(addressRepository.findByStreet(street));
        product.setSupplier(supplierRepository.findBySname(sname));
        productRepository.save(product);
        return "redirect:/product";
    }



    @GetMapping("/review")
    public String review(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
        return "review";
    }

    @GetMapping("/new/review")
    public String showSignUpFormRe(Review review) {
        return "add-review";
    }

    @GetMapping("/searchRe")
    public String searchReviews(@RequestParam("number") String number, Model model) {
        List<Review> reviews = reviewRepository.findByNumber(number);
        model.addAttribute("reviews", reviews);
        return "searchRe";
    }


    @GetMapping("/edit/{id}/review")
    public String showUpdateFormRe(@PathVariable(value = "id") long id, Model model) {
        Review review = reviewRepository.findById(id).orElseThrow();
        model.addAttribute("review", review);
        return "update-review";
    }

    @GetMapping("/delete/{id}/review")
    public String deleteReview(@PathVariable("id") long id, Model model) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        reviewRepository.delete(review);
        model.addAttribute("reviews", reviewRepository.findAll());
        return "review";
    }

    @PostMapping("/addreview")
    public String addReview(@Valid Review review, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-review";
        }

        reviewRepository.save(review);
        model.addAttribute("reviews", reviewRepository.findAll());

        return "review";
    }

    @PostMapping("/update/{id}/review")
    public String updateReview(@ModelAttribute("review")@Valid Review review,BindingResult result,@PathVariable(value = "id") long id) {
        if(result.hasErrors())
            return "update-review";
        reviewRepository.save(review);
        return "redirect:/review";
    }

    @GetMapping("/product/review/add")
    private String Main(Model model){
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        Iterable<Review> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "product-review-add";
    }

    @PostMapping("/product/review/add")
    public String blogPostAdd(@RequestParam Long product, @RequestParam Long review, Model model)
    {
        Product product1 = productRepository.findById(product).orElseThrow();
        Review review1 = reviewRepository.findById(review).orElseThrow();
        product1.getReviews().add(review1);
        productRepository.save(product1);
        return "redirect:/product";
    }

    @GetMapping("/product/rating/add")
    private String Rat(Model model){
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        Iterable<Rating> ratings = ratingRepository.findAll();
        model.addAttribute("ratings", ratings);
        return "product-rating-add";
    }

    @PostMapping("/product/rating/add")
    public String blogPostAddRat(@RequestParam Long product, @RequestParam Long rating, Model model)
    {
        Product product2 = productRepository.findById(product).orElseThrow();
        Rating rating1 = ratingRepository.findById(rating).orElseThrow();
        product2.getRating().add(rating1);
        productRepository.save(product2);
        return "redirect:/product";
    }



    @GetMapping("/rating")
    public String rating(Model model) {
        model.addAttribute("ratings", ratingRepository.findAll());
        return "rating";
    }


    @GetMapping("/new/rating")
    public String showSignUpFormRat(@ModelAttribute("rating")Rating rating, Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "add-rating";
    }

    @GetMapping("/searchRat")
    public String searchRatings(@RequestParam(name = "rate", required = false) String rate, Model model) {
        if (rate == null || rate.isEmpty()) {
            return "/home";
        }

        try {
            double rateValue = Double.parseDouble(rate);
            Rating ratings = ratingRepository.findByRate(rateValue);
            if (ratings == null) {
                return "/home";
            }
            model.addAttribute("ratings", ratings);
            return "searchRat";
        } catch (NumberFormatException e) {
            return "error-view";
        }
    }



    @GetMapping("/edit/{id}/rating")
    public String showUpdateFormRat(@PathVariable(value = "id") long id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow();
        model.addAttribute("rating", rating);
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "update-rating";
    }

    @GetMapping("/delete/{id}/rating")
    public String deleteRating(@PathVariable("id") long id, Model model) {
        Rating rating = ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));

        // Обновление внешнего ключа на null
        rating.setUser(null); // Предполагается, что "user" - это поле в вашей сущности Rating

        // Сохранение обновленной записи (обнуленного внешнего ключа)
        ratingRepository.save(rating);

        // Удаление записи
        ratingRepository.delete(rating);

        model.addAttribute("ratings", ratingRepository.findAll());
        return "rating";
    }

    @PostMapping("/addrating")
    public String addRating(@Valid Rating rating, BindingResult result,
                            @RequestParam String username, Model model) {
        if (result.hasErrors()) {
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "add-rating";
        }

        rating.setUser(userRepository.findByUsername(username));
        ratingRepository.save(rating);

        return "redirect:/rating";
    }

    @PostMapping("/update/{id}/rating")
    public String updateRating(@ModelAttribute("rating")@Valid Rating rating,@RequestParam String username, Model model,BindingResult result,@PathVariable(value = "id") long id) {
        if(result.hasErrors()) {
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "update-rating";
        }

        rating.setUser(userRepository.findByUsername(username));
        ratingRepository.save(rating);
        return "redirect:/rating";
    }
}
