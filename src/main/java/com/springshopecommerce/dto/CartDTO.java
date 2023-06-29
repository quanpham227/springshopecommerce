package com.springshopecommerce.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private List<CartItemDTO> items;
    private double totalPrice;

    public CartDTO() {
        items = new ArrayList<>();
    }

    public void addItem(CartItemDTO cartItem) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        for (CartItemDTO item : items) {
            if (item.getProductId().equals(cartItem.getProductId())) {
                // Nếu sản phẩm đã tồn tại trong giỏ hàng, tăng số lượng
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                updateItemAmount(item); // Cập nhật lại giá trị amount
                updateTotalPrice(); // Cập nhật lại giá trị totalPrice
                return;
            }
        }
        // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới vào
        items.add(cartItem);
        updateItemAmount(cartItem); // Cập nhật lại giá trị amount
        updateTotalPrice(); // Cập nhật lại giá trị totalPrice
    }

    public void removeItem(Long productId) {
        // Xóa sản phẩm dựa trên ID
        items.removeIf(item -> item.getProductId().equals(productId));
        updateTotalPrice(); // Cập nhật lại giá trị totalPrice
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
        updateTotalPrice(); // Cập nhật lại giá trị totalPrice
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public void updateItemQuantity(int index, int quantity) {
        if (index >= 0 && index < items.size()) {
            CartItemDTO item = items.get(index);
            item.setQuantity(quantity);
            updateItemAmount(item); // Cập nhật lại giá trị amount
        }
        updateTotalPrice(); // Cập nhật lại giá trị totalPrice
    }

    private void updateItemAmount(CartItemDTO item) {
        double discount = item.getDiscount();
        int quantity = item.getQuantity();
        double unitPrice = item.getUnitPrice();
        item.setAmount((unitPrice - (unitPrice * discount / 100)) * quantity);
    }
    private void updateTotalPrice() {
        double total = 0;
        for (CartItemDTO item : items) {
            total += item.getAmount();
        }
        totalPrice = total;
    }

}



