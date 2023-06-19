package com.springshopecommerce.api.web;

import com.springshopecommerce.dto.CartDTO;
import com.springshopecommerce.dto.CartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/web/cart")
public class CartRestController {
    private CartDTO cart = new CartDTO();
    @Autowired
    private HttpSession httpSession;
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDTO cartItemDTO) {
        // Lấy thông tin giỏ hàng từ session (nếu có)
         cart = (CartDTO) httpSession.getAttribute("cart");

        // Nếu giỏ hàng chưa tồn tại, tạo mới giỏ hàng
        if (cart == null) {
            cart = new CartDTO();
            httpSession.setAttribute("cart", cart);
        }

        // Thêm sản phẩm vào giỏ hàng
        cart.addItem(cartItemDTO);

        return ResponseEntity.ok("Add product to cart success");
    }

    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getCartItems() {
        CartDTO cart = (CartDTO) httpSession.getAttribute("cart");

        if (cart == null) {
            cart = new CartDTO();
            httpSession.setAttribute("cart", cart);
        }

        List<CartItemDTO> cartItems = cart.getItems();
        double totalPrice = cart.getTotal();

        int countProducts = cart.countDistinctProducts();

        Map<String, Object> response = new HashMap<>();
        response.put("cartItems", cartItems);
        response.put("totalPrice", totalPrice);
        response.put("countProducts", countProducts);

        return ResponseEntity.ok(response);
    }


}
