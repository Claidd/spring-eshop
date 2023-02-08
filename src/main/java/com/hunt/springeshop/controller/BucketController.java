package com.hunt.springeshop.controller;

import com.hunt.springeshop.domain.Product;
import com.hunt.springeshop.dto.BucketDTO;
import com.hunt.springeshop.service.BucketService;
import com.hunt.springeshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BucketController {
    private final BucketService bucketService;
    private final ProductService productService;

    public BucketController(BucketService bucketService, ProductService productService) {
        this.bucketService = bucketService;
        this.productService = productService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        /*Запрашиваем страницу корзины. Если корзины нет, то создаем новую, если есть то находим по имени и передаем
        * ее (приязанную) во вью*/
        if (principal == null){
            model.addAttribute("bucket", new BucketDTO());
        }
        else {
            BucketDTO bucketDTO = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDTO);
        }
        return "bucket";
    }

    @GetMapping("/bucket/{title}/delete")
    public String deleteBucket(@PathVariable String title, Principal principal){
        System.out.println(title);
        if (principal == null){
            return "redirect:/bucket";
        }
        Product product = productService.findByTitle(title);
        System.out.println("--------------------------------------------");
        System.out.println(title);
        System.out.println(product.getId());
        System.out.println("--------------------------------------------");
        productService.removeToUserBucket(product.getId(), principal.getName());
        return "redirect:/bucket";
    }



}
