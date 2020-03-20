package com.binio.productorder.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.binio.productorder.model.ProductApi;
import com.binio.productorder.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    List<ProductApi> products;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        products = new ArrayList();
        products.add(getTestProduct()
        );

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    private ProductApi getTestProduct() {
        return ProductApi.builder()
                .product_name("Product A")
                .product_price(new BigDecimal(22.00))
                .product_created_date(ZonedDateTime.now())
                .product_id(1L)
                .product_sku("111222333")
                .build();
    }

    @Test
    void getAllProducts() throws Exception{
        when(productService.getAllProducts()).thenReturn(products);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/all")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void getProduct() throws Exception {
        when(productService.getProductById(any())).thenReturn(Optional.of(getTestProduct()));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/id/1")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.product_name").value("Product A"))
                .andExpect(jsonPath("$.product_price").value(new BigDecimal(22.00).toString()))
                .andExpect(jsonPath("$.product_id").value("1"))
                .andExpect(jsonPath("$.product_sku").value("111222333"))
                .andReturn();
    }


    @Test
    void getProductBySkuAndDeleted() throws Exception {
        when(productService.getProductBySkuAndProductDeleted("111222333", false)).thenReturn(Optional.of(getTestProduct()));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/sku/111222333/deleted/0")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.product_name").value("Product A"))
                .andExpect(jsonPath("$.product_price").value(new BigDecimal(22.00).toString()))
                .andExpect(jsonPath("$.product_id").value("1"))
                .andExpect(jsonPath("$.product_sku").value("111222333"))
                .andReturn();
    }

    @Test
    void updateProduct() throws Exception{
        when(productService.updateProduct(any())).thenReturn(Optional.of(getTestProduct()));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/product/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deleteProduct() throws Exception{
        when(productService.deleteProduct(1L)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/product/delete/1")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}