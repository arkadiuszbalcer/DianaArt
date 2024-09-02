package pl.javastart.dianaart.product.dto;
public class ProductDto {

    private Long id;
    private String name;
    private Double price;
    private String details;
    private  String category;
    private Integer quantity;


    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDto(Long id, String name, Double price, String details, String category, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.details = details;
        this.category = category;
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategoryName(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", details='" + details + '\'' +
                ", category='" + category + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
