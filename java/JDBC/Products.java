package JDBC;

public class Products {
private String productCode;
private String productName;
private String productLine;
private String productScale;
private String productVendor;
private String productDescription;
private int quantityInStock;
private Double buyPrice;
private Double MSRP;

    public Products(String productCode, String productName, String productLine, String productScale, String productVendor, String productDescription, int quantityInStock, Double buyPrice, Double MSRP) {
        this.productCode = productCode;
        this.productName = productName;
        this.productLine = productLine;
        this.productScale = productScale;
        this.productVendor = productVendor;
        this.productDescription = productDescription;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.MSRP = MSRP;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductLine() {
        return productLine;
    }

    public String getProductScale() {
        return productScale;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public Double getMSRP() {
        return MSRP;
    }
}
