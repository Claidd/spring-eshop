package com.hunt.springeshop.service;

import com.hunt.springeshop.dao.BucketRepository;
import com.hunt.springeshop.dao.ProductRepository;
import com.hunt.springeshop.domain.*;
import com.hunt.springeshop.dto.BucketDTO;
import com.hunt.springeshop.dto.BucketDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class BucketServiceImpl implements BucketService{
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    private final OrderService orderService;

    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService, OrderService orderService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.orderService = orderService;
    }


    @Override
    @Transactional
    /*Метод создания новой корзинки*/
    public Bucket createBucket(User user, List<Long> productIds) {
        /*Создаем новую корзину*/
        Bucket bucket = new Bucket();
        /*Указывает этой корзине ее юзера*/
        bucket.setUser(user);
        /*Берем из базы список ссылок на продукты по id и ищем по ним сами объекты*/
        List<Product> productList = getCollectRefProductsByIds(productIds);
        /*Указываем корзине список продуктов в ней*/
        bucket.setProducts(productList);
        /*Сохраняем все корзину с данными в БД*/
        return bucketRepository.save(bucket);
    }

    /*Метод доставания ссылок на объекты из репозитория*/
    private List<Product> getCollectRefProductsByIds(List<Long> productsIds) {
        return productsIds.stream()
                /*getOne вытаскивает ссылку на объект, findById - вытаскивает сам объект,
                * получается мы создаем список ссылок на объекты, который может обращаться и получать данные полей объекта по этой
                *  ссылке, а не сам список самих объектов. Будем получать по ил первый попавшийся ид и добавлять ссылку
                * на этот объект. Нет смысла вытаскивать весь объект, так как вытаскивая весь, может подгрузиться вся
                * начинка объекта, в том числе и лишняя, а через ссылку на объект мы можем брать нужную нам информацию */
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    /*Метод добавления корзины, в параметр передаем массив айдишников продуктов*/
    @Override
    /*Метод добавления списка продуктов в корзинку*/
    /* нам в метод передали корзину пользователя и список из одного элемента с id продукта*/
    public void addProduct(Bucket bucket, List<Long> productIds) {
        /*Создаем список продуктов из корзины*/
        List<Product> products = bucket.getProducts();
        /*создаем еще один новый список продуктов и проверяем, если в корзине выше не оказалось продуктов (пустая), то
        * мы создаем просто новый пустой арей лист, если есть, то создаем новый список продуктов и в него добавляем
        * тот список, что выше*/
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        /*В этом список добавляем продукт 1 продует, который мы находим по id в БД */
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        /*далее установившем новый список продуктов в нашу корзинку*/
        bucket.setProducts(newProductList);
        /* и сохраняем корзинку с новым набром продуктов в БД*/
        bucketRepository.save(bucket);
    }

    @Override
    /*Метод добавления списка продуктов в корзинку*/
    /* нам в метод передали корзину пользователя и список из одного элемента с id продукта*/
    public void removeProduct(Bucket bucket, List<Long> productIds) {
        /*Создаем список продуктов из корзины*/
        List<Product> products = bucket.getProducts();
        /*создаем еще один новый список продуктов и проверяем, если в корзине выше не оказалось продуктов (пустая), то
         * мы создаем просто новый пустой арей лист, если есть, то создаем новый список продуктов и в него добавляем
         * тот список, что выше*/
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        /*В этом список добавляем продукт 1 продует, который мы находим по id в БД */
        newProductList.removeAll(getCollectRefProductsByIds(productIds));
        /*далее установившем новый список продуктов в нашу корзинку*/
        bucket.setProducts(newProductList);
        /* и сохраняем корзинку с новым набром продуктов в БД*/
        bucketRepository.save(bucket);
    }


    @Override
    /*Метод для того, чтобы корректно высчитывать кол-во товаров взависимости от того сколько раз мы добавили
    * его в нашу корзину*/
    public BucketDTO getBucketByUser(String name) {
        /*Ищем юзера в базе по его имени*/
        User user = userService.findByName(name);
        /*если его нет или его корзина пуста, то создаем новую дто корзину*/
        if (user == null || user.getBucket() == null){
            return new BucketDTO();
        }
        /*Создаем новуб дто корзину*/
        BucketDTO bucketDTO = new BucketDTO();
        /*Создаем мап коллекцию с ид и деталями*/
        Map<Long, BucketDetailDTO> mapByProductId = new HashMap<>();
        /*Создаем лист продуктов взятый у юзера в корзине*/
        List<Product> products = user.getBucket().getProducts();
        /*Проходимся по этому листу*/
        for(Product product : products){
            /*присеваем мапу деталям*/
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            /*если пустой, то добавляем в мапу ид продукта и новые детали по каждому продукту из листа продуктов*/
            if (detail == null){
                mapByProductId.put(product.getId(), new BucketDetailDTO(product));
            }
            else {
                /*если мпа не пустая, то устанавливаем amount и sum значения значениями из мапы*/
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() * Double.valueOf(detail.getAmount().toString()));
            }
        }
        /*после прохода по продуктам записываем общую информацию (все детали) во вью дто */
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        /*Вызываем Метод, который будет агрегировать в себе кол-во определенного товара, чтобы мы выводили сумму по принципу сколько
         * товаров мы ввели(в том числе одинковых, чтобы увидеть общую сумму)*/
        bucketDTO.aggregate();
        return bucketDTO;
    }

    @Override
    @Transactional
    public void commitBucketToOrder(String username){
        /*Ищем нашего юзера в БД по имени и берем его*/
        User user = userService.findByName(username);
        /*Проверяем юзера на нал, друг его не оказалось и юзеру присвоилась ссылка на нал*/
        if  (user == null){
            throw new RuntimeException("Пользователь не найден");
        }
        /*Берем карзину наденного нами юзера*/
        Bucket bucket = user.getBucket();
        /*Проверяем есть ли корзина и не пустая ли она. Если да то смысла что-то делать нет, возвращаемся*/
        if  (bucket == null || bucket.getProducts().isEmpty()){
            return;
        }

        /*Создаем новый заказ*/
        Order order = new Order();
        /*Устанавливаем статус заказа как новый*/
        order.setStatus(OrderStatus.NEW);
        /*Закрепляем за заказом юзера сделавшего заказ*/
        order.setUser(user);

        /*Пытаемся взять все кол-во наших продуктов из корзины и сгруппировать их*/
        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        /**/
        List<OrderDetail> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetail(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(orderDetail -> orderDetail.getPrice().multiply(orderDetail.getAmount()))
                        .mapToDouble(BigDecimal::doubleValue).sum());

        /*передаем детали заказа и сумму, адрес пока пустой*/
        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");

        /*Сохраняем все*/
        orderService.saveOrder(order);
        /*Очищаем корзинку*/
        bucket.getProducts().clear();
        /*Сохраняем очищенную корзинку*/
        bucketRepository.save(bucket);

    }


}
