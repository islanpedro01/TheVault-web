package controllers;

import annotations.Controller;
import annotations.Route;
import dto.ProductDTO;
import http.HttpRequest;
import http.HttpResponse;
import test.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    private static final List<Product> products = new ArrayList<>();

    @Route(path = "/products", method = "GET")
    public void getProducts(HttpRequest req, HttpResponse resp) {
        resp.writeJson(products); // Retorna a lista de produtos
    }

    @Route(path = "/products", method = "POST")
    public void addProduct(ProductDTO productDTO, HttpResponse resp) {
        Product p = new Product();
        p.setName(productDTO.getName());
        p.setPrice(productDTO.getPrice());
        products.add(p);
        resp.writeJson(Map.of("message", "Produto adicionado com sucesso!"));
    }
}
