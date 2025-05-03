package com.devpuccino.accountservice.controller;

import com.devpuccino.accountservice.service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TransactionService transactionService;

    @Test
    public void shouldResponseSuccess_whenInsertTransaction() throws Exception {
        String requestJson = """
            {
                "wallet_id": "1",
                "category_id": "1",
                "amount": 125.50,
                "transaction_date": "2025-05-01",
                "transaction_type": "INCOME"
            }
        """;
        Long expectedData = 1L;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        when(transactionService.insertTransaction(any())).thenReturn(expectedData);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200-000"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.transaction_id").value(expectedData));
    }
    @Test
    public void shouldResponseInvalidRequest_whenInsertTransaction_without_wallet_id() throws Exception {
        String requestJson = """
            {
                "category_id": "1",
                "amount": 125.50,
                "transaction_date": 1746259150,
                "transaction_type": "INCOME"
            }
        """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400-002"))
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
    }
    @Test
    public void shouldResponseInvalidRequest_whenInsertTransaction_without_category_id() throws Exception {
        String requestJson = """
            {
                "wallet_id": "1",
                "amount": 125.50,
                "transaction_date": 1746259150,
                "transaction_type": "INCOME"
            }
        """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400-002"))
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
    }
    @Test
    public void shouldResponseInvalidRequest_whenInsertTransaction_without_amount() throws Exception {
        String requestJson = """
            {
               "wallet_id": "1",
                "category_id": "1",
                "transaction_date": 1746259150,
                "transaction_type": "INCOME"
            }
        """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400-002"))
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
    }
    @Test
    public void shouldResponseInvalidRequest_whenInsertTransaction_invalid_amount() throws Exception {
        String requestJson = """
            {
               "wallet_id": "1",
                "category_id": "1",
                "amount": "abc",
                "transaction_date": 1746259150,
                "transaction_type": "INCOME"
            }
        """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400-002"))
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
    }
    @Test
    public void shouldResponseInvalidRequest_whenInsertTransaction_without_transaction_date() throws Exception {
        String requestJson = """
            {
                "wallet_id": "1",
                "category_id": "1",
                "amount": 125.50,
                "transaction_type": "INCOME"
            }
        """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400-002"))
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
    }
    @Test
    public void shouldResponseInvalidRequest_whenInsertTransaction_invalid_transaction_type() throws Exception {
        String requestJson = """
            {
                "wallet_id": "1",
                "category_id": "1",
                "amount": 125.50,
                "transaction_date": 1746259150,
                "transaction_type": "income"
            }
        """;
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/transaction").contentType(MediaType.APPLICATION_JSON).content(requestJson);
        mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400-002"))
                .andExpect(jsonPath("$.message").value("Invalid request"))
                .andExpect(jsonPath("$.data").value(Matchers.nullValue()));
    }
}
