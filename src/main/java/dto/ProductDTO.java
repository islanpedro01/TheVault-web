package dto;

public class ProductDTO {
    private String name;
    private double price;

    // Construtor vazio (necessário para a desserialização)
    public ProductDTO() {}

    public ProductDTO(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
