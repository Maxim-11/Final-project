package com.example.pr33.Controllers;

import com.example.pr33.models.Category;
import com.example.pr33.models.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasAnyAuthority('EMPLOYEE','ADMIN')")
public class employeeController {
    @Autowired
    private com.example.pr33.repositories.CategoryRepository categoryRepository;

    @Autowired
    private com.example.pr33.repositories.SupplierRepository supplierRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "category";
    }


    @GetMapping("/new/category")
    public String showSignUpFormCa(Category category) {
        return "add-category";
    }

    @GetMapping("/searchCa")
    public String searchCategories(@RequestParam("name") String name, Model model) {
        Category categories = categoryRepository.findByName(name);
        model.addAttribute("categories", categories);
        return "searchCa";
    }


    @GetMapping("/edit/{id}/category")
    public String showUpdateFormCa(@PathVariable(value = "id") long id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("category", category);
        return "update-category";
    }

    @GetMapping("/delete/{id}/category")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        categoryRepository.delete(category);
        model.addAttribute("categories", categoryRepository.findAll());
        return "category";
    }

    @PostMapping("/addcategory")
    public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-category";
        }

        categoryRepository.save(category);
        model.addAttribute("categories", categoryRepository.findAll());

        return "redirect:/category";
    }

    @PostMapping("/update/{id}/category")
    public String updateCategory(@ModelAttribute("category")@Valid Category category,BindingResult result,@PathVariable(value = "id") long id) {
        if(result.hasErrors())
            return "update-category";
        categoryRepository.save(category);
        return "redirect:/category";
    }


    @GetMapping("/supplier")
    public String supplier(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "supplier";
    }


    @GetMapping("/new/supplier")
    public String showSignUpFormSu(Supplier supplier) {
        return "add-supplier";
    }

    @GetMapping("/searchSu")
    public String searchSuppliers(@RequestParam("name") String sname, Model model) {
        Supplier suppliers = supplierRepository.findBySname(sname);
        model.addAttribute("suppliers", suppliers);
        return "searchSu";
    }


    @GetMapping("/edit/{id}/supplier")
    public String showUpdateFormSu(@PathVariable(value = "id") long id, Model model) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        model.addAttribute("supplier", supplier);
        return "update-supplier";
    }

    @GetMapping("/delete/{id}/supplier")
    public String deleteSupplier(@PathVariable("id") long id, Model model) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        supplierRepository.delete(supplier);
        model.addAttribute("suppliers", supplierRepository.findAll());
        return "supplier";
    }

    @PostMapping("/addsupplier")
    public String addSupplier(@ModelAttribute("supplier") @Valid Supplier supplier, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-supplier";
        }

        supplierRepository.save(supplier);
        model.addAttribute("suppliers", supplierRepository.findAll());

        return "redirect:/supplier";
    }

    @PostMapping("/update/{id}/supplier")
    public String updateCategory(@ModelAttribute("supplier")@Valid Supplier supplier,BindingResult result,@PathVariable(value = "id") long id) {
        if(result.hasErrors())
            return "update-supplier";
        supplierRepository.save(supplier);
        return "redirect:/supplier";
    }
}
