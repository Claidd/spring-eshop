package com.hunt.springeshop.service;

import com.hunt.springeshop.dao.ProductRepository;
import com.hunt.springeshop.domain.Bucket;
import com.hunt.springeshop.domain.Product;
import com.hunt.springeshop.domain.User;
import com.hunt.springeshop.dto.ProductDTO;
import com.hunt.springeshop.mapper.ProductMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductMapper mapper = ProductMapper.MAPPER;

    private  final ProductRepository productRepository;
    private final UserService userService;

    private final BucketService bucketService;
    private final SimpMessagingTemplate template;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, SimpMessagingTemplate template) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.template = template;
    }


    @Override
    /*Метод взять все продукты */
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    public Product findByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    @Override
    @Transactional
    /*Метод добавления продукта в корзину. Передаем id выбранного товара и имя текущего пользователя*/
    public void addToUserBucket(Long productId, String username) {
        /*Находим юзера по его имени*/
        User user = userService.findByName(username);
        /*Делаем проверку на null и в случе чего выдаем ошибку*/
        if (user == null){
            throw new RuntimeException("Пользователь не найден - " + username);
        }
        /*Берем корзинку найденного пользователя Если ее нет, то*/
        Bucket bucket = user.getBucket();
        /*Если она пустая, то*/
        if (bucket == null){
            /*Создаем новую корзину(передаем в нее юзера и список ид продуктов) наполняем ее или оставляем пустой*/
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            /*присваиваем эту корзину этому юзеру*/
            user.setBucket(newBucket);
            /*сохраняем юзера с новой корзиной в БД*/
            userService.save(user);
        }
        else {
            /*Если корзина есть и не null, то мы вызываем метод для добавления в нее продукта
            * и предаем в него корзину пользователя и список из одного элемента с id продукта*/
            bucketService.addProduct(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    /*Метод удаления продукта из корзины. Передаем id выбранного товара и имя текущего пользователя*/
    public void removeToUserBucket(Long productId, String username) {
        /*Находим юзера по его имени*/
        User user = userService.findByName(username);
        /*Делаем проверку на null и в случе чего выдаем ошибку*/
        if (user == null){
            throw new RuntimeException("Пользователь не найден - " + username);
        }
        /*Берем корзинку найденного пользователя Если ее нет, то*/
        Bucket bucket = user.getBucket();
        /*Если она пустая, то*/
        if (bucket == null){
            /*Создаем новую корзину(передаем в нее юзера и список ид продуктов) наполняем ее или оставляем пустой*/
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            /*присваиваем эту корзину этому юзеру*/
            user.setBucket(newBucket);
            /*сохраняем юзера с новой корзиной в БД*/
            userService.save(user);
        }
        else {
            /*Если корзина есть и не null, то мы вызываем метод для добавления в нее продукта
             * и предаем в него корзину пользователя и список из одного элемента с id продукта*/
            bucketService.removeProduct(bucket, Collections.singletonList(productId));
        }
    }

    @Override
    @Transactional
    public void addProduct(ProductDTO dto){
        Product product = mapper.toProduct(dto);
        Product saveProduct = productRepository.save(product);
        /*Будем отправлять все в топик продукты*/
        /*Активизируем метод по которому все кто подписан на данный топик будут получать эту информацию*/
        template.convertAndSend("/topic/products", ProductMapper.MAPPER.fromProduct(saveProduct));
    }


}
