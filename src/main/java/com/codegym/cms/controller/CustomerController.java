package com.codegym.cms.controller;

import com.codegym.cms.model.Customer;
import com.codegym.cms.model.Province;
import com.codegym.cms.service.CustomerService;
import com.codegym.cms.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> province(){
        return provinceService.findAll();
    }

    @GetMapping("/create-customer")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create-customer")
    public ModelAndView saveCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        if (bindingResult.hasFieldErrors()){
            modelAndView.addAllObjects(bindingResult.getModel());
            return modelAndView;
        } else {
        customerService.save(customer);
        modelAndView.addObject("message", "New customer created successfully");
        return modelAndView;
        }
    }
    @GetMapping("/customers")
    public ModelAndView listCustomers(Pageable pageable){
        Page<Customer> customers = customerService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @GetMapping("/customers1")
    public ModelAndView showCustomers(Pageable pageable, @RequestParam("s") Optional<String> s, @RequestParam("s") String a){
        Page<Customer> customers = customerService.findAllByFirstnameContaining(s.get(), pageable);
        ModelAndView modelAndView = new ModelAndView("/customer/list1");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("s",a);
        return modelAndView;
    }

    @GetMapping("/delete-customer/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customer/delete");
            modelAndView.addObject("customer", customer);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-customer")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer){
        customerService.remove(customer.getId());
        return "redirect:customers";
    }
    @GetMapping("/edit-customer/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            ModelAndView modelAndView = new ModelAndView("/customer/edit");
            modelAndView.addObject("customer", customer);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-customer")
    public ModelAndView updateCustomer(@Validated @ModelAttribute("customer") Customer customer, BindingResult result){
        ModelAndView modelAndView = new ModelAndView("/customer/edit");
        modelAndView.addObject("customer", customer);
        if (result.hasFieldErrors()){
            modelAndView.addAllObjects(result.getModel());
            return modelAndView;
        } else {
        customerService.save(customer);
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
        }
    }

    @GetMapping("/view-province/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Province province = provinceService.findById(id);
        if(province == null){
            return new ModelAndView("/error.404");
        }

        Iterable<Customer> customers = customerService.findAllByProvince(province);

        ModelAndView modelAndView = new ModelAndView("/province/view");
        modelAndView.addObject("province", province);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}