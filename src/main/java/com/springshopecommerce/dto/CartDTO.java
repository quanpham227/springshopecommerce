package com.springshopecommerce.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;

    public CartDTO() {
        items = new ArrayList<>();
    }

    public void addItem(CartItemDTO cartItem) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        for (CartItemDTO item : items) {
            if (item.getProductId().equals(cartItem.getProductId())) {
                // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                return;
            }
        }
        // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới vào
        items.add(cartItem);
    }

    public void removeItem(Long productId) {
        // Xóa sản phẩm dựa trên ID
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public int countDistinctProducts() {
        List<Long> productIds = new ArrayList<>();

        // Duyệt qua danh sách sản phẩm và thêm mã sản phẩm vào danh sách
        for (CartItemDTO item : items) {
            if (!productIds.contains(item.getProductId())) {
                productIds.add(item.getProductId());
            }
        }

        // Trả về số lượng sản phẩm khác mã sản phẩm
        return productIds.size();
    }


    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public void clear() {
        // Xóa tất cả các sản phẩm trong giỏ hàng
        items.clear();
    }

    public double getTotal() {
        // Tính tổng số tiền trong giỏ hàng
        double total = 0;
        for (CartItemDTO item : items) {
            total += item.getUnitPrice() * item.getQuantity();
        }
        return total;
    }
}



