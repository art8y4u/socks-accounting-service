package ru.example.socksaccountingservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.example.socksaccountingservice.dto.ProcessedFileInfo;
import ru.example.socksaccountingservice.entity.SockPair;
import ru.example.socksaccountingservice.entity.enums.Color;
import ru.example.socksaccountingservice.entity.enums.ComparisonOperator;
import ru.example.socksaccountingservice.mapper.SockAccountingMapper;
import ru.example.socksaccountingservice.service.SockAccountingService;
import ru.example.socksaccountingservice.util.DataUtils;
import ru.example.socksaccountingservice.util.constants.Constants;

import static org.hibernate.type.IntegerType.ZERO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SockAccountingControllerTest {

    public static final String POST_REGISTER_INCOME_URL = "/socks/income";
    public static final String POST_REGISTER_OUTCOME_URL = "/socks/outcome";
    public static final String PUT_CHANGE_PARAMETERS_URL = "/socks/{id}";
    public static final String GET_FILTERED_QUANTITY_URL = "/socks";
    public static final String POST_REGISTER_FILE_INCOME_URL = "/socks/batch";

    public static final String DEFAULT_SUCCESS_MESSAGE = "Operation performed successfully.";

    @Mock
    private SockAccountingService service;

    @Mock
    private SockAccountingMapper mapper;

    @InjectMocks
    private SockAccountingController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Тестирование метода прихода носков на склад")
    void registerSockIncome_shouldReturnOkResponse_whenRequestIsValid() throws Exception {

        when(service.registerSockIncome(DataUtils.FIRST_SOCK_PAIR.getColor(), DataUtils.FIRST_SOCK_PAIR.getCottonPercentage(), DataUtils.FIRST_SOCK_PAIR.getQuantity())).thenReturn(DataUtils.FIRST_SOCK_PAIR);
        when(mapper.toChangeQuantityResponse(DataUtils.FIRST_SOCK_PAIR)).thenReturn(DataUtils.CHANGE_QUANTITY_RESPONSE);

        mockMvc.perform(post(POST_REGISTER_INCOME_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DataUtils.CHANGE_PARAMETERS_REQUEST)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(DEFAULT_SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.socks_id").value(Constants.FIRST_SOCK_PAIR_ID.toString()))
            .andExpect(jsonPath("$.new_quantity").value(DataUtils.FIRST_SOCK_PAIR.getQuantity()));

        verify(service, times(1))
            .registerSockIncome(DataUtils.FIRST_SOCK_PAIR.getColor(),
                DataUtils.FIRST_SOCK_PAIR.getCottonPercentage(), DataUtils.FIRST_SOCK_PAIR.getQuantity());
        verify(mapper, times(1)).toChangeQuantityResponse(DataUtils.FIRST_SOCK_PAIR);
    }


    @Test
    @DisplayName("Тестирование метода списания носков со склада")
    void registerSockOutcome_shouldReturnOkResponse_whenRequestIsValid() throws Exception {

        when(service.registerSockOutcome(DataUtils.FIRST_SOCK_PAIR.getColor(), DataUtils.FIRST_SOCK_PAIR.getCottonPercentage(), DataUtils.FIRST_SOCK_PAIR.getQuantity())).thenReturn(DataUtils.FIRST_SOCK_PAIR);
        when(mapper.toChangeQuantityResponse(DataUtils.FIRST_SOCK_PAIR)).thenReturn(DataUtils.CHANGE_QUANTITY_RESPONSE);

        mockMvc.perform(post(POST_REGISTER_OUTCOME_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DataUtils.CHANGE_PARAMETERS_REQUEST)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(DEFAULT_SUCCESS_MESSAGE))
            .andExpect(jsonPath("$.socks_id").value(DataUtils.FIRST_SOCK_PAIR.getId().toString()))
            .andExpect(jsonPath("$.new_quantity").value(DataUtils.FIRST_SOCK_PAIR.getQuantity()));

        verify(service, times(1)).registerSockOutcome(DataUtils.FIRST_SOCK_PAIR.getColor(), DataUtils.FIRST_SOCK_PAIR.getCottonPercentage(), DataUtils.FIRST_SOCK_PAIR.getQuantity());
        verify(mapper, times(1)).toChangeQuantityResponse(DataUtils.FIRST_SOCK_PAIR);
    }

    @Test
    @DisplayName("Тестирование метода изменения параметров носков")
    void changeSockPairParameters_shouldReturnOkResponse_whenRequestIsValid() throws Exception {

        when(service.changeSockPairParameters(any(SockPair.class))).thenReturn(DataUtils.FIRST_SOCK_PAIR);
        when(mapper.toSockPair(DataUtils.FIRST_SOCK_PAIR.getId(), DataUtils.CHANGE_PARAMETERS_REQUEST)).thenReturn(DataUtils.FIRST_SOCK_PAIR);
        when(mapper.toChangeParametersResponse(DataUtils.FIRST_SOCK_PAIR)).thenReturn(DataUtils.CHANGE_PARAMETERS_RESPONSE);

        mockMvc.perform(put(PUT_CHANGE_PARAMETERS_URL, DataUtils.FIRST_SOCK_PAIR.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DataUtils.CHANGE_PARAMETERS_REQUEST)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(DataUtils.FIRST_SOCK_PAIR.getId().toString()))
            .andExpect(jsonPath("$.color").value(DataUtils.FIRST_SOCK_PAIR.getColor().toString()))
            .andExpect(jsonPath("$.cotton_percentage").value(DataUtils.FIRST_SOCK_PAIR.getCottonPercentage()))
            .andExpect(jsonPath("$.quantity").value(DataUtils.FIRST_SOCK_PAIR.getQuantity()));

        verify(service, times(1)).changeSockPairParameters(any(SockPair.class));
        verify(mapper, times(1)).toSockPair(DataUtils.FIRST_SOCK_PAIR.getId(),
            DataUtils.CHANGE_PARAMETERS_REQUEST);
        verify(mapper, times(1)).toChangeParametersResponse(DataUtils.FIRST_SOCK_PAIR);
    }

    @Test
    @DisplayName("Тестирование метода получения отфильтрованного количества носков")
    void getFilteredQuantity_shouldReturnOkResponse_whenRequestIsValid() throws Exception {

        final Color color = DataUtils.FIRST_SOCK_PAIR.getColor();
        final int cottonPercentage = DataUtils.FIRST_SOCK_PAIR.getCottonPercentage();
        final int quantity = DataUtils.FIRST_SOCK_PAIR.getQuantity();

        when(service.getTotalSocksAmount(color, ComparisonOperator.EQUAL, cottonPercentage))
            .thenReturn(quantity);

        mockMvc.perform(get(GET_FILTERED_QUANTITY_URL)
                .param("color", String.valueOf(color))
                .param("cotton", String.valueOf(cottonPercentage))
                .param("operator", String.valueOf(ComparisonOperator.EQUAL)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.total_amount").value(quantity));

        verify(service, times(1))
            .getTotalSocksAmount(color, ComparisonOperator.EQUAL, cottonPercentage);
    }

    @Test
    @DisplayName("Тестирование метода загрузки файла с приходом носок")
    void registerFileIncome_shouldReturnOkResponse_whenFileIsProcessedSuccessfully() throws Exception {

        final MockMultipartFile fileMock = new MockMultipartFile(Constants.MULTIPART_FILE_NAME,
            Constants.MULTIPART_FILE_NAME.getBytes());
        final ProcessedFileInfo fileInfo = ProcessedFileInfo.builder()
            .rowsSkipped(ZERO)
            .processedRows(ZERO)
            .build();

        when(service.processFileIncome(any(MultipartFile.class))).thenReturn(fileInfo);

        mockMvc.perform(multipart(POST_REGISTER_FILE_INCOME_URL)
                .file(fileMock)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.processed").value(fileInfo.processedRows()))
            .andExpect(jsonPath("$.skipped").value(fileInfo.rowsSkipped()));

        verify(service, times(1)).processFileIncome(fileMock);
    }
}