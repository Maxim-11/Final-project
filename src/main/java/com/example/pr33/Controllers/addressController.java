package com.example.pr33.Controllers;

import com.example.pr33.models.Address;
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
public class addressController {
    @Autowired
    private com.example.pr33.repositories.AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/address")
    public String address(Model model) {
        model.addAttribute("addresses", addressRepository.findAll());
        return "address";
    }

    @GetMapping("/new/address")
    public String showSignUpFormAd(Address address) {
        return "add-address";
    }

    @GetMapping("/searchAd")
    public String searchAddresses(@RequestParam("street") String street, Model model) {
        Address addresses = addressRepository.findByStreet(street);
        model.addAttribute("addresses", addresses);
        return "searchAd";
    }


    @GetMapping("/edit/{id}/address")
    public String showUpdateFormAd(@PathVariable(value = "id") long id, Model model) {
        Address address = addressRepository.findById(id).orElseThrow();
        model.addAttribute("address", address);
        return "update-address";
    }

    @GetMapping("/delete/{id}/address")
    public String deleteAddress(@PathVariable("id") long id, Model model) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        addressRepository.delete(address);
        model.addAttribute("addresses", addressRepository.findAll());
        return "address";
    }

    @PostMapping("/addaddress")
    public String addAddress(@Valid Address address, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-address";
        }

        addressRepository.save(address);
        model.addAttribute("addresses", addressRepository.findAll());

        return "address";
    }

    @PostMapping("/update/{id}/address")
    public String updateAddress(@ModelAttribute("address")@Valid Address address,BindingResult result,@PathVariable(value = "id") long id) {
        if(result.hasErrors())
            return "update-address";
        addressRepository.save(address);
        return "redirect:/address";
    }
}
